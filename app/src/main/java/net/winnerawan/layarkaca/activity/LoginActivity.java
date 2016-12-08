package net.winnerawan.layarkaca.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.helper.DialogUtils;
import net.winnerawan.layarkaca.helper.MyTwitterApiClient;
import net.winnerawan.layarkaca.helper.SQLiteHandler;
import net.winnerawan.layarkaca.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SessionManager session;
    private SQLiteHandler db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SIGN IN";
    private static final int REQUEST_CODE_GOOGLE_SIGN_IN = 1;
    GoogleApiClient mGoogleApiClient;
    static LoginManager loginManager;
    CallbackManager callbackManager;
    private ArrayList<String> permissions = new ArrayList<>();

    TwitterSession sessionss;

    @Bind(R.id.login_with_facebook) LoginButton mFacebookLoginButton;
    @Bind(R.id.login_with_twitter) TwitterLoginButton mTwitterLoginButton;

    @Bind(R.id.btnFacebook) ImageView btnFacebook;
    @Bind(R.id.btnGoogle) ImageView btnGoogle;
    @Bind(R.id.btnTwitter) ImageView btnTwitter;

    @OnClick(R.id.btnGoogle)
    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        DialogUtils.showProgressDialog(LoginActivity.this, "", getString(R.string.sign_in), false);

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Google sign in failed. Please try again",
                                    Toast.LENGTH_SHORT).show();
                            DialogUtils.dismissProgressDialog();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("account", "google");
                            //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("username", acct.getDisplayName());
                            //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("googleId", acct.getId());
                            startActivity(intent);
                            finish();
                            session.setLogin(true);
                            // TODO -> insert sqlite
                            DialogUtils.dismissProgressDialog();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        mTwitterLoginButton.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                Log.d("google sign in", "successful");
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.e("google sign in", "failed");
                // Google Sign In failed, update UI appropriately
                // ...
            }
        } else if (requestCode ==  CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }




    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        session = new SessionManager(getApplicationContext());
        //db = new SQLiteHandler(getApplicationContext());

        FacebookSdk.sdkInitialize(getApplicationContext());
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        setupFacebookLoginButton();
        permissions.add("user_friends");
        permissions.add("user_birthday");
        permissions.add("public_profile");
        permissions.add("email");
        mFacebookLoginButton.setReadPermissions(permissions);

        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "firebaseauthentication.android.com.firebaseauthentication",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("exception", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("exception", e.toString());
        }*/

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTwitterLoginButton.performClick();
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFacebookLoginButton.performClick();

            }
        });

        mFacebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setFacebookData(loginResult);
                Log.e("facebook login", "success");
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String name = object.optString("name");
                        String email = object.optString("email");
                        String bday = object.optString("birthday");
                        String firstName = object.optString("first_name");
                        String lastName = object.optString("last_name");
                        String gender = object.optString("gender");
                        String facebookUserId = object.optString("id");
                        if (!TextUtils.isEmpty(name)) {
                            Log.e(TAG, "PRO "+name+" "+email+" "+bday);
                            //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("username", name);
                        }
                        //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("facebookUserId", facebookUserId);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name,gender, birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Facebook Sign in failed. Please try again", Toast.LENGTH_SHORT).show();
            }
        });


        mTwitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d("Twitter Login", "success");
                sessionss = result.data;
                Twitter.getApiClient(sessionss).getAccountService().verifyCredentials(true, false).enqueue(new Callback<User>()
                {
                    @Override
                    public void success(Result<User> userResult)
                    {
                        try
                        {
                            final User user = userResult.data;
                            String foto = user.profileImageUrlHttps;
                            String name = user.name;
                            String location = user.location;
                            String email = user.email;
                            String x = user.description;
                            int y = user.followersCount;
                            Long twid = user.getId();
                            String tw = user.idStr;
                            Long i = user.id;
                            Log.e(TAG, "TW "+name+"twid"+twid+"-"+tw+"long "+i);

                            Call<User> usertw = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false);
                            usertw.enqueue(new Callback<User>() {
                                @Override
                                public void success(Result<User> userResult) {
                                    String name = userResult.data.name;
                                    String email = userResult.data.email;

                                    // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
                                    String photoUrlNormalSize   = userResult.data.profileImageUrl;
                                    String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                                    String photoUrlMiniSize     = userResult.data.profileImageUrl.replace("_normal", "_mini");
                                    String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");
                                    db = new SQLiteHandler(getApplicationContext());
                                    db.addUser(name, email, photoUrlOriginalSize, 17, "", "");

                                }

                                @Override
                                public void failure(TwitterException exc) {
                                    Log.d("TwitterKit", "Verify Credentials Failure", exc);
                                }
                            });


//                         twitterImage = user.profileImageUrl;
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(TwitterException e)
                    {

                    }

                });
                String name = result.data.getUserName();
                String twitterUserId = String.valueOf(result.data.getUserId());

                if (!TextUtils.isEmpty(name)) {
                    Log.e(TAG, "TW "+name);

                    //SharedPreferenceUtils.getInstance(LoginActivity.this).setValue("username", name);

                }
                //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("twitterUserId", twitterUserId);

                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Twitter Login", "failure");
            }
        });



        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String URL = "https://graph.facebook.com/";
                if (user != null) {
                    // User is signed in
                    Log.e(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (!user.getProviderData().isEmpty() && user.getProviderData().size() > 1)
                        URL.concat(user.getProviderData().get(1).getUid() + "/picture?type=large");
                    /*
                    Log.e(TAG, "POTO PESBUK:" + URL.concat(user.getProviderData().get(1).getUid() + "/picture?type=large"));
                    Log.e(TAG, "NAMA PESBUK:" + user.getDisplayName());
                    Log.e(TAG, "EMAIL PESBUK:" + user.getEmail());
                    Log.e(TAG, "foto PESBUK:" + user.getPhotoUrl());
                    */
//                    SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("uid", user.getUid());
                } else {
                    // User is signed out
                    Log.e(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        DialogUtils.showProgressDialog(LoginActivity.this, "", getString(R.string.sign_in), false);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Login with Facebook successful");
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("account", "facebook");
                            startActivity(intent);
                            finish();
                            session.setLogin(true);
                            // TODO -> insert sqlite
                        }
                        DialogUtils.dismissProgressDialog();
                    }
                });
    }

    private void handleTwitterSession(final TwitterSession sessions) {
        Log.d(TAG, "handleTwitterSession:" + sessions);
        // [START_EXCLUDE silent]
        DialogUtils.showProgressDialog(LoginActivity.this, "", getString(R.string.sign_in), false);
        // [END_EXCLUDE]

        AuthCredential credential = TwitterAuthProvider.getCredential(
                sessions.getAuthToken().token,
                sessions.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential Twitter : onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            //SharedPreferenceUtils.getInstance(SigninSignupActivity.this).setValue("account", "twitter");
                            startActivity(intent);
                            finish();
                            session = new SessionManager(getApplicationContext());
                            session.setLogin(true);
                            // TODO -> insert sqlite
                        }
                        DialogUtils.dismissProgressDialog();
                    }
                });
    }


    /*Facebook Login*/
    private void setupFacebookLoginButton() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton = (LoginButton) findViewById(R.id.login_with_facebook);
        mFacebookLoginButton.setReadPermissions("email", "public_profile");
        mFacebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e("TomerBu", "Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("TomerBu", error.getLocalizedMessage());
            }
        });
    }


    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response",response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");
                            String bday= response.getJSONObject().getString("birthday");
                            //String location= response.getJSONObject().getString("location");

                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link",link);
                            if (Profile.getCurrentProfile()!=null)
                            {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }
                                /*
                            Log.i("Login" + "Email", email);
                            Log.i("Login"+ "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);
                            Log.i("Login" + "Bday", bday);

                            Log.i("Login" + "+", profile.getName());
                                */
                            String[] birthday = bday.split("/");
                            int month = Integer.parseInt(birthday[0]);
                            int day = Integer.parseInt(birthday[1]);
                            int year = Integer.parseInt(birthday[2]);

                            int age = Integer.parseInt(getAge(year, month, day));

                            db = new SQLiteHandler(getApplicationContext());
                            db.addUser(profile.getName(), email, profile.getProfilePictureUri(200,200).toString(), age , gender, bday);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        Log.e(TAG, "hari ini "+today);
        Log.e(TAG, "umur : "+ageInt);
        Log.e(TAG, "umur : "+ageS);

        return ageS;

    }



}

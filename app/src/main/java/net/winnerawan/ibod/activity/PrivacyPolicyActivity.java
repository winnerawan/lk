package net.winnerawan.ibod.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.winnerawan.ibod.R;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.Bind;
import butterknife.ButterKnife;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private static final String TAG = PrivacyPolicyActivity.class.getSimpleName();

    private final String TITLE = "Privacy & Policy";
    private RequestQueue queue;
    public static int MY_SOCKET_TIMEOUT_MS = 200000;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.txt_privacy_policy) WebView txt_privacy_policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        overridePendingTransition(R.anim.anim_pop_left, R.anim.anim_push_left);
        ButterKnife.bind(this);
        initToolbar();
        iniLayout();
        setTxt_privacy_policy();
    }

    private void iniLayout() {
        this.txt_privacy_policy.getSettings().setJavaScriptEnabled(true);
        this.txt_privacy_policy.setBackgroundColor(Color.parseColor("#ff1a1a1a"));
        this.txt_privacy_policy.setWebViewClient(new WebViewClient()
        {
            public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                PrivacyPolicyActivity.this.txt_privacy_policy.loadUrl("javascript:document.body.style.setProperty(\"color\", \"white\");");
            }
        });
        this.queue = Volley.newRequestQueue(this);
    }

    private void initToolbar() {
        setSupportActionBar(this.toolbar);
        ((ImageView)findViewById(R.id.left_btn_toolbar)).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                paramAnonymousView.startAnimation(AnimationUtils.loadAnimation(PrivacyPolicyActivity.this, R.anim.image_click));
                PrivacyPolicyActivity.this.finishAction();
            }
        });
        ((ImageView)findViewById(R.id.right_btn_toolbar)).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.txt_title)).setText("Privacy & Policy");
    }

    /*
    private void setTxt_privacy_policy() {
        JsonObjectRequest localJsonObjectRequest = new JsonObjectRequest(0, "http://api.zulu.id/v2/staticcontent/PRIVACYANDPOLICY", null, new Response.Listener()new Response.ErrorListener
        {
            public void onResponse(JSONObject paramAnonymousJSONObject)
            {
                try
                {
                    PrivacyPolicyActivity.this.txt_privacy_policy.loadDataWithBaseURL(null, paramAnonymousJSONObject.getString("description").replace("justify", "left"), "text/html; charset=utf-8", "UTF-8", null);
                    return;
                }
                catch (JSONException paramAnonymousJSONObject)
                {
                    paramAnonymousJSONObject.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
    {
        public void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
            VolleyLog.d("Error response", new Object[] { "Error: " + paramAnonymousVolleyError.getMessage() });
        }
    });
        localJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, 1, 1.0F));
        this.queue.add(localJsonObjectRequest);
    }
    */

    private void setTxt_privacy_policy() {
        JsonObjectRequest req = new JsonObjectRequest("http://api.layarkaca.wonderwall.biz.id/v1/static/PRIVACYPOLICY.json", new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e(TAG, "PRIVACY LOG "+response.toString());
                            PrivacyPolicyActivity.this.txt_privacy_policy.loadDataWithBaseURL(null, response.getString("description").replace("justify", "left"), "text/html; charset=utf-8", "UTF-8", null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, 1, 1.0F));
        this.queue.add(req);
    }
    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_right, R.anim.anim_push_right);
    }

    @Override
    public void onBackPressed() {
        finishAction();
    }
}

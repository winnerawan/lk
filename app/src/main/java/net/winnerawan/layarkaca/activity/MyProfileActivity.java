package net.winnerawan.layarkaca.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.google.firebase.auth.FirebaseUser;

import net.winnerawan.layarkaca.R;
import net.winnerawan.layarkaca.app.AppController;
import net.winnerawan.layarkaca.helper.CircledNetworkImageView;
import net.winnerawan.layarkaca.helper.SQLiteHandler;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyProfileActivity extends AppCompatActivity {

    SQLiteHandler db;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Bind(R.id.img_profile) CircledNetworkImageView img_profile;
    @Bind(R.id.txt_name) TextView txt_name;
    @Bind(R.id.txt_member) TextView txt_member;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.anim_pop_left, R.anim.anim_push_left);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        String email = user.get("email");
        String foto = user.get("foto");
        //String age = user.get("age");
        //String gender = user.get("gender");

        img_profile.setImageUrl(foto, imageLoader);
        txt_name.setText(name);
        //String jenis_kelamin = Character.toUpperCase(gender.charAt(0))+gender.substring(1);
        txt_member.setText(email);


    }

    private void finishAction() {
        finish();
        overridePendingTransition(R.anim.anim_pop_right, R.anim.anim_push_right);
    }
    public void onBackPressed() {
        finishAction();
    }
}

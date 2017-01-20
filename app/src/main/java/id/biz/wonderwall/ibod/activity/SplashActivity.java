package id.biz.wonderwall.ibod.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.biz.wonderwall.ibod.R;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.btn_get_started) Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
        });
    }


}

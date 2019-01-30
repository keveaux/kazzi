package com.example.kelvincb.ikazi.splashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kelvincb.ikazi.R;
import com.example.kelvincb.ikazi.UserLoginAndRegister.LoginRegisterActivity;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
            }
        }, 1500);

    }
}

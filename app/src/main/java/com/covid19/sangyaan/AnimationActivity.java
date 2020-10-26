
package com.covid19.sangyaan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(()-> {
            Intent intent = new Intent(AnimationActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim);
            finish();
        },2000);

    }

}
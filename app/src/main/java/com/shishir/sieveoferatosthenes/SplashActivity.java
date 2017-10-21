package com.shishir.sieveoferatosthenes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nvanbenschoten.motion.ParallaxImageView;
import com.shishir.sieveoferatosthenes.utils.UIUtils;

public class SplashActivity extends Activity {

    /**Used Parallax effect to splash screen background*/
    private ParallaxImageView mBackground;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ctx = SplashActivity.this;
        mBackground = (ParallaxImageView)findViewById(android.R.id.background);

        mBackground.setImageResource(R.drawable.ancient_greek);

        /**Library to add animation to image*/
        YoYo.with(Techniques.Landing)
                .duration(5000)
                .repeat(0)
                .playOn(findViewById(R.id.img_splash_center));

        UIUtils.showToast(ctx,R.string.touch_to_begin);
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .repeat(10)
                .playOn(findViewById(R.id.img_touch_being));

        mBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ctx, MainActivity.class));

                finish();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mBackground.registerSensorManager();
    }

    @Override
    public void onPause() {
        mBackground.unregisterSensorManager();
        super.onPause();
    }
}

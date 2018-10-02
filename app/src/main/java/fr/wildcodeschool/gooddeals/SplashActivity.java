package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity    {

    private static int SPLASH_TIME_OUT = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView good = findViewById(R.id.text_good);
        TextView deals = findViewById(R.id.text_deals);
        ImageView logo = findViewById(R.id.image_logo);

        Animation fromBottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        Animation fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        good.setAnimation(fromTop);
        deals.setAnimation(fromTop);
        logo.setAnimation(fromBottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }
        },SPLASH_TIME_OUT);
    }
}


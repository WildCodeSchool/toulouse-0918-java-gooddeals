package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView good = findViewById(R.id.text_good);
        TextView deals = findViewById(R.id.text_deals);
        ImageView logo = findViewById(R.id.image_logo);

        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        Animation fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        good.setAnimation(fromTop);
        deals.setAnimation(fromTop);
        logo.setAnimation(fromBottom);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Singleton singleton = Singleton.getInstance();
        if (user != null) {
            String personEmail = user.getEmail();
            String personGivenName = user.getDisplayName();
            Uri personPhoto = user.getPhotoUrl();

            String photoPath = null;
            if (personPhoto != null) {
                photoPath = personPhoto.toString();
            }

            if (personGivenName != null && personGivenName.isEmpty()) {
                personGivenName = null;
            }

            LoginModel loginModel = new LoginModel(personEmail, photoPath, personGivenName);
            singleton.setLogModel(loginModel);
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, NavbarActivity.class));

            }
        }, SPLASH_TIME_OUT);

        DealSingleton.getInstance();
    }
}


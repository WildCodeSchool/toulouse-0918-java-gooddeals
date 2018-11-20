package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private boolean mDealsLoaded = false;
    private boolean mUserLoaded = false;
    private boolean mAnimationEnded = false;

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

        if (user != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("User");
            myRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    LoginModel logModel = dataSnapshot.getValue(LoginModel.class);
                    Singleton singleton = Singleton.getInstance();
                    singleton.setLogModel(logModel);
                    mUserLoaded = true;
                    goToMap();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mUserLoaded = true;
                    goToMap();
                }
            });
        } else {
            mUserLoaded = true;
            goToMap();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimationEnded = true;
                goToMap();
            }
        }, SPLASH_TIME_OUT);

        DealSingleton singleton = DealSingleton.getInstance();
        singleton.loadDeals(new DealListener() {
            @Override
            public void onResponse(boolean success) {
                mDealsLoaded = true;
                goToMap();
            }
        });
    }

    private void goToMap() {
        if (mUserLoaded && mDealsLoaded && mAnimationEnded) {
            startActivity(new Intent(SplashActivity.this, NavbarActivity.class));
        }
    }
}


package fr.wildcodeschool.gooddeals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class Popup extends AppCompatActivity {

    ImageButton shareDeal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        Intent intentFromList = getIntent();
        String description = intentFromList.getStringExtra("EXTRA_DESCRIPTION");
        String title = intentFromList.getStringExtra("EXTRA_TITLE");
        int image = intentFromList.getIntExtra("EXTRA_IMAGE", 0);

        TextView text = findViewById(R.id.titleDeal);
        text.setText(title);

        TextView desc = findViewById(R.id.textPresentation);
        desc.setText(description);

        ImageView pic = findViewById(R.id.imageDeal);
        pic.setImageResource(image);

        shareDeal = findViewById(R.id.shareButton);
        shareDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("Text/Plain");
                String shareBody = "Your body here";
                String shareSub = "Your subject here";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, getString(R.string.titleShare)));

            }
        });
    }
}






package fr.wildcodeschool.gooddeals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class Popup extends AppCompatActivity {

    ImageButton shareDeal;
    ImageButton itinerary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .6), (int) (height * .6));

        Intent intentFromList = getIntent();
        String description = intentFromList.getStringExtra("EXTRA_DESCRIPTION");
        String title = intentFromList.getStringExtra("EXTRA_TITLE");
        String image = intentFromList.getStringExtra("EXTRA_IMAGE");
        final Double latitude = intentFromList.getDoubleExtra("EXTRA_LATITUDE", 0.0);
        final Double longitude = intentFromList.getDoubleExtra("EXTRA_LONGITUDE", 0.0);

        TextView text = findViewById(R.id.titleDeal);
        text.setText(title);

        TextView desc = findViewById(R.id.textPresentation);
        desc.setText(description);

        ImageView pic = findViewById(R.id.imageDeal);
        Glide.with(this).load(image).into(pic);

        shareDeal = findViewById(R.id.shareButton);
        shareDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("Text/Plain");
                startActivity(Intent.createChooser(myIntent, getString(R.string.titleShare)));
            }
        });
        itinerary = findViewById(R.id.bt_itinerary);
        itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itineraryIntent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?.34&daddr=" + latitude+ "," + longitude));
                startActivity(itineraryIntent);
            }
        });
    }
}






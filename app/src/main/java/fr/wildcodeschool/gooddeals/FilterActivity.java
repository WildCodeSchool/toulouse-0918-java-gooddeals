package fr.wildcodeschool.gooddeals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.CheckBox;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        CheckBox pourMangerBox = findViewById(R.id.pour_manger);
        CheckBox aperoBox = findViewById(R.id.aperos_filter);
        CheckBox friandisesBox = findViewById(R.id.friandises_filter);
        CheckBox bienEtreBox = findViewById(R.id.bien_etre_filter);
        CheckBox loisirsBox = findViewById(R.id.loisir_filter);


    }
}

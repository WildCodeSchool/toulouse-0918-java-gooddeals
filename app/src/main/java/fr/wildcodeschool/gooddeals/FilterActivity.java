package fr.wildcodeschool.gooddeals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    ArrayList<String> selection = new ArrayList<String>();



    public void finalSelection() {

    }

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

        /*

    public void selectItem(View view) {

        boolean checked = ((CheckBox) view). isChecked();
        switch (view.getId()) {
            case : R.id.pour_manger:

        }

        */







    }

package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

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

        final String currentFragment = getIntent().getStringExtra("CURRENT_FRAGMENT");

        final CheckBox pourMangerBox = findViewById(R.id.pour_manger);
        final CheckBox aperoBox = findViewById(R.id.aperos_filter);
        final CheckBox friandisesBox = findViewById(R.id.friandises_filter);
        final CheckBox bienEtreBox = findViewById(R.id.bien_etre_filter);
        final CheckBox loisirsBox = findViewById(R.id.loisir_filter);
        pourMangerBox.setTypeface(ResourcesCompat.getFont(FilterActivity.this, R.font.montserrat));
        aperoBox.setTypeface(ResourcesCompat.getFont(FilterActivity.this, R.font.montserrat));
        friandisesBox.setTypeface(ResourcesCompat.getFont(FilterActivity.this, R.font.montserrat));
        bienEtreBox.setTypeface(ResourcesCompat.getFont(FilterActivity.this, R.font.montserrat));
        loisirsBox.setTypeface(ResourcesCompat.getFont(FilterActivity.this, R.font.montserrat));

        Button filterButton = findViewById(R.id.but_filter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pourManger = pourMangerBox.isChecked();
                boolean friandises = friandisesBox.isChecked();
                boolean bienEtre = bienEtreBox.isChecked();
                boolean loisirs = loisirsBox.isChecked();
                boolean aperos = aperoBox.isChecked();


                if (!pourManger && !friandises && !bienEtre && !loisirs && !aperos) {
                    Toast.makeText(FilterActivity.this, R.string.categorie, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(FilterActivity.this, NavbarActivity.class);
                    intent.putExtra("filter_manger", pourManger);
                    intent.putExtra("filter_aperos", aperos);
                    intent.putExtra("filter_friandises", friandises);
                    intent.putExtra("filter_bienEtre", bienEtre);
                    intent.putExtra("filter_loisirs", loisirs);
                    intent.putExtra("CURRENT_FRAGMENT", currentFragment);
                    startActivity(intent);
                }
            }
        });
    }
}

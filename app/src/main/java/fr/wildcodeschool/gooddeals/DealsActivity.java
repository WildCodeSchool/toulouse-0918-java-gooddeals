package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DealsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);


        Intent intentFromSearch = getIntent();


        ListView listDeals = findViewById(R.id.list_view_deals);
        ArrayList<DealsModel> results = new ArrayList<>();


        Integer integerPrice = new Integer(0);

        results.add(new DealsModel("Santosha", "", "10€ le plat", ""));
        results.add(new DealsModel("Pitaya", "-15%", "", ""));
        results.add(new DealsModel("Duck Me", "",
                "Une planche de Canardises offertes pour l'achat d'une bouteille" +
                        "Du mercredi au dimanche à partir de 18h", ""));
        results.add(new DealsModel("Subway", "-30%","","" ));
        results.add(new DealsModel("Bistrot des Marchands", "","Happy hour(18h-20h)" +
                "Demi = 3€  Pinte = 4€" ,""));
        results.add(new DealsModel("SLD café", "-20%","" ,""));
        results.add(new DealsModel("Bagelstein", "-15%","","" ));
        results.add(new DealsModel("Chocolat de Neuville", "-15%","" ,""));
        results.add(new DealsModel("L'Inde", "-10%","" ,""));


        DealsAdapter adapter = new DealsAdapter(this, results);
        listDeals.setAdapter(adapter);
    }
}

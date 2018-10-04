package fr.wildcodeschool.gooddeals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static fr.wildcodeschool.gooddeals.MapFragment.dealArrayList;

public class ListFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        ListView listDeals = findViewById(R.id.list_view_deals);
        DealsAdapter adapter = new DealsAdapter(listDeals.getContext(), dealArrayList());
        listDeals.setAdapter(adapter);

        listDeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListFragment.this, "Clic sur la liste", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

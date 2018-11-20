package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ListFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_deals, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListView listDeals = getView().findViewById(R.id.list_view_deals);
        DealSingleton dealSingleton = DealSingleton.getInstance();
        final ArrayList<Deal> deals = dealSingleton.getDealArrayList();


        ArrayList<Deal> dealsFilter = new ArrayList<>();

        Bundle mBundle = this.getArguments();
        if (mBundle != null) {
            boolean pourManger = mBundle.getBoolean("filter_manger", true);
            boolean friandises = mBundle.getBoolean("filter_friandises", true);
            boolean bienEtre = mBundle.getBoolean("filter_bienEtre", true);
            boolean loisirs = mBundle.getBoolean("filter_loisirs", true);
            boolean aperos = mBundle.getBoolean("filter_aperos", true);
            for (Deal deal : deals) {
                switch (deal.getType()) {

                    case "Pour Manger":
                        if (pourManger) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Apéro":
                        if (aperos) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Friandises":
                        if (friandises) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Bien-être":
                        if (bienEtre) {
                            dealsFilter.add(deal);
                        }
                        break;
                    case "Loisirs":
                        if (loisirs) {
                            dealsFilter.add(deal);
                        }
                        break;
                }
            }
        }
        final DealsAdapter adapter = new DealsAdapter(listDeals.getContext(), dealsFilter);

        listDeals.setAdapter(adapter);
        listDeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Deal deal = deals.get(i);
                Intent intent = new Intent(getActivity(), Popup.class);
                intent.putExtra("EXTRA_DESCRIPTION", deal.getDescription());
                intent.putExtra("EXTRA_TITLE", deal.getName());
                intent.putExtra("EXTRA_IMAGE", deal.getImage());
                intent.putExtra("EXTRA_LATITUDE", deal.getLatitude());
                intent.putExtra("EXTRA_LONGITUDE", deal.getLongitude());
                startActivity(intent);
            }
        });
    }
}
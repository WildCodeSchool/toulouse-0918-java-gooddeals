package fr.wildcodeschool.gooddeals;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_deals, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ArrayList<Deal> deals = new ArrayList<>();
        final ListView listDeals = getView().findViewById(R.id.list_view_deals);
        final DealsAdapter adapter = new DealsAdapter(listDeals.getContext(), deals);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dealRef = database.getReference("deal");
        dealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dealSnapshot : dataSnapshot.getChildren()) {
                    Deal deal = dealSnapshot.getValue(Deal.class);
                    deals.add(deal);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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

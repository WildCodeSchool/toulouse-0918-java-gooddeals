package fr.wildcodeschool.gooddeals;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class DealSingleton {
    private static final DealSingleton ourInstance = new DealSingleton();
    private ArrayList<Deal> dealArrayList = new ArrayList<>();

    static DealSingleton getInstance() {
        return ourInstance;
    }

    private DealSingleton() {
        loadDeals();
    }

    public void loadDeals() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dealRef = database.getReference("deal");
        dealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dealSnapshot : dataSnapshot.getChildren()) {
                    Deal deal = dealSnapshot.getValue(Deal.class);
                    dealArrayList.add(deal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public ArrayList<Deal> getDealArrayList() {
        return dealArrayList;
    }
}

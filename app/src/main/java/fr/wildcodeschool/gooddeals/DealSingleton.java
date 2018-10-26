package fr.wildcodeschool.gooddeals;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class DealSingleton {
    private static final DealSingleton ourInstance = new DealSingleton();
    private ArrayList<Deal> dealArrayList = new ArrayList<>();

    private DealSingleton() {

    }

    static DealSingleton getInstance() {
        return ourInstance;
    }

    public void loadDeals(final DealListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dealRef = database.getReference("deal");
        dealRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dealSnapshot : dataSnapshot.getChildren()) {
                    Deal deal = dealSnapshot.getValue(Deal.class);
                    dealArrayList.add(deal);
                }
                listener.onResponse(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onResponse(false);
            }
        });
    }

    public ArrayList<Deal> getDealArrayList() {
        return dealArrayList;
    }
}

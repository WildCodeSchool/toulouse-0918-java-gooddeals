package fr.wildcodeschool.gooddeals;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static fr.wildcodeschool.gooddeals.R.*;

public class DealsAdapter extends ArrayAdapter {

    public DealsAdapter(@NonNull Context context, ArrayList<Deal> results ) {
        super(context, 0, results);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Deal deals = (Deal) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout.item_deals, parent, false);
        }
        TextView dealName =convertView.findViewById(id.name_deals);
        dealName.setText(deals.getName());

        ImageView icons =convertView.findViewById(id.icon_deals);
        icons.setImageResource(deals.getIcon());

        return convertView;
    }
}
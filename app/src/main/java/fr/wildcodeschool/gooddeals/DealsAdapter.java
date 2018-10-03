package fr.wildcodeschool.gooddeals;


import android.content.Context;
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


    public DealsAdapter(@NonNull Context context, ArrayList<DealsModel> results ) {
        super(context, 0, results);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DealsModel deals = (DealsModel) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout.item_deals, parent, false);

        }

        TextView nom = (TextView) convertView.findViewById(id.nom_deals);
        TextView reduction = (TextView) convertView.findViewById(id.reduction_deals);
        TextView description = (TextView) convertView.findViewById(id.description_deals);
        ImageView icons = (ImageView) convertView.findViewById(id.icon_deals);

        nom.setText(deals.getNom());
        reduction.setText(deals.getReduction());
        description.setText(deals.getDescription());





        return convertView;
    }
}
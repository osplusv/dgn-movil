package com.oracle.dgnmovil.app.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oracle.dgnmovil.app.R;
import com.oracle.dgnmovil.app.model.Item;

import java.util.ArrayList;

/**
 * Created by osvaldo on 10/18/14.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_favoritos, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.placeholder);

        TextView item_clave = (TextView) convertView.findViewById(R.id.item_clave);
        item_clave.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        TextView item_titulo = (TextView) convertView.findViewById(R.id.item_titulo);
        item_titulo.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        TextView item_fecha = (TextView) convertView.findViewById(R.id.item_fecha);
        item_fecha.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        return convertView;
    }
}

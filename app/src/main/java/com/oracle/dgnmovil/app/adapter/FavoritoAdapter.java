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
import com.oracle.dgnmovil.app.model.Favorito;

import java.util.ArrayList;

/**
 * Created by osvaldo on 10/18/14.
 */
public class FavoritoAdapter extends ArrayAdapter<Favorito> {

    public FavoritoAdapter(Context context, ArrayList<Favorito> favoritos) {
        super(context, 0, favoritos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Favorito favorito = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_favoritos, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.placeholder);

        TextView fav_clave = (TextView) convertView.findViewById(R.id.item_clave);
        fav_clave.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        TextView fav_titulo = (TextView) convertView.findViewById(R.id.item_titulo);
        fav_titulo.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        /*TextView fav_fecha = (TextView) convertView.findViewById(R.id.item_fecha);
        fav_fecha.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));*/

        fav_clave.setText(favorito.getClave());
        fav_titulo.setText(favorito.getTitulo());
        // fav_fecha.setText(favorito.getFecha());

        return convertView;
    }
}

package com.oracle.dgnmovil.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oracle.dgnmovil.app.NormActivity;
import com.oracle.dgnmovil.app.R;
import com.oracle.dgnmovil.app.SearchActivity;
import com.oracle.dgnmovil.app.model.Favorito;

import java.util.ArrayList;

/**
 * Created by osvaldo on 10/18/14.
 */
public class FavoritoAdapter extends ArrayAdapter<Favorito> {

    Context context;

    public FavoritoAdapter(Context context, ArrayList<Favorito> favoritos) {
        super(context, 0, favoritos);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Favorito favorito = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_favoritos, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.placeholder);
        String name = favorito.getImg().toLowerCase().replaceAll(" ", "_").replaceAll(",", "").replace("-", "");
        int resID = context.getResources().getIdentifier(name, "drawable",  context.getPackageName());
        if(resID == 0)
            resID = R.drawable.ic_dgn_ico00;
        image.setImageResource(resID);

        TextView fav_clave = (TextView) convertView.findViewById(R.id.item_clave);
        fav_clave.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        TextView fav_titulo = (TextView) convertView.findViewById(R.id.item_titulo);
        fav_titulo.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        /*TextView fav_fecha = (TextView) convertView.findViewById(R.id.item_fecha);
        fav_fecha.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));*/

        fav_clave.setText(favorito.getClave());
        fav_titulo.setText(favorito.getTitulo());
        // fav_fecha.setText(favorito.getFecha());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NormActivity.class);
                String[] attributes = favorito.getStringAttributes();
                int fav = favorito.getFavorito();
                long id =favorito.getId();
                intent.putExtra(SearchActivity.NORMA_ATTRIBUTES, attributes);
                intent.putExtra(SearchActivity.NORMA_FAVORITE, fav);
                intent.putExtra(SearchActivity.NORMA_ID, id);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}

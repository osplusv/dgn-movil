package com.oracle.dgnmovil.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oracle.dgnmovil.app.R;
import com.oracle.dgnmovil.app.model.Reporte;

import java.util.List;

/**
 * Created by osvaldo on 10/19/14.
 */
public class ReporteItemAdapter extends ArrayAdapter<Reporte> {

    public ReporteItemAdapter(Context context, List<Reporte> reportes) {
        super(context, 0, reportes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reporte reporte = getItem(position);

        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reporte, parent, false);
        }

        ImageView reporte_img = (ImageView) convertView.findViewById(R.id.reporte_producto_img);

        TextView reporte_producto = (TextView) convertView.findViewById(R.id.reporte_producto);
        reporte_producto.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        TextView reporte_comentarios = (TextView) convertView.findViewById(R.id.reporte_comentario);
        reporte_comentarios.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/MavenPro-Bold.ttf"));

        reporte_producto.setText(reporte.getProducto());
        reporte_comentarios.setText(reporte.getComentario());

        reporte_img.setImageBitmap(decodeBase64String(reporte.getImg()));

        return convertView;
    }

    private Bitmap decodeBase64String(String input) {
        byte[] decodedString = Base64.decode(input, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return  decodedByte;
    }
}

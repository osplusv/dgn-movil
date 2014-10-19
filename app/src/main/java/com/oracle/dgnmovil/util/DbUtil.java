package com.oracle.dgnmovil.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.oracle.dgnmovil.app.data.DgnContract;
import com.oracle.dgnmovil.app.data.DgnDbHelper;
import com.oracle.dgnmovil.app.model.Favorito;

import java.util.ArrayList;

/**
 * Created by osvaldo on 10/19/14.
 */
public class DbUtil {
    private Context mContext;
    SQLiteDatabase db;
    DgnDbHelper dbHelper;

    public DbUtil(Context context) {
        mContext = context;
        dbHelper = new DgnDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void setNormaPreference(long norma_id, int preference) {
        ContentValues values = new ContentValues();
        values.put(DgnContract.NormasEntry.COLUMN_FAV, preference);

        int cursor = mContext.getContentResolver().update(
                DgnContract.NormasEntry.CONTENT_URI,
                values,
                DgnContract.NormasEntry._ID + " = ?",
                new String[] { norma_id + "" }
        );
    }

    public ArrayList<Favorito> getFavorites() {
        ArrayList<Favorito> favoritos = new ArrayList<Favorito>();

        Cursor cursor = db.query(
                true,
                DgnContract.NormasEntry.TABLE_NAME,
                null,
                DgnContract.NormasEntry.COLUMN_FAV + " = ?",
                new String[]{ 1 + ""},
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Favorito fav = new Favorito();

            int claveIndex = cursor.getColumnIndex(DgnContract.NormasEntry.COLUMN_CLAVE);
            String clave = cursor.getString(claveIndex);
            fav.setClave(clave);

            int tituloIndex = cursor.getColumnIndex(DgnContract.NormasEntry.COLUMN_TITULO);
            String titulo = cursor.getString(tituloIndex);
            fav.setTitulo(titulo);

            int publicacionIndex = cursor.getColumnIndex(DgnContract.NormasEntry.COLUMN_PUB);
            String publicacion = cursor.getString(publicacionIndex);
            fav.setFecha(publicacion);

            int raeIndex = cursor.getColumnIndex(DgnContract.NormasEntry.COLUMN_RAE_KEY);
            long rae_id = cursor.getInt(raeIndex);

            Cursor c2 = mContext.getContentResolver().query(
                    DgnContract.RaeEntry.buildRaeUri((rae_id)),
                    null,
                    null,
                    null,
                    null
            );
            if (c2.moveToFirst()) {
                int imgIndex = c2.getColumnIndex(DgnContract.RaeEntry.COLUMN_NOM);
                String img = c2.getString(imgIndex);
                fav.setImg(img);
            }
            c2.close();
            favoritos.add(fav);
        }
        return favoritos;
    }
}

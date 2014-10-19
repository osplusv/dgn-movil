package com.oracle.dgnmovil.app.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.oracle.dgnmovil.app.data.DgnContract.NormasEntry;
import com.oracle.dgnmovil.app.data.DgnContract.ProductosEntry;
import com.oracle.dgnmovil.app.data.DgnContract.RaeEntry;
import com.oracle.dgnmovil.app.model.Norma;
import com.oracle.dgnmovil.app.model.Producto;
import com.oracle.dgnmovil.app.model.Rae;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by osvaldo on 10/18/14.
 *
 */
public class FetchSearchTask extends AsyncTask<String, Void, Map<String, List<Object>>>  {

    private final String LOG_TAG = FetchSearchTask.class.getSimpleName();
    private Context mContext;
    private Map<String, List<Object>> mMap;
    public static final String NORMA = "norma";
    public static final String PRODUCTO = "producto";
    public static final String RAE = "rae";
    SQLiteDatabase db;
    DgnDbHelper dbHelper;

    public FetchSearchTask(Context context) {
        mContext = context;
        dbHelper = new DgnDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    protected Map<String, List<Object>> doInBackground(String... query) {
        mMap = new HashMap<String, List<Object>>();
        String val = "%" + query[0] + "%";
        searchByNorma(val);
        searchByProducto(val);
        searchByRae(val);
        return mMap;
    }

    private void searchByNorma(String val) {
        List<Object> lNorma = new ArrayList<Object>();

        Cursor cursor = db.query(
                true,
                NormasEntry.TABLE_NAME,
                null,
                NormasEntry.COLUMN_CLAVE + " LIKE ? or " + NormasEntry.COLUMN_TITULO + " LIKE ? LIMIT 7",
                new String[] { val, val },
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Norma norma = new Norma();

            int claveIndex = cursor.getColumnIndex(NormasEntry.COLUMN_CLAVE);
            String clave = cursor.getString(claveIndex);
            norma.setClave(clave);

            int tituloIndex = cursor.getColumnIndex(NormasEntry.COLUMN_TITULO);
            String titulo = cursor.getString(tituloIndex);
            norma.setTitulo(titulo);

            int vigorIndex = cursor.getColumnIndex(NormasEntry.COLUMN_ACT);
            String vigor = cursor.getString(vigorIndex);
            norma.setFecha(vigor);

            int raeIndex = cursor.getColumnIndex(NormasEntry.COLUMN_RAE_KEY);
            long rae_id = cursor.getInt(raeIndex);

            Cursor c2 = mContext.getContentResolver().query(
                    RaeEntry.buildRaeUri((rae_id)),
                    null,
                    null,
                    null,
                    null
            );
            if (c2.moveToFirst()) {
                int imgIndex = c2.getColumnIndex(RaeEntry.COLUMN_NOM);
                String img = c2.getString(imgIndex);
                norma.setImg(img);
            }
            c2.close();
            lNorma.add(norma);
        }
        cursor.close();
        mMap.put(NORMA, lNorma);
    }

    private void searchByProducto(String val) {
        List<Object> lProducto = new ArrayList<Object>();

        Cursor cursor = mContext.getContentResolver().query(
                ProductosEntry.CONTENT_URI,
                null,
                ProductosEntry.COLUMN_NOM + " LIKE ?",
                new String[] { val },
                null
        );

        while (cursor.moveToNext()) {
            Producto p = new Producto();
            List<String> imgs = new ArrayList<String>();

            int claveIndex = cursor.getColumnIndex(ProductosEntry._ID);
            long prod_id = cursor.getLong(claveIndex);

            int nombreIndex = cursor.getColumnIndex(ProductosEntry.COLUMN_NOM);
            String nombre = cursor.getString(nombreIndex);
            p.setNombre(nombre);

            Cursor c2 = mContext.getContentResolver().query(
                    DgnContract.ProdRaeEntry.buildProductoByRae(Long.toString(prod_id)),
                    null,
                    null,
                    null,
                    null
            );

            while (c2.moveToNext()) {
                int imgIndex = c2.getColumnIndex(DgnContract.ProdRaeEntry.COLUMN_IMG);
                String img = c2.getString(imgIndex);
                imgs.add(img);
                Log.v(LOG_TAG, img);
            }
            c2.close();
            p.setImg(imgs);
            long num = DatabaseUtils.queryNumEntries(
                    db,
                    NormasEntry.TABLE_NAME,
                    NormasEntry.COLUMN_PROD_KEY + " = ?",
                    new String[] { Long.toString(prod_id)}
            );
            p.setNumNormas(num);
            lProducto.add(p);
            Log.v(LOG_TAG, num + "");
        }
        mMap.put(PRODUCTO, lProducto);
        cursor.close();
    }

    private void searchByRae(String val) {
        List<Object> lRae = new ArrayList<Object>();

        Cursor cursor = db.query(
                true,
                RaeEntry.TABLE_NAME,
                null,
                RaeEntry.COLUMN_NOM + " LIKE ?",
                new String[] { val },
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Rae r = new Rae();

            int raeIndex = cursor.getColumnIndex(RaeEntry._ID);
            long rae_id = cursor.getLong(raeIndex);

            int nombreIndex = cursor.getColumnIndex(RaeEntry.COLUMN_NOM);
            String nombre = cursor.getString(nombreIndex);
            r.setNombre(nombre);
            r.setImg(nombre);

            long num = DatabaseUtils.queryNumEntries(
                    db,
                    NormasEntry.TABLE_NAME,
                    NormasEntry.COLUMN_RAE_KEY + " = ?",
                    new String[] { Long.toString(rae_id) }
            );

            r.setNumNormas(num);
            lRae.add(r);
        }
        mMap.put(RAE, lRae);
        cursor.close();
    }
}

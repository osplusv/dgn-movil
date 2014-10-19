package com.oracle.dgnmovil.app.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.oracle.dgnmovil.app.data.DgnContract.NormasEntry;
import com.oracle.dgnmovil.app.data.DgnContract.ProductosEntry;
import com.oracle.dgnmovil.app.data.DgnContract.RaeEntry;
import com.oracle.dgnmovil.app.model.Norma;
import com.oracle.dgnmovil.app.model.Producto;
import com.oracle.dgnmovil.app.model.Rae;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        //possiblesRae(val);
        return mMap;
    }

    private void searchByNorma(String val) {
        List<Object> lNorma = new ArrayList<Object>();

        Cursor cursor = db.query(
                true,
                NormasEntry.TABLE_NAME,
                null,
                NormasEntry.COLUMN_CLAVE + " LIKE ? or " + NormasEntry.COLUMN_TITULO + " LIKE ? LIMIT 10",
                new String[] { val, val },
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Norma norma = new Norma();

            int idIndex = cursor.getColumnIndex(NormasEntry._ID);
            long id = cursor.getLong(idIndex);
            norma.setId(id);

            int claveIndex = cursor.getColumnIndex(NormasEntry.COLUMN_CLAVE);
            String clave = cursor.getString(claveIndex);
            norma.setClave(clave);

            int tituloIndex = cursor.getColumnIndex(NormasEntry.COLUMN_TITULO);
            String titulo = cursor.getString(tituloIndex);
            norma.setTitulo(titulo);

            int publicacionIndex = cursor.getColumnIndex(NormasEntry.COLUMN_PUB);
            String publicacion = cursor.getString(publicacionIndex);
            norma.setPublicacion(publicacion);

            int vigorIndex = cursor.getColumnIndex(NormasEntry.COLUMN_ACT);
            String vigor = cursor.getString(vigorIndex);
            norma.setFecha(vigor);

            int tipoIndex = cursor.getColumnIndex(NormasEntry.COLUMN_TIPO);
            String tipo = cursor.getString(tipoIndex);
            norma.setTipo(tipo);

            int norma_internacional_Index = cursor.getColumnIndex(NormasEntry.COLUMN_INTER);
            String norma_internacional = cursor.getString(norma_internacional_Index);
            norma.setNorma_internacional(norma_internacional);

            int concordanciaIndex = cursor.getColumnIndex(NormasEntry.COLUMN_CONC);
            String concordancia = cursor.getString(concordanciaIndex);
            norma.setConcordancia(concordancia);

            int documentoIndex = cursor.getColumnIndex(NormasEntry.COLUMN_DOC);
            String documento = cursor.getString(documentoIndex);
            norma.setDocumento(documento);

            int favoritoIndex = cursor.getColumnIndex(NormasEntry.COLUMN_FAV);
            int favorito = cursor.getInt(favoritoIndex);
            norma.setFavorito(favorito);

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
                ProductosEntry.COLUMN_NOM + " LIKE ? LIMIT 10",
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
                // Log.v(LOG_TAG, img);
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
            // Log.v(LOG_TAG, num + "");
        }
        mMap.put(PRODUCTO, lProducto);
        cursor.close();
    }

    private void searchByRae(String val) {
        int max = 10;
        Set<String> uRae = new HashSet<String>();
        List<Object> lRae = new ArrayList<Object>();

        /* Artificial Intelligence */

        String query = "select distinct r.id, r.nombre from rae r INNER JOIN normas n ON n.rae_id = r.id WHERE n.titulo like '" + val +"' LIMIT 10";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int nombreIndex = cursor.getColumnIndex(DgnContract.RaeEntry.COLUMN_NOM);
            int idIndex = cursor.getColumnIndex(RaeEntry._ID);

            lRae.add(new Rae(cursor.getLong(idIndex), cursor.getString(nombreIndex)));
            uRae.add(cursor.getString(nombreIndex));
        }

        cursor.close();
        /****************************/

        if (lRae.size() < 10) {
            max = max - lRae.size();
            Cursor cursor2 = db.query(
                    true,
                    RaeEntry.TABLE_NAME,
                    null,
                    RaeEntry.COLUMN_NOM + " LIKE ? LIMIT " + max,
                    new String[] { val },
                    null,
                    null,
                    null,
                    null
            );

            while (cursor2.moveToNext()) {
                Rae r = new Rae();

                int raeIndex = cursor2.getColumnIndex(RaeEntry._ID);
                long rae_id = cursor2.getLong(raeIndex);

                int nombreIndex = cursor2.getColumnIndex(RaeEntry.COLUMN_NOM);
                String nombre = cursor2.getString(nombreIndex);

                if (uRae.add(nombre)) {
                    r.setNombre(nombre);
                    r.setImg(nombre);

                    lRae.add(r);
                }
            }
            cursor2.close();
        }


        int size = lRae.size();
        for (int i = 0; i < size; i++) {
            Rae r = (Rae)lRae.get(i);

            long num = DatabaseUtils.queryNumEntries(
                    db,
                    NormasEntry.TABLE_NAME,
                    NormasEntry.COLUMN_RAE_KEY + " = ?",
                    new String[] { Long.toString(r.getId()) }
            );

            r.setNumNormas(num);
        }

        mMap.put(RAE, lRae);
    }

    /*
    public void possiblesRae(String val) {
        boolean f = false;
        List<Object> prod = mMap.get(PRODUCTO);
        String query = "select distinct r.nombre from rae r INNER JOIN normas n ON n.rae_id = r.id WHERE n.titulo like '" + val + "'";

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int nombreIndex = cursor.getColumnIndex(RaeEntry.COLUMN_NOM);
            String nombre = cursor.getString(nombreIndex);

            for (int i = 0; i < prod.size(); i++) {
                if (((Producto) prod.get(i)).getNombre() == nombre) {
                    f = true;
                    break;
                }
            }
            if (!f) {
                prod.add(new Producto(nombre));
            }
        }
        cursor.close();
        mMap.put(PRODUCTO, prod);
    }
    */
}

package com.oracle.dgnmovil.app;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.oracle.dgnmovil.app.data.DgnContract.DependenciasEntry;
import com.oracle.dgnmovil.app.data.DgnContract.NormasEntry;
import com.oracle.dgnmovil.app.data.DgnContract.OrganizmosEntry;
import com.oracle.dgnmovil.app.data.DgnContract.ProductosEntry;
import com.oracle.dgnmovil.app.data.DgnContract.RaeEntry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Vector;

/**
 * Created by osvaldo on 10/16/14.
 */
public class FetchFilesTask extends AsyncTask<Void, Void, String> {
    private final String LOG_TAG = FetchFilesTask.class.getSimpleName();
    Context mContext;

    public FetchFilesTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Vector<ContentValues> cVVectorProd = new Vector<ContentValues>();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL("http://www.applicate.mx/en/Applicate/Repositorio_de_descargas/" +
                    "_rid/273/_mto/3/_act/download/doc/catanmx.csv");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            Log.v(LOG_TAG, "Built Uri " + url.toString());

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();

            parseDocument(forecastJsonStr);

            Log.v(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            forecastJsonStr = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    private void parseDocument(String input) {
        Vector<ContentValues> cVVector = new Vector<ContentValues>();
        try {
            final CSVParser parser = new CSVParser(new StringReader(input), CSVFormat.EXCEL.withHeader());
            Map<String, Integer> map = parser.getHeaderMap();

            for (CSVRecord record : parser) {
                ContentValues normaValues = new ContentValues();
                Long dependenciaID = null;
                Long organizmosID = null;

                if (map.containsKey("F. ENTRADA EN VIGOR")) {
                    normaValues.put(NormasEntry.COLUMN_ACT, record.get("F. ENTRADA EN VIGOR"));
                }
                if (map.containsKey("NORMA INTERNACIONAL")) {
                    normaValues.put(NormasEntry.COLUMN_INTER, record.get("NORMA INTERNACIONAL"));
                }
                if (map.containsKey("CONCORDANCIA")) {
                    normaValues.put(NormasEntry.COLUMN_CONC, record.get("CONCORDANCIA"));
                }
                if (map.containsKey("DEPENDENCIA")) {
                    dependenciaID = addElement(record.get("DEPENDENCIA"), DependenciasEntry.CONTENT_URI, DependenciasEntry._ID, DependenciasEntry.COLUMN_NOM);
                }
                if (map.containsKey("CCNN")) {
                    organizmosID = addElement(record.get("CCNN"), OrganizmosEntry.CONTENT_URI, OrganizmosEntry._ID, OrganizmosEntry.COLUMN_NOM);
                } else {
                    organizmosID = addElement(record.get("CTNN"), OrganizmosEntry.CONTENT_URI, OrganizmosEntry._ID, OrganizmosEntry.COLUMN_NOM);
                }


                long productoID = addElement(record.get("PRODUCTO"), ProductosEntry.CONTENT_URI, ProductosEntry._ID, ProductosEntry.COLUMN_NOM);
                long raeID = addElement(record.get("RAE"), RaeEntry.CONTENT_URI, RaeEntry._ID, RaeEntry.COLUMN_NOM);


                normaValues.put(NormasEntry.COLUMN_CLAVE, record.get("CLAVE DE LA NORMA"));
                normaValues.put(NormasEntry.COLUMN_TITULO, record.get("TITULO"));
                normaValues.put(NormasEntry.COLUMN_PUB, record.get("F. PUBLICACIÓN"));
                normaValues.put(NormasEntry.COLUMN_TIPO, record.get("TIPO NORMA"));
                normaValues.put(NormasEntry.COLUMN_DOC, record.get("DOCUMENTO"));

                normaValues.put(NormasEntry.COLUMN_PROD_KEY, productoID);
                normaValues.put(NormasEntry.COLUMN_RAE_KEY, raeID);
                normaValues.put(NormasEntry.COLUMN_DEP_KEY, dependenciaID);
                normaValues.put(NormasEntry.COLUMN_ORG_KEY, organizmosID);

                cVVector.add(normaValues);
            }
        } catch (IOException e) {

        }
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            int rowsInserted = mContext.getContentResolver()
                    .bulkInsert(NormasEntry.CONTENT_URI, cvArray);
            Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of normas data");
        }
    }

    private long addElement(String name, Uri uri, String columnId, String columnName) {
        Cursor cursor = mContext.getContentResolver().query(
                uri,
                new String[] {columnId},
                columnName + " = ?",
                new String[] {name},
                null
        );

        if (cursor.moveToFirst()) {
            //Log.v(LOG_TAG, "Found it in database - " + name);
            int elementIdIndex = cursor.getColumnIndex(columnId);
            long id = cursor.getLong(elementIdIndex);
            cursor.close();
            return id;
        } else {
            //Log.v(LOG_TAG, "Didn't find it in the database, inserting now");
            ContentValues elementsValues = new ContentValues();
            elementsValues.put(columnName, name);

            Uri productoInsertUri = mContext.getContentResolver().insert(
                    uri,
                    elementsValues
            );

            cursor.close();
            return ContentUris.parseId(productoInsertUri);
        }
    }
}

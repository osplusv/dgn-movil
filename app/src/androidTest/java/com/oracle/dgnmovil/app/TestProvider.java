package com.oracle.dgnmovil.app;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.oracle.dgnmovil.app.data.DgnContract.DependenciasEntry;
import com.oracle.dgnmovil.app.data.DgnContract.NormasEntry;
import com.oracle.dgnmovil.app.data.DgnContract.OrganizmosEntry;
import com.oracle.dgnmovil.app.data.DgnContract.ProductosEntry;
import com.oracle.dgnmovil.app.data.DgnContract.RaeEntry;
import com.oracle.dgnmovil.app.data.DgnDbHelper;

import java.util.Map;
import java.util.Set;

/**
 * Created by osvaldo on 10/16/14.
 */
public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void testInsertReadProvider() {
        // Delete first the database
        // mContext.deleteDatabase(DgnDbHelper.DATABASE_NAME);

        // Test data we're going to insert into the DB to see if it works.
        DgnDbHelper dbHelper = new DgnDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues productosValues = createProductosValues();

        Uri insertUri= mContext.getContentResolver().insert(ProductosEntry.CONTENT_URI, productosValues);
        long prodRowId = ContentUris.parseId(insertUri);

        // A cursor is your primary interface to the query results
        Cursor productosCursor = mContext.getContentResolver().query(
                ProductosEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        validateCursor(productosCursor, productosValues);

        productosCursor = mContext.getContentResolver().query(
                ProductosEntry.buildProductoUri(prodRowId),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        validateCursor(productosCursor, productosValues);

        // Create a new map of values, where column names are the keys
        ContentValues raeValues = createRaeValues();

        long raeRowId;
        raeRowId = db.insert(RaeEntry.TABLE_NAME, null, raeValues);

        // Verify we got a row back
        assertTrue(raeRowId != -1);
        Log.d(LOG_TAG, "New row id: " + raeRowId);

        // A cursor is your primary interface to the query results
        Cursor raeCursor = db.query(
                RaeEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(raeCursor, raeValues);

        // Create a new map of values, where column names are the keys
        ContentValues dependenciasValues = createDependenciasValues();

        long dependenciasRowId;
        dependenciasRowId = db.insert(DependenciasEntry.TABLE_NAME, null, dependenciasValues);

        // Verify we got a row back
        assertTrue(dependenciasRowId != -1);
        Log.d(LOG_TAG, "New row id: " + dependenciasRowId);

        // A cursor is your primary interface to the query results
        Cursor dependeciasCursor = db.query(
                DependenciasEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(dependeciasCursor, dependenciasValues);

        // Create a new map of values, where column names are the keys
        ContentValues organizmosValues = createOrganizmosValues();

        long organizmosRowId;
        organizmosRowId = db.insert(OrganizmosEntry.TABLE_NAME, null, organizmosValues);

        // Verify we got a row back
        assertTrue(organizmosRowId != -1);
        Log.d(LOG_TAG, "New row id: " + organizmosRowId);

        // A cursor is your primary interface to the query results
        Cursor organizmosCursor = db.query(
                OrganizmosEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(organizmosCursor, organizmosValues);

        // Create a new map of values, where column names are the keys
        ContentValues normasValues = createNormasValues(prodRowId,raeRowId,dependenciasRowId,organizmosRowId);

        insertUri = mContext.getContentResolver().insert(NormasEntry.CONTENT_URI, normasValues);
        long normaRowId = ContentUris.parseId(insertUri);

        // A cursor is your primary interface to the query results
        Cursor normaCursor = mContext.getContentResolver().query(
                NormasEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (normaCursor.moveToFirst()) {
            validateCursor(normaCursor, normasValues);
        } else {
            fail("No normas data returned!");
        }
        normaCursor.close();

        normaCursor = mContext.getContentResolver().query(
                NormasEntry.buildNormaByElement("producto", "AGUA"),
                null,
                null,
                null,
                null
        );

        if (normaCursor.moveToFirst()) {
            validateCursor(normaCursor, normasValues);
        } else {
            fail("No normas data returned!");
        }
        normaCursor.close();

        dbHelper.close();
    }

    static ContentValues createNormasValues(long prodRowId, long raeRowId, long dependenciasRowId,
                                            long organizmosRowId) {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(NormasEntry.COLUMN_CLAVE, "NOM-001-CONAGUA-2011");
        testValues.put(NormasEntry.COLUMN_TITULO, "SISTEMAS DE AGUA POTABLE, TOMA DOMICILIARIA Y ALCANTARILLADO SANITARIO-HERMETICIDAD-ESPECIFICACIONES Y MÉTODOS DE PRUEBA.");
        testValues.put(NormasEntry.COLUMN_PUB, "2012-02-17T00:00:00Z");
        testValues.put(NormasEntry.COLUMN_ACT, "2012-06-16T00:00:00Z");
        testValues.put(NormasEntry.COLUMN_TIPO, "DEFINITIVA");
        testValues.put(NormasEntry.COLUMN_INTER, "ESTA NORMA OFICIAL MEXICANA NO COINCIDE CON NINGUNA NORMA INTERNACIONAL");
        testValues.put(NormasEntry.COLUMN_CONC, "NO APLICA");
        testValues.put(NormasEntry.COLUMN_DOC, "http://www.economia-noms.gob.mx/normas/noms/2010/001conagua2012.pdf");
        testValues.put(NormasEntry.COLUMN_PROD_KEY, prodRowId);
        testValues.put(NormasEntry.COLUMN_RAE_KEY, raeRowId);
        testValues.put(NormasEntry.COLUMN_DEP_KEY, dependenciasRowId);
        testValues.put(NormasEntry.COLUMN_ORG_KEY, organizmosRowId);
        return testValues;
    }

    static ContentValues createProductosValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(ProductosEntry.COLUMN_NOM, "AGUA");
        return testValues;
    }

    static ContentValues createRaeValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(RaeEntry.COLUMN_NOM, "AGUA Y SUMINISTRO DE GAS POR DUCTOS");
        return testValues;
    }

    static ContentValues createDependenciasValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(DependenciasEntry.COLUMN_NOM, "SEMARNAT");
        return testValues;
    }

    static ContentValues createOrganizmosValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(OrganizmosEntry.COLUMN_NOM, "CCNN DEL SECTOR AGUA (CONAGUA)");
        return testValues;
    }

    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();
    }
}

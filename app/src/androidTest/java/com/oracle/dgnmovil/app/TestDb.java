package com.oracle.dgnmovil.app;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.oracle.dgnmovil.app.data.DgnDbHelper;

/**
 * Created by osvaldo on 10/16/14.
 */
public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    public void testCreateDb() throws Throwable {
        //mContext.deleteDatabase(DgnDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new DgnDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    /*public void testInsertReadDb() {

        // Test data we're going to insert into the DB to see if it works.
            String testProd = "AGUA";
        String testRae = "AGUA Y SUMINISTRO DE GAS POR DUCTOS";
        String testDependecias = "SEMARNAT";
        String testOrganizmos = "CCNN DEL SECTOR AGUA (CONAGUA)";

        String testClave = "NOM-001-CONAGUA-2011";
        String testTitulo = "SISTEMAS DE AGUA POTABLE, TOMA DOMICILIARIA Y ALCANTARILLADO SANITARIO-HERMETICIDAD-ESPECIFICACIONES Y MÉTODOS DE PRUEBA.";
        String testPub = "2012-02-17T00:00:00Z";
        String testAct = "2012-06-16T00:00:00Z";
        String testTipo = "DEFINITIVA";
        String testInter = "ESTA NORMA OFICIAL MEXICANA NO COINCIDE CON NINGUNA NORMA INTERNACIONAL";
        String testConc = "NO APLICA";
        String testDoc = "http://www.economia-noms.gob.mx/normas/noms/2010/001conagua2012.pdf";

        DgnDbHelper dbHelper = new DgnDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues productosValues = new ContentValues();
        productosValues.put(ProductosEntry.COLUMN_NOM, testProd);

        long prodRowId;
        prodRowId = db.insert(ProductosEntry.TABLE_NAME, null, productosValues);

        // Verify we got a row back
        assertTrue(prodRowId != -1);
        Log.d(LOG_TAG, "New row id: " + prodRowId);

        // Retrieve data
        // Specify which columns you want.
        String[] productosColumns = {
                ProductosEntry._ID,
                ProductosEntry.COLUMN_NOM
        };

        // A cursor is your primary interface to the query results
        Cursor productosCursor = db.query(
                ProductosEntry.TABLE_NAME,  // Table to Query
                productosColumns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // If possible, move to the first row of the query results
        if (productosCursor.moveToFirst()) {
            // Get the value in each column by finding the appropriate column index.
            int nombreIndex = productosCursor.getColumnIndex(ProductosEntry.COLUMN_NOM);
            String nombre = productosCursor.getString(nombreIndex);

            assertEquals(testProd, nombre);
        } else {
            // That's weird, it works on MY machine...
            fail("No values returned :(");
        }

        // Create a new map of values, where column names are the keys
        ContentValues raeValues = new ContentValues();
        raeValues.put(RaeEntry.COLUMN_NOM, testRae);

        long raeRowId;
        raeRowId = db.insert(RaeEntry.TABLE_NAME, null, raeValues);

        // Verify we got a row back
        assertTrue(raeRowId != -1);
        Log.d(LOG_TAG, "New row id: " + raeRowId);

        // Retrieve data
        // Specify which columns you want.
        String[] raeColumns = {
                RaeEntry._ID,
                RaeEntry.COLUMN_NOM
        };

        // A cursor is your primary interface to the query results
        Cursor raeCursor = db.query(
                RaeEntry.TABLE_NAME,  // Table to Query
                raeColumns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // If possible, move to the first row of the query results
        if (raeCursor.moveToFirst()) {
            // Get the value in each column by finding the appropriate column index.
            int nombreIndex = raeCursor.getColumnIndex(RaeEntry.COLUMN_NOM);
            String nombre = raeCursor.getString(nombreIndex);

            assertEquals(testRae, nombre);
        } else {
            // That's weird, it works on MY machine...
            fail("No values returned :(");
        }

        // Create a new map of values, where column names are the keys
        ContentValues dependenciasValues = new ContentValues();
        dependenciasValues.put(DependenciasEntry.COLUMN_NOM, testDependecias);

        long dependenciasRowId;
        dependenciasRowId = db.insert(DependenciasEntry.TABLE_NAME, null, dependenciasValues);

        // Verify we got a row back
        assertTrue(dependenciasRowId != -1);
        Log.d(LOG_TAG, "New row id: " + dependenciasRowId);

        // Retrieve data
        // Specify which columns you want.
        String[] dependenciasColumns = {
                DependenciasEntry._ID,
                DependenciasEntry.COLUMN_NOM
        };

        // A cursor is your primary interface to the query results
        Cursor dependeciasCursor = db.query(
                DependenciasEntry.TABLE_NAME,  // Table to Query
                dependenciasColumns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // If possible, move to the first row of the query results
        if (dependeciasCursor.moveToFirst()) {
            // Get the value in each column by finding the appropriate column index.
            int nombreIndex = dependeciasCursor.getColumnIndex(DependenciasEntry.COLUMN_NOM);
            String nombre = dependeciasCursor.getString(nombreIndex);

            assertEquals(testDependecias, nombre);
        } else {
            // That's weird, it works on MY machine...
            fail("No values returned :(");
        }

        // Create a new map of values, where column names are the keys
        ContentValues organizmosValues = new ContentValues();
        organizmosValues.put(OrganizmosEntry.COLUMN_NOM, testOrganizmos);

        long organizmosRowId;
        organizmosRowId = db.insert(OrganizmosEntry.TABLE_NAME, null, organizmosValues);

        // Verify we got a row back
        assertTrue(organizmosRowId != -1);
        Log.d(LOG_TAG, "New row id: " + organizmosRowId);

        // Retrieve data
        // Specify which columns you want.
        String[] organizmosColumns = {
                OrganizmosEntry._ID,
                OrganizmosEntry.COLUMN_NOM
        };

        // A cursor is your primary interface to the query results
        Cursor organizmosCursor = db.query(
                OrganizmosEntry.TABLE_NAME,  // Table to Query
                organizmosColumns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // If possible, move to the first row of the query results
        if (organizmosCursor.moveToFirst()) {
            // Get the value in each column by finding the appropriate column index.
            int nombreIndex = organizmosCursor.getColumnIndex(OrganizmosEntry.COLUMN_NOM);
            String nombre = organizmosCursor.getString(nombreIndex);

            assertEquals(testOrganizmos, nombre);
        } else {
            // That's weird, it works on MY machine...
            fail("No values returned :(");
        }

        // Create a new map of values, where column names are the keys
        ContentValues normasValues = new ContentValues();
        normasValues.put(NormasEntry.COLUMN_CLAVE, testClave);
        normasValues.put(NormasEntry.COLUMN_TITULO, testTitulo);
        normasValues.put(NormasEntry.COLUMN_PUB, testPub);
        normasValues.put(NormasEntry.COLUMN_ACT, testAct);
        normasValues.put(NormasEntry.COLUMN_TIPO, testTipo);
        normasValues.put(NormasEntry.COLUMN_INTER, testInter);
        normasValues.put(NormasEntry.COLUMN_CONC, testConc);
        normasValues.put(NormasEntry.COLUMN_DOC, testDoc);
        normasValues.put(NormasEntry.COLUMN_PROD_KEY, prodRowId);
        normasValues.put(NormasEntry.COLUMN_RAE_KEY, raeRowId);
        normasValues.put(NormasEntry.COLUMN_DEP_KEY, dependenciasRowId);
        normasValues.put(NormasEntry.COLUMN_ORG_KEY, organizmosRowId);

        long normaRowId;
        normaRowId = db.insert(NormasEntry.TABLE_NAME, null, normasValues);

        // Verify we got a row back
        assertTrue(normaRowId != -1);
        Log.d(LOG_TAG, "New row id: " + normaRowId);

        // Retrieve data
        // Specify which columns you want.
        String[] normaColumns = {
                NormasEntry._ID,
                NormasEntry.COLUMN_CLAVE,
                NormasEntry.COLUMN_TITULO,
                NormasEntry.COLUMN_PUB,
                NormasEntry.COLUMN_ACT,
                NormasEntry.COLUMN_TIPO,
                NormasEntry.COLUMN_INTER,
                NormasEntry.COLUMN_CONC,
                NormasEntry.COLUMN_DOC,
                NormasEntry.COLUMN_PROD_KEY,
                NormasEntry.COLUMN_RAE_KEY,
                NormasEntry.COLUMN_DEP_KEY,
                NormasEntry.COLUMN_ORG_KEY
        };

        // A cursor is your primary interface to the query results
        Cursor normaCursor = db.query(
                NormasEntry.TABLE_NAME,  // Table to Query
                normaColumns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // If possible, move to the first row of the query results
        if (normaCursor.moveToFirst()) {
            // Get the value in each column by finding the appropriate column index.
            int claveIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_CLAVE);
            String clave = normaCursor.getString(claveIndex);

            int tituloIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_TITULO);
            String titulo = normaCursor.getString(tituloIndex);

            int pubIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_PUB);
            String pub = normaCursor.getString(pubIndex);

            int actIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_ACT);
            String act = normaCursor.getString(actIndex);

            int tipoIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_TIPO);
            String tipo = normaCursor.getString(tipoIndex);

            int interIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_INTER);
            String inter = normaCursor.getString(interIndex);

            int concIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_CONC);
            String conc = normaCursor.getString(concIndex);

            int docIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_DOC);
            String doc = normaCursor.getString(docIndex);

            int prodIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_PROD_KEY);
            int prod = normaCursor.getInt(prodIndex);

            int raeIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_RAE_KEY);
            int rae = normaCursor.getInt(raeIndex);

            int depIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_DEP_KEY);
            int dep = normaCursor.getInt(depIndex);

            int orgIndex = normaCursor.getColumnIndex(NormasEntry.COLUMN_ORG_KEY);
            int org = normaCursor.getInt(orgIndex);

            assertEquals(testClave, clave);
            assertEquals(testTitulo, titulo);
            assertEquals(testPub, pub);
            assertEquals(testAct, act);
            assertEquals(testTipo, tipo);
            assertEquals(testInter, inter);
            assertEquals(testConc, conc);
            assertEquals(testDoc, doc);
            assertEquals(prodRowId, prod);
            assertEquals(raeRowId, rae);
            assertEquals(dependenciasRowId, dep);
            assertEquals(organizmosRowId, org);
        } else {
            // That's weird, it works on MY machine...
            fail("No values returned :(");
        }
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
    }*/
}

package com.oracle.dgnmovil.app.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by osvaldo on 10/16/14.
 */
public class DgnProvider extends ContentProvider {
    private static final int NORMA = 100;
    private static final int NORMA_BY_ELEMENT = 101;
    private static final int PRODUCTO = 300;
    private static final int PRODUCTO_ID = 301;
    private static final int RAE = 400;
    private static final int RAE_ID = 401;
    private static final int DEPENDENCIA = 500;
    private static final int DEPENDENCIA_ID = 501;
    private static final int ORGANIZMO = 600;
    private static final int ORGANIZMO_ID = 601;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sNormaByProductoQueryBuilder;
    private static final SQLiteQueryBuilder sNormaByRaeQueryBuilder;
    private static final SQLiteQueryBuilder sNormaByDependenciasQueryBuilder;
    private static final SQLiteQueryBuilder sNormaByOrganizmosQueryBuilder;

    private DgnDbHelper mDbHelper;

    static {
        // Producto query
        sNormaByProductoQueryBuilder = new SQLiteQueryBuilder();
        sNormaByProductoQueryBuilder.setTables(
                DgnContract.NormasEntry.TABLE_NAME + " INNER JOIN " +
                        DgnContract.ProductosEntry.TABLE_NAME +
                        " ON " + DgnContract.NormasEntry.TABLE_NAME +
                        "." + DgnContract.NormasEntry.COLUMN_PROD_KEY +
                        " = " + DgnContract.ProductosEntry.TABLE_NAME +
                        "." + DgnContract.ProductosEntry._ID
        );

        // Rae query
        sNormaByRaeQueryBuilder = new SQLiteQueryBuilder();
        sNormaByRaeQueryBuilder.setTables(
                DgnContract.NormasEntry.TABLE_NAME + " INNER JOIN " +
                        DgnContract.RaeEntry.TABLE_NAME +
                        " ON " + DgnContract.RaeEntry.TABLE_NAME +
                        "." + DgnContract.NormasEntry.COLUMN_PROD_KEY +
                        " = " + DgnContract.RaeEntry.TABLE_NAME +
                        "." + DgnContract.RaeEntry._ID
        );

        // Dependecias query
        sNormaByDependenciasQueryBuilder = new SQLiteQueryBuilder();
        sNormaByDependenciasQueryBuilder.setTables(
                DgnContract.NormasEntry.TABLE_NAME + " INNER JOIN " +
                        DgnContract.DependenciasEntry.TABLE_NAME +
                        " ON " + DgnContract.DependenciasEntry.TABLE_NAME +
                        "." + DgnContract.NormasEntry.COLUMN_PROD_KEY +
                        " = " + DgnContract.DependenciasEntry.TABLE_NAME +
                        "." + DgnContract.DependenciasEntry._ID
        );

        // Organizmos query
        sNormaByOrganizmosQueryBuilder = new SQLiteQueryBuilder();
        sNormaByDependenciasQueryBuilder.setTables(
                DgnContract.NormasEntry.TABLE_NAME + " INNER JOIN " +
                        DgnContract.OrganizmosEntry.TABLE_NAME +
                        " ON " + DgnContract.OrganizmosEntry.TABLE_NAME +
                        "." + DgnContract.NormasEntry.COLUMN_PROD_KEY +
                        " = " + DgnContract.OrganizmosEntry.TABLE_NAME +
                        "." + DgnContract.OrganizmosEntry._ID
        );
    }

    private static final String sProductoSelection = DgnContract.ProductosEntry.TABLE_NAME +
            "." + DgnContract.ProductosEntry.COLUMN_NOM + " = ? ";

    private static final String sRaeSelection = DgnContract.RaeEntry.TABLE_NAME +
            "." + DgnContract.RaeEntry.COLUMN_NOM + " = ? ";

    private static final String sDependeciasSelection = DgnContract.DependenciasEntry.TABLE_NAME +
            "." + DgnContract.DependenciasEntry.COLUMN_NOM + " = ? ";

    private static final String sOrganizmosSelection = DgnContract.OrganizmosEntry.TABLE_NAME +
            "." + DgnContract.OrganizmosEntry.COLUMN_NOM + " = ? ";

    private Cursor getNormaByElement(Uri uri, String[] projection, String sortOrder) {
        String element = DgnContract.NormasEntry.getElementFromUri(uri);
        String value = DgnContract.NormasEntry.getValueFromUri(uri);

        String[] selectionArgs = new String[] { value };
        String selection = null;

        if (element.equals("producto")) {
            selection = sProductoSelection;
        } else if (element.equals("rae")) {
            selection = sRaeSelection;
        } else if (element.equals("dependecia")) {
            selection = sDependeciasSelection;
        } else {
            selection = sOrganizmosSelection;
        }

        return sNormaByProductoQueryBuilder.query(
                mDbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private static UriMatcher buildUriMatcher() {
        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DgnContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, DgnContract.PATH_NORMA, NORMA);
        matcher.addURI(authority, DgnContract.PATH_NORMA + "/*/*", NORMA_BY_ELEMENT);

        matcher.addURI(authority, DgnContract.PATH_PRODUCTO, PRODUCTO);
        matcher.addURI(authority, DgnContract.PATH_PRODUCTO + "/#", PRODUCTO_ID);

        matcher.addURI(authority, DgnContract.PATH_RAE, RAE);
        matcher.addURI(authority, DgnContract.PATH_RAE + "/#", RAE_ID);

        matcher.addURI(authority, DgnContract.PATH_DEPENDECIA, DEPENDENCIA);
        matcher.addURI(authority, DgnContract.PATH_DEPENDECIA + "/#", DEPENDENCIA_ID);

        matcher.addURI(authority, DgnContract.PATH_ORGANIZMO, ORGANIZMO);
        matcher.addURI(authority, DgnContract.PATH_ORGANIZMO + "/#", ORGANIZMO_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DgnDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "norma"
            case NORMA: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.NormasEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "norma/{element}/{value}"
            case NORMA_BY_ELEMENT: {
                retCursor = getNormaByElement(uri, projection, sortOrder);
                break;
            }

            // "producto"
            case PRODUCTO: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.ProductosEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            // "producto/id"
            case PRODUCTO_ID: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.ProductosEntry.TABLE_NAME,
                        projection,
                        DgnContract.ProductosEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case RAE: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.RaeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case RAE_ID: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.RaeEntry.TABLE_NAME,
                        projection,
                        DgnContract.RaeEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case DEPENDENCIA: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.DependenciasEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case DEPENDENCIA_ID: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.DependenciasEntry.TABLE_NAME,
                        projection,
                        DgnContract.DependenciasEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case ORGANIZMO: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.OrganizmosEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case ORGANIZMO_ID: {
                retCursor = mDbHelper.getReadableDatabase().query(
                        DgnContract.OrganizmosEntry.TABLE_NAME,
                        projection,
                        DgnContract.OrganizmosEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NORMA:
                return DgnContract.NormasEntry.CONTENT_TYPE;
            case NORMA_BY_ELEMENT:
                return DgnContract.NormasEntry.CONTENT_TYPE;
            case PRODUCTO:
                return DgnContract.ProductosEntry.CONTENT_TYPE;
            case PRODUCTO_ID:
                return DgnContract.ProductosEntry.CONTENT_ITEM_TYPE;
            case RAE:
                return DgnContract.RaeEntry.CONTENT_TYPE;
            case RAE_ID:
                return DgnContract.RaeEntry.CONTENT_ITEM_TYPE;
            case DEPENDENCIA:
                return DgnContract.DependenciasEntry.CONTENT_TYPE;
            case DEPENDENCIA_ID:
                return DgnContract.DependenciasEntry.CONTENT_ITEM_TYPE;
            case ORGANIZMO:
                return DgnContract.OrganizmosEntry.CONTENT_TYPE;
            case ORGANIZMO_ID:
                return DgnContract.OrganizmosEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case NORMA: {
                long _id = db.insert(DgnContract.NormasEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DgnContract.NormasEntry.buildNormaUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case PRODUCTO: {
                long _id = db.insert(DgnContract.ProductosEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DgnContract.ProductosEntry.buildProductoUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case RAE: {
                long _id = db.insert(DgnContract.RaeEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DgnContract.RaeEntry.buildRaeUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case DEPENDENCIA: {
                long _id = db.insert(DgnContract.DependenciasEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DgnContract.DependenciasEntry.buildDependenciaUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case ORGANIZMO: {
                long _id = db.insert(DgnContract.OrganizmosEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DgnContract.OrganizmosEntry.buildOrganizmoUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case NORMA:
                rowsDeleted = db.delete(
                        DgnContract.NormasEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCTO:
                rowsDeleted = db.delete(
                        DgnContract.ProductosEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RAE:
                rowsDeleted = db.delete(
                        DgnContract.RaeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DEPENDENCIA:
                rowsDeleted = db.delete(
                        DgnContract.DependenciasEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ORGANIZMO:
                rowsDeleted = db.delete(
                        DgnContract.OrganizmosEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case NORMA:
                rowsUpdated = db.update(DgnContract.NormasEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case PRODUCTO:
                rowsUpdated =  db.update(DgnContract.ProductosEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case RAE:
                rowsUpdated =  db.update(DgnContract.RaeEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case DEPENDENCIA:
                rowsUpdated =  db.update(DgnContract.DependenciasEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case ORGANIZMO:
                rowsUpdated =  db.update(DgnContract.OrganizmosEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NORMA: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DgnContract.NormasEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case PRODUCTO: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DgnContract.ProductosEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case RAE: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DgnContract.RaeEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case DEPENDENCIA: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DgnContract.DependenciasEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            case ORGANIZMO: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DgnContract.OrganizmosEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }
}

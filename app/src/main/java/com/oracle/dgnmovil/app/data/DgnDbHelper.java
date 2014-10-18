package com.oracle.dgnmovil.app.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by osvaldo on 10/15/14.
 */
public class DgnDbHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "dgn.db";
    private static final int DATABASE_VERSION = 1;

    public DgnDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}

package com.oracle.dgnmovil.util;

import android.content.ContentValues;
import android.content.Context;

import com.oracle.dgnmovil.app.data.DgnContract;

/**
 * Created by osvaldo on 10/19/14.
 */
public class DbUtil {
    private Context mContext;

    void DbUtil(Context context) {
        mContext = context;
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

}

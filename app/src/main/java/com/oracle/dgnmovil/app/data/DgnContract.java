package com.oracle.dgnmovil.app.data;

/**
 * Created by osvaldo on 10/15/14.
 */

import android.content.ContentUris;
import android.net.Uri;

/**
 * Defines table and column names for the dgn database.
 */
public class DgnContract {

    // Content authority to create the base of all URI's which apps will use to contract
    // the content provider
    public static final String CONTENT_AUTHORITY = "com.oracle.dgnmovil.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (append to base content URI for possible URI's)
    public static final String PATH_NORMA = "norma";
    public static final String PATH_PRODUCTO = "producto";
    public static final String PATH_RAE = "rae";
    public static final String PATH_ORGANISMO = "organismo";
    public static final String PATH_PROD_RAE = "prod_rae";

    /* Inner class that defines the table contents of the normas table */
    public static final class NormasEntry {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NORMA).build();

        public static final String CONTENT_TYPE =
                "vdn.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_NORMA;

        public static final String CONTENT_ITEM_TYPE =
                "vdn.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_NORMA;

        public static final String TABLE_NAME = "normas";

        public static final String _ID = "id";

        // Column with the foreign key into the productos table.
        public static final String COLUMN_PROD_KEY = "producto_id";
        // Column with the foreign key into the rae table.
        public static final String COLUMN_RAE_KEY = "rae_id";

        // Column with the foreign key into the organismos table.
        public static final String COLUMN_ORG_KEY = "organismo_id";

        // Column with the norma Clave
        public static final String COLUMN_CLAVE = "clave";

        // Column with the norma Title
        public static final String COLUMN_TITULO = "titulo";

        //Column with the norma Publicacion
        public static final String COLUMN_PUB = "publicacion";

        // Column with the norma Activa
        public static final String COLUMN_ACT = "activa";

        // Column with the norma Tipo
        public static final String COLUMN_TIPO = "tipo";

        // Column with the norma Internacional
        public static final String COLUMN_INTER = "norma_internacional";

        // Column with the norma Concordancia
        public static final String COLUMN_CONC = "concordancia";

        // Column with the norma Documento
        public static final String COLUMN_DOC = "documento";

        //
        public static final String COLUMN_FAV = "favorito";

        // Uri to build a norma based on the Id
        public static Uri buildNormaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        // Uri to build a norma based on an Element and Value
        public static Uri buildNormaByElement(String element, String value) {
            return CONTENT_URI.buildUpon().appendPath(element).appendPath(value).build();
        }

        // Returns the Element from Uri
        public static String getElementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        // Returns the Value form Uri
        public static String getValueFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }
    }

    /* Inner class that defines the table contents of the productos table */
    public static final class ProductosEntry {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTO).build();

        public static final String CONTENT_TYPE =
                "vdn.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTO;

        public static final String CONTENT_ITEM_TYPE =
                "vdn.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTO;

        public static final String TABLE_NAME = "productos";

        public static final String _ID = "id";

        // Column with the foreign key into the productos table.
        public static final String COLUMN_NOM = "nombre";

        // Uri to build a product based on the Id
        public static Uri buildProductoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        // Returns the Element from Uri
        public static String getElementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    /* Inner class that defines the table contents of the productos table */
    public static final class RaeEntry {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RAE).build();

        public static final String CONTENT_TYPE =
                "vdn.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_RAE;

        public static final String CONTENT_ITEM_TYPE =
                "vdn.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_RAE;

        public static final String TABLE_NAME = "rae";

        public static final String _ID = "id";

        // Column with the foreign key into the productos table.
        public static final String COLUMN_NOM = "nombre";

        public static final String COLUMN_VISITADO = "visitado";

        // Uri to build a rae based on the Id
        public static Uri buildRaeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the organismos table */
    public static final class OrganismosEntry {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ORGANISMO).build();

        public static final String CONTENT_TYPE =
                "vdn.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ORGANISMO;

        public static final String CONTENT_ITEM_TYPE =
                "vdn.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ORGANISMO;

        public static final String _ID = "id";

        public static final String TABLE_NAME = "organizmos";

        // Column with the foreign key into the productos table.
        public static final String COLUMN_NOM = "nombre";

        // Uri to build a organismo based on the Id
        public static Uri buildOrganismoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the organismos table */
    public static final class ProdRaeEntry {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROD_RAE).build();

        public static final String CONTENT_TYPE =
                "vdn.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_PROD_RAE;

        public static final String CONTENT_ITEM_TYPE =
                "vdn.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_PROD_RAE;

        public static final String _ID = "id";

        public static final String TABLE_NAME = "productos_rae";

        // Column with the foreign key into the productos table.
        public static final String COLUMN_PROD_KEY = "producto_id";

        // Column with the foreign key into the productos table.
        public static final String COLUMN_RAE_KEY = "rae_id";

        // Column with the foreign key into the productos table.
        public static final String COLUMN_IMG = "img";

        // Uri to build a organismo based on the Id
        public static Uri buildOrganismoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        // Returns the Element from Uri
        public static String getElementFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        // Uri to build a norma based on an Element and Value
        public static Uri buildProductoByRae(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }

}

package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.oracle.dgnmovil.app.data.FetchSearchTask;
import com.oracle.dgnmovil.app.model.Norma;
import com.oracle.dgnmovil.app.model.Producto;
import com.oracle.dgnmovil.app.model.Rae;

import java.util.List;
import java.util.Map;

public class SearchActivity extends ActionBarActivity {

    Typeface tf;

    LinearLayout root;
    ProgressBar progressBar;
    TextView productosHeader;
    TextView raesHeader;
    TextView normasHeader;
    LinearLayout productsRoot;
    LinearLayout raesRoot;
    LinearLayout normsRoot;
    ImageView empty;

    long millis;

    public static String NORMA_ATTRIBUTES = "norma_attributes";
    public static String NORMA_FAVORITE = "norma_favorite";
    public static String NORMA_ID = "norma_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView titleTextView = (TextView) findViewById(titleId);
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

        root = (LinearLayout) findViewById(R.id.search);

        productosHeader = (TextView) findViewById(R.id.header_products);
        raesHeader = (TextView) findViewById(R.id.header_raes);
        normasHeader = (TextView) findViewById(R.id.header_norms);

        productosHeader.setTypeface(tf);
        raesHeader.setTypeface(tf);
        normasHeader.setTypeface(tf);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        productsRoot = (LinearLayout) findViewById(R.id.linear_products);
        raesRoot = (LinearLayout) findViewById(R.id.linear_raes);
        normsRoot = (LinearLayout) findViewById(R.id.linear_norms);
        empty = (ImageView) findViewById(R.id.search_empty);

        if(savedInstanceState == null) {
            root.removeAllViews();
            root.addView(empty);
        }

        tf = Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf");
    }

    private void displayResults(Map<String, List<Object>> result) {
        root.removeAllViews();

        LayoutInflater li = getLayoutInflater();

        List<Object> productos = result.get(FetchSearchTask.PRODUCTO);

        if (productos.size() > 0) {
            root.addView(productosHeader);
            root.addView(productsRoot);
            for (Object obj : productos) {
                Producto producto = (Producto) obj;

                LinearLayout productoItem = (LinearLayout) li.inflate(R.layout.search_product, productsRoot, false);

                List<String> imgs = producto.getImg();

                LinearLayout productoImgs = (LinearLayout) productoItem.findViewById(R.id.search_producto_imgs);
                for(int i = 0; i < imgs.size() && i < 4; i++) {
                    ImageView productoImg = new ImageView(this);
                    String name = imgs.get(i).toLowerCase().replaceAll(" ", "_").replaceAll(",", "").replace("-", "");
                    int resID = getResources().getIdentifier(name, "drawable",  getPackageName());
                    if(resID == 0)
                        resID = R.drawable.ic_dgn_ico00;
                    productoImg.setImageResource(resID);

                    productoImg.setPadding(0, 0, 20, 0);
                    productoImg.setLayoutParams(new ActionBar.LayoutParams(70, 70));
                    productoImgs.addView(productoImg);
                }

                TextView productoName = (TextView) productoItem.findViewById(R.id.search_producto);
                productoName.setText(producto.getNombre());
                productoName.setTypeface(tf);

                TextView productoNorms = (TextView) productoItem.findViewById(R.id.search_producto_norms);
                productoNorms.setText(producto.getNumNormas() + " noms");
                productoNorms.setTypeface(tf);

                productsRoot.addView(productoItem);
            }
        }

        List<Object> raes = result.get(FetchSearchTask.RAE);

        if (raes.size() > 0) {
            root.addView(raesHeader);
            root.addView(raesRoot);
            for (Object obj : raes) {
                Rae rae = (Rae) obj;

                LinearLayout raeItem = (LinearLayout) li.inflate(R.layout.search_rae, raesRoot, false);

                ImageView raeImg = (ImageView) raeItem.findViewById(R.id.search_rae_img);
                String name = rae.getNombre().toLowerCase().replaceAll(" ", "_").replaceAll(",", "").replace("-", "");
                int resID = getResources().getIdentifier(name, "drawable",  getPackageName());
                if(resID == 0)
                    resID = R.drawable.ic_dgn_ico00;
                raeImg.setImageResource(resID);

                TextView raeName = (TextView) raeItem.findViewById(R.id.search_rae);
                raeName.setText(rae.getNombre());
                raeName.setTypeface(tf);

                TextView raeNorms = (TextView) raeItem.findViewById(R.id.search_rae_norms);
                raeNorms.setText(rae.getNumNormas() + " noms");
                raeNorms.setTypeface(tf);

                raesRoot.addView(raeItem);
            }
        }

        List<Object> normas = result.get(FetchSearchTask.NORMA);

        if (normas.size() > 0) {
            root.addView(normasHeader);
            root.addView(normsRoot);
            for (Object obj : normas) {
                final Norma norma = (Norma) obj;

                LinearLayout normaItem = (LinearLayout) li.inflate(R.layout.search_norm, normsRoot, false);

                ImageView normaImg = (ImageView) normaItem.findViewById(R.id.reporte_producto_img);
                String name = norma.getImg().toLowerCase().replaceAll(" ", "_").replaceAll(",", "").replace("-", "");
                int resID = getResources().getIdentifier(name, "drawable",  getPackageName());
                if(resID == 0)
                    resID = R.drawable.ic_dgn_ico00;
                normaImg.setImageResource(resID);

                TextView normaName = (TextView) normaItem.findViewById(R.id.reporte_producto);
                normaName.setText(norma.getClave());
                normaName.setTypeface(tf);

                TextView normaTitle = (TextView) normaItem.findViewById(R.id.reporte_comentario);
                normaTitle.setText(norma.getTitulo());
                normaTitle.setTypeface(tf);

                normaItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SearchActivity.this, NormActivity.class);
                        String[] attributes = norma.getStringAttributes();
                        int favorito = norma.getFavorito();
                        long id = norma.getId();
                        intent.putExtra(NORMA_ATTRIBUTES, attributes);
                        intent.putExtra(NORMA_FAVORITE, favorito);
                        intent.putExtra(NORMA_ID, id);
                        startActivity(intent);
                    }
                });

                normsRoot.addView(normaItem);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        int frameId = searchView.getContext().getResources().getIdentifier("android:id/search_edit_frame", null, null);
        LinearLayout frameView = (LinearLayout) searchView.findViewById(frameId);
        frameView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        int hintId = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView hintView = (ImageView) searchView.findViewById(hintId);
        hintView.setImageResource(R.drawable.green_dot);

        int plateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View textView = searchView.findViewById(plateId);
        textView.setBackgroundResource(R.drawable.dgn_edit_text_holo_light);

        int textId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchPlate = (EditText) searchView.findViewById(textId);
        searchPlate.setTextColor(Color.WHITE);

        int closeId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) searchView.findViewById(closeId);
        closeButton.setImageResource(R.drawable.ic_dgn_cancel);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                final long currMillis = System.currentTimeMillis();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                if (currMillis >= millis) {
                                    root.removeAllViews();
                                    productsRoot.removeAllViews();
                                    raesRoot.removeAllViews();
                                    normsRoot.removeAllViews();
                                    if (!query.isEmpty()) {
                                        root.addView(progressBar);
                                        new SearchTask().execute(query);
                                    } else
                                        root.addView(empty);
                                    }
                                }
                        },
                        400);

                millis = currMillis;


                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class SearchTask extends FetchSearchTask {
        public SearchTask() {
            super(SearchActivity.this);
        }

        @Override
        protected void onPostExecute(Map<String, List<Object>> result) {
            displayResults(result);
        }
    }
}

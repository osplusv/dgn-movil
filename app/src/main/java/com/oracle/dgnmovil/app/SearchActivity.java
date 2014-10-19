package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.oracle.dgnmovil.app.data.FetchSearchTask;
import com.oracle.dgnmovil.app.model.Norma;
import com.oracle.dgnmovil.app.model.Producto;
import com.oracle.dgnmovil.app.model.Rae;

import java.util.List;
import java.util.Map;

public class SearchActivity extends ActionBarActivity {

    LinearLayout root;
    ProgressBar progressBar;
    Typeface tf;

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

        root = (LinearLayout) findViewById(R.id.searchLinear);

        progressBar = new ProgressBar(this);
        LinearLayout.LayoutParams pblp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        pblp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(pblp);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.INVISIBLE);
        root.addView(progressBar);

        tf = Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf");
    }

    private void displayResults(Map<String, List<Object>> result) {
        LayoutInflater li = getLayoutInflater();

        if(result.containsKey(FetchSearchTask.PRODUCTO)) {
            List<Object> productos = result.get(FetchSearchTask.PRODUCTO);

            if(productos.size() > 0) {
                TextView header = new TextView(this);
                header.setText("Productos");
                root.addView(header);

                for (Object obj : productos) {
                    Producto producto = (Producto) obj;

                    RelativeLayout productoItem = (RelativeLayout) li.inflate(R.layout.search_product, null);

                    TextView productoName = (TextView) productoItem.findViewById(R.id.search_product);
                    productoName.setText(producto.getNombre());
                    productoName.setTypeface(tf);

                    TextView productoNorms = (TextView) productoItem.findViewById(R.id.search_product_norms);
                    productoNorms.setText("" + producto.getNumNormas());
                    productoNorms.setTypeface(tf);

                    root.addView(productoItem);
                }
            }
        }

        if(result.containsKey(FetchSearchTask.PRODUCTO)) {
            List<Object> raes = result.get(FetchSearchTask.RAE);

            progressBar.setVisibility(View.INVISIBLE);

            if(raes.size() > 0) {
                TextView header = new TextView(this);
                header.setText("Actividades Económicas");
                root.addView(header);

                for (Object obj : raes) {
                    Rae rae = (Rae) obj;

                    RelativeLayout raeItem = (RelativeLayout) li.inflate(R.layout.search_rae, null);

                    TextView raeName = (TextView) raeItem.findViewById(R.id.search_rae);
                    raeName.setText(rae.getNombre());
                    raeName.setTypeface(tf);

                    TextView raeNorms = (TextView) raeItem.findViewById(R.id.search_rae_norms);
                    raeNorms.setText("" + rae.getNumNormas());
                    raeNorms.setTypeface(tf);

                    root.addView(raeItem);
                }
            }
        }

        if(result.containsKey(FetchSearchTask.NORMA)) {
            List<Object> normas = result.get(FetchSearchTask.NORMA);

            if(normas.size() > 0) {
                TextView header = new TextView(this);
                header.setText("Normas");
                root.addView(header);

                for (Object obj : normas) {
                    Norma norma = (Norma) obj;

                    RelativeLayout normaItem = (RelativeLayout) li.inflate(R.layout.search_norm, null);

                    TextView normaName = (TextView) normaItem.findViewById(R.id.search_norma);
                    normaName.setText(norma.getClave());
                    normaName.setTypeface(tf);

                    TextView normaDate = (TextView) normaItem.findViewById(R.id.search_norma_date);
                    normaDate.setText(norma.getFecha());
                    normaDate.setTypeface(tf);

                    TextView normaTitle = (TextView) normaItem.findViewById(R.id.search_norma_title);
                    normaTitle.setText(norma.getTitulo());
                    normaTitle.setTypeface(tf);

                    root.addView(normaItem);
                }
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

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String query = "" + searchView.getQuery();
                if (!b && !query.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    new SearchTask().execute(query);
                }
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

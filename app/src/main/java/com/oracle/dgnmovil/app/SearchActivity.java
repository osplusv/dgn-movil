package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchActivity extends ActionBarActivity {

    public static String[] names = {"Agua", "Aguacate", "Aguja", "Abejas", "Abellanas"};
    public static String[] names2 = {"Agricultura", "Agraria", "Amar", "Amono"};

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

        /*final EditText search = (EditText) findViewById(R.id.search);

        /*search.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(search, 0);
            }
        },200);

        final ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.INVISIBLE);

        final LinearLayout root = (LinearLayout) findViewById(R.id.searchLinear);
        root.addView(progressBar);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                root.removeViews(0, root.getChildCount());
                String text = editable.toString();

                if(!text.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);

                    ArrayList<String> results1 = new ArrayList<String>();
                    for(int i = 0; i < names.length; i++) {
                        if(names[i].toLowerCase().contains(text)) {
                            results1.add(names[i]);
                        }
                    }

                    ArrayList<String> results2 = new ArrayList<String>();
                    for(int i = 0; i < names2.length; i++) {
                        if(names2[i].toLowerCase().contains(text)) {
                            results2.add(names2[i]);
                        }
                    }

                    progressBar.setVisibility(View.INVISIBLE);

                    for(int j = 0; j < 10; j++) {
                        if(results1.size() > 0) {
                            TextView header1 = new TextView(SearchActivity.this);
                            header1.setText("Productos");
                            header1.setTypeface(null, Typeface.BOLD);

                            root.addView(header1);

                            for(String element: results1) {
                                TextView elementView = new TextView(SearchActivity.this);
                                elementView.setText(element);
                                root.addView(elementView);

                                elementView.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        System.out.println("click");
                                    }
                                });
                            }
                        }

                        if(results2.size() > 0) {
                            TextView header2 = new TextView(SearchActivity.this);
                            header2.setText("Actividades");
                            header2.setTypeface(null, Typeface.BOLD);

                            root.addView(header2);

                            for(String element: results2) {
                                TextView elementView = new TextView(SearchActivity.this);
                                elementView.setText(element);
                                root.addView(elementView);

                                elementView.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        System.out.println("click");
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

        });*/
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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            final SearchView searchView = (SearchView) item.getActionView();
            searchView.setIconifiedByDefault(false);
            searchView.requestFocus();

            int hintId = searchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
            final ImageView hintView = (ImageView) searchView.findViewById(hintId);
            hintView.setVisibility(View.INVISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

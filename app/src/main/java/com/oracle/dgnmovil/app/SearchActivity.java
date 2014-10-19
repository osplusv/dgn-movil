package com.oracle.dgnmovil.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    public static String[] names = {"Agua", "Aguacate", "Aguja", "Abejas", "Abellanas"};
    public static String[] names2 = {"Agricultura", "Agraria", "Amar", "Amono"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText search = (EditText) findViewById(R.id.search);

        search.postDelayed(new Runnable() {

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

        });
    }
}

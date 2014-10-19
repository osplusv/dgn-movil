package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class NormActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norm);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView titleTextView = (TextView) findViewById(titleId);
        titleTextView.setTextColor(Color.WHITE);
        titleTextView.setTypeface(Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf"));

        Button report = (Button) findViewById(R.id.report_btn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.norm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search_main) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

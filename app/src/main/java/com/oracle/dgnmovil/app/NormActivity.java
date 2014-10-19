package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

        Typeface tf = Typeface.createFromAsset(getAssets(), "font/MavenPro-Bold.ttf");

        TextView code = (TextView) findViewById(R.id.norm_code);
        TextView organism = (TextView) findViewById(R.id.norm_organism);
        TextView title = (TextView) findViewById(R.id.norm_title);
        TextView publicationBanner = (TextView) findViewById(R.id.norm_publication_banner);
        TextView publication = (TextView) findViewById(R.id.norm_publication);
        TextView releaseBanner = (TextView) findViewById(R.id.norm_relase_banner);
        TextView release = (TextView) findViewById(R.id.norm_release);
        TextView internationalBanner = (TextView) findViewById(R.id.norm_international_banner);
        TextView international = (TextView) findViewById(R.id.norm_international);
        TextView concordanceBanner = (TextView) findViewById(R.id.norm_concordance_banner);
        TextView concordance = (TextView) findViewById(R.id.norm_concordance);
        TextView productBanner = (TextView) findViewById(R.id.norm_product_banner);
        TextView product = (TextView) findViewById(R.id.norm_product);
        TextView raeBanner = (TextView) findViewById(R.id.norm_rae_banner);
        TextView rae = (TextView) findViewById(R.id.norm_rae);

        code.setTypeface(tf);
        organism.setTypeface(tf);
        title.setTypeface(tf);
        publicationBanner.setTypeface(tf);
        publication.setTypeface(tf);
        releaseBanner.setTypeface(tf);
        release.setTypeface(tf);
        internationalBanner.setTypeface(tf);
        international.setTypeface(tf);
        concordanceBanner.setTypeface(tf);
        concordance.setTypeface(tf);
        productBanner.setTypeface(tf);
        product.setTypeface(tf);
        raeBanner.setTypeface(tf);
        rae.setTypeface(tf);

        Button favoriteButton = (Button) findViewById(R.id.norm_favorite);

        Button fileButton = (Button) findViewById(R.id.norm_file);
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.economia-noms.gob.mx/normas/noms/2010/001conagua2012.pdf"));
                startActivity(browserIntent);
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

package com.oracle.dgnmovil.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.oracle.dgnmovil.tabs.FavoritesTabFragment;
import com.oracle.dgnmovil.tabs.ReportTabFragment;

public class MainActivity extends ActionBarActivity {

    final String[] TABS = { "Relevantes", "Reportados" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ApplicateFragmentAdapter(getSupportFragmentManager()));

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        actionBar.addTab(actionBar.newTab()
                .setText("Favoritos")
                .setTabListener(tabListener));

        actionBar.addTab(actionBar.newTab()
                .setText("Reportados")
                .setTabListener(tabListener));

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        });

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

        Button contact = (Button) findViewById(R.id.contact_btn);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "36336892"));
                startActivity(callIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

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

    private class ApplicateFragmentAdapter extends FragmentPagerAdapter {
        public ApplicateFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return new FavoritesTabFragment();
                }

                case 1: {
                    return new ReportTabFragment();
                }
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }

        @Override
        public int getCount() {
            return TABS.length;
        }
    }
}

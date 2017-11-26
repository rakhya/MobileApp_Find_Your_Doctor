package rakhya.gsu.edu.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.net.Uri;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String user_name, user_sex_age, user_sex, user_lat, user_lon, user_age;
    SessionManager name_sex_age_session;
    OptionsMenu options;
    //LocationSessionManager location_session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // these session managers have users input data!
        name_sex_age_session = new SessionManager(getApplicationContext());
        //location_session = new LocationSessionManager(getApplicationContext());

        // check whether user has logged in or filled details
        name_sex_age_session.checkLogin();  // if he hasnt, user will be automatically redirected to login activity
        //location_session.checkLogin();

        // get user data from SessionManager : data : name and sex_age
        HashMap<String, String> user = name_sex_age_session.getUserDetails();
        user_name = user.get(SessionManager.KEY_NAME);
        user_sex = user.get(SessionManager.KEY_SEX);
        user_age = user.get(SessionManager.KEY_AGE);

        options = new OptionsMenu(this);

        // get user data from LocationSessionManager : data : lat and lon
        /*HashMap<String, String> user_2 = location_session.getUserDetails();
        user_lat = user_2.get(LocationSessionManager.KEY_NAME);
        user_lon = user_2.get(LocationSessionManager.KEY_EMAIL);
*/        //Toast.makeText(MainActivity.this, "Welcome " + user_name, Toast.LENGTH_SHORT).show();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TwoFragment(), "Nearby Hospitals");
        adapter.addFragment(new OneFragment(), "Symptoms & Diseases");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // In case you have an item
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       options.onOptionsItemSelected(item);
       return super.onOptionsItemSelected(item);
    }


}

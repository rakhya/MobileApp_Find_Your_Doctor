package rakhya.gsu.edu.project;

/**
 * Created by prava on 11/24/2017.
 */

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoctorDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private Toolbar toolbar;
    private DoctorPagerAdapter adapterViewPager;
    ArrayList<Doctor> mDoctors = new ArrayList<>();

    private OptionsMenu options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        options = new OptionsMenu(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //super.onCreateDrawer();
        ButterKnife.bind(this);

        mDoctors = Parcels.unwrap(getIntent().getParcelableExtra("doctors"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

        adapterViewPager = new DoctorPagerAdapter(getSupportFragmentManager(), mDoctors);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
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

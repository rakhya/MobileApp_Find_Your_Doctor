package rakhya.gsu.edu.project;

/**
 * Created by prava on 11/23/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorListActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.noResultsImageView) ImageView mNoResultsImageView;
    @Bind(R.id.noResultsTextView) TextView mNoResultsTextView;

    private OptionsMenu options;
    private Toolbar toolbar;
    public ArrayList<Doctor> mDoctors = new ArrayList<>();
    private DoctorListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        options = new OptionsMenu(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //super.onCreateDrawer();
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String speciality = intent.getStringExtra("speciality");
        String lat_lng_rad = intent.getStringExtra("lat_lng_rad");
        String ins_uid = intent.getStringExtra("insurance_uid");
        /*String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");*/
        getDoctors(speciality, lat_lng_rad, ins_uid);
    }

    private void getDoctors(String speciality, String lat_lng_rad, String ins_uid) {
        final BetterDoctorService betterDoctorService = new BetterDoctorService();

        betterDoctorService.findDoctorsByLocationAndSpecialty(speciality, lat_lng_rad, ins_uid, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mDoctors = betterDoctorService.processResults(response);

                DoctorListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter = new DoctorListAdapter(getApplicationContext(), mDoctors);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DoctorListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                        if (mDoctors.isEmpty()) {
                            mNoResultsImageView.setVisibility(View.VISIBLE);
                            mNoResultsTextView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
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

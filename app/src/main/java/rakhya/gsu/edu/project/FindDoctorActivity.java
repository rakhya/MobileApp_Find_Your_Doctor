package rakhya.gsu.edu.project;

/**
 * Created by prava on 11/23/2017.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindDoctorActivity extends AppCompatActivity implements View.OnClickListener {

    private OptionsMenu options;
    SessionManager address_session;
    private Toolbar toolbar;
    private int mStateSelectedPosition;
    private int mSpecialistSelectedPosition;
    private int mRadSelectedPosition;
    private String mBetterDoctorURL = "http://www.betterdoctor.com";

    @Bind(R.id.specialtySearchButton) Button mSpecialtySearchButton;
    @Bind(R.id.streetAddressEdit) EditText mStreetAddressEditText;
    @Bind(R.id.zipEdit) EditText mZipCodeEditText;
    @Bind(R.id.insuranceEdit) EditText mInsuranceEditText;
    @Bind(R.id.cityEditText) EditText mCityEditText;
    @Bind(R.id.stateSpinner) Spinner mStateSpinner;
    @Bind(R.id.radiusSpinner) Spinner mRadiusSpinner;
    @Bind(R.id.specialistSpinner) Spinner mSpecialistSpinner;
    @Bind(R.id.betterDoctorCreditTextView) TextView mBetterDoctorCreditTextView;
    @Bind(R.id.findDoctorTitle) TextView mFindDoctorTitle;
    @Bind(R.id.chooseSpecialistTextView) TextView mChooseSpecialistTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        options = new OptionsMenu(this);
        //super.onCreateDrawer();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        address_session = new SessionManager(getApplicationContext());

        ButterKnife.bind(this);
        mSpecialtySearchButton.setOnClickListener(this);
        mBetterDoctorCreditTextView.setOnClickListener(this);

        //Typeface questrialFont = Typeface.createFromAsset(getAssets(), "fonts/Questrial-Regular.otf");
        //mFindDoctorTitle.setTypeface(questrialFont);
        //mChooseSpecialistTextView.setTypeface(questrialFont);

        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       // mEditor = mSharedPreferences.edit();

        //mCityPreference = mSharedPreferences.getString(Constants.PREFERENCES_CITY_KEY, null);
        HashMap<String, String> user = address_session.getUserDetails();

        mStreetAddressEditText.setText(user.get(SessionManager.STREET_ADDRESS_KEY));
        mCityEditText.setText(user.get(SessionManager.CITY_KEY));
        mStateSpinner.setSelection(Integer.parseInt(user.get(SessionManager.STATE_KEY)));
        mZipCodeEditText.setText(user.get(SessionManager.ZIP_CODE_KEY));
        mSpecialistSpinner.setSelection(Integer.parseInt(user.get(SessionManager.SPECIALTY_KEY)));
        mRadiusSpinner.setSelection(Integer.parseInt(user.get(SessionManager.RADIUS_KEY)));
        mInsuranceEditText.setText(user.get(SessionManager.INSURANCE_KEY));
    }

    @Override
    public void onClick(View v) {
        if (v == mSpecialtySearchButton) {
            String speciality = mSpecialistSpinner.getSelectedItem().toString().toLowerCase().replace(" ", "-").trim();
            String stAdd = mStreetAddressEditText.getText().toString().toLowerCase().trim();
            String radius = mRadiusSpinner.getSelectedItem().toString().toLowerCase();
            String zipCode = mZipCodeEditText.getText().toString().toLowerCase().trim();
            String city = mCityEditText.getText().toString().toLowerCase().trim();
            String state = mStateSpinner.getSelectedItem().toString().toLowerCase();
            String ins_uid = mInsuranceEditText.getText().toString().toLowerCase().trim();

            mStateSelectedPosition = mStateSpinner.getSelectedItemPosition();
            mSpecialistSelectedPosition = mSpecialistSpinner.getSelectedItemPosition();
            mRadSelectedPosition = mRadiusSpinner.getSelectedItemPosition();
            address_session.findDoctorSession(mSpecialistSelectedPosition,city,mStateSelectedPosition,mRadSelectedPosition,stAdd,ins_uid,zipCode);

            if(radius.equals("")){
                radius = "100";
            }
            String fullAddr = stAdd + " " + city + " " + state + " " + zipCode;
            fullAddr = fullAddr.trim();
            String lat_lng = geoLocate(fullAddr);
            if (!(lat_lng.equals(""))) {
                String lat_lng_rad = lat_lng + "," + radius;


                Intent intent = new Intent(FindDoctorActivity.this, DoctorListActivity.class);
                intent.putExtra("speciality", speciality);
            /*intent.putExtra("city", city);
            intent.putExtra("state", state);*/

                intent.putExtra("lat_lng_rad", lat_lng_rad);
                intent.putExtra("insurance_uid", ins_uid);

                startActivity(intent);
            } else {
                Log.e("Lat_lng_tag", "Exact location not retrieved with user entered address");
            }

            if (v == mBetterDoctorCreditTextView) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mBetterDoctorURL));
                startActivity(webIntent);
            }
        }
    }

    public String geoLocate(String Address){
        String lat_lng = "";
        Geocoder gc = new Geocoder(this);
        try {
            List<Address> list = gc.getFromLocationName(Address,10);
            if(list.size()>1){
                Toast.makeText(this, "Multiple locations associated with entered details.Please enter exact address", Toast.LENGTH_SHORT).show();
            }
            else if(list.size()==0){
                Toast.makeText(this, "No locations associated with entered details.Please enter exact address", Toast.LENGTH_LONG).show();
            }
            else{
                Address add = list.get(0);
                String locality = add.getLocality();
                Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
                double lat = add.getLatitude();
                double lng = add.getLongitude();
                lat_lng = lat+","+lng;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return lat_lng;
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

package rakhya.gsu.edu.project;

/**
 * Created by prava on 11/19/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MyPrefs";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_SEX = "sex";

    public static final String KEY_AGE = "age";

    public static final String STATE_KEY = "stateSpinnerSelection";

    public static final String CITY_KEY = "city";

    public static final String SPECIALTY_KEY = "specialtySpinnerSelection";

    public static final String STREET_ADDRESS_KEY = "streetAddress";

    public static final String ZIP_CODE_KEY = "zipCode";

    public static final String INSURANCE_KEY = "insurance";

    public static final String RADIUS_KEY = "radius";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String sex, String age){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_SEX, sex);

        // Storing email in pref
        editor.putString(KEY_AGE, age);

        // commit changes
        editor.commit();
    }

    public void findDoctorSession(int specialty, String city, int state, int radius, String stAddr, String insUid, String zip) {
        editor.putInt(SPECIALTY_KEY, specialty);
        editor.putString(CITY_KEY, city);
        editor.putInt(STATE_KEY, state);
        editor.putInt(RADIUS_KEY, radius);
        editor.putString(STREET_ADDRESS_KEY, stAddr);
        editor.putString(INSURANCE_KEY, insUid);
        editor.putString(ZIP_CODE_KEY, zip);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_SEX, pref.getString(KEY_SEX, null));

        // user email id
        user.put(KEY_AGE, pref.getString(KEY_AGE, null));

        user.put(STREET_ADDRESS_KEY, pref.getString(STREET_ADDRESS_KEY, null));

        user.put(CITY_KEY, pref.getString(CITY_KEY, null));

        user.put(STATE_KEY, Integer.toString(pref.getInt(STATE_KEY, 0)));

        user.put(ZIP_CODE_KEY, pref.getString(ZIP_CODE_KEY, null));

        user.put(SPECIALTY_KEY, Integer.toString(pref.getInt(SPECIALTY_KEY, 0)));

        user.put(RADIUS_KEY, Integer.toString(pref.getInt(RADIUS_KEY, 0)));

        user.put(INSURANCE_KEY, pref.getString(INSURANCE_KEY, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}



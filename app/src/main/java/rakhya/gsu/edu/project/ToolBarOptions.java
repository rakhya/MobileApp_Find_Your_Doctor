package rakhya.gsu.edu.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by prava on 11/24/2017.
 */

public class ToolBarOptions extends AppCompatActivity {
    SessionManager name_sex_age_session = new SessionManager(getApplicationContext());

    public boolean menuOptions(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // In case you have an item
        return true;
    }



    public boolean itemOptionsClick(MenuItem item, Context context)
    {
        int id = item.getItemId();


        if(id == R.id.action_logout)  //Logout
        {
            name_sex_age_session.logoutUser();
            //location_session.logoutUser();
        }

        if(id == R.id.action_feedback)  //Feedback
        {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

        if(id == R.id.action_share)  //Share
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        if(id == R.id.action_about)  //About
        {



            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

            // Setting Dialog Title
            alertDialog.setTitle("About");

            // Setting Dialog Message
            alertDialog.setMessage("Find Your Doctor Health App is your personal healthcare assistant, that brings you the location of nearby hospitals along with their details. It also helps you to track or map a disease based on the various symptoms." +
                    "\n\nDevelopers:\n\nPravarakhya\npravarakhya.chilukuri@gmail.com\n\nNanda Kishore\nnandachavakula@gmail.com\n\nAniket Joshi\naniket_joshi3000@yahoo.co.in\n\nIcons for this app are designed with flaticon.com");


            // if User clicks Accept
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alertDialog.show();



        }

        if (id == R.id.action_disclaimer)  //Disclaimer
        {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

            // Setting Dialog Title
            alertDialog.setTitle("Disclaimer. Legal Notice.");

            // Setting Dialog Message
            alertDialog.setMessage("\n" +
                    "Users kindly understand that this app is meant for educational purpose only. This application is NOT meant for SELF DIAGNOSIS. The results provided by this app are not a legal advice. This app maps various symptoms to their PROBABLE diseases or cause. Actual disease may vary depending on various conditions. This method is not the best means of diagnosis. Please visit a registered doctor in case you require a medical assistance or help.\n\nThe result generated based on the information given by the user is only for informative and indicative purposes. Therefore, the developers make no warranties whatsoever nor is responsible for damages of any kind regarding the use of this app or any decision made by the user.\n");



            // if User clicks Accept
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alertDialog.show();
        }


        return super.onOptionsItemSelected(item);
    }



}



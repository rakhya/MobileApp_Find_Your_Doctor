package rakhya.gsu.edu.project;

/**
 * Created by prava on 11/24/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

public class DoctorPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Doctor> mDoctors;

    public DoctorPagerAdapter(FragmentManager fm, ArrayList<Doctor> doctors) {
        super(fm);
        mDoctors = doctors;
    }

    @Override
    public Fragment getItem(int position) {
        return DoctorDetailFragment.newInstance(mDoctors.get(position));
    }

    @Override
    public int getCount() {
        return mDoctors.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDoctors.get(position).getName();
    }
}

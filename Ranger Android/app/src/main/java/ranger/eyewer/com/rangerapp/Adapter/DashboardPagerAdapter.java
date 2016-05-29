package ranger.eyewer.com.rangerapp.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ranger.eyewer.com.rangerapp.Main.Fragments.Fragment_InfoView;
import ranger.eyewer.com.rangerapp.Main.Fragments.Fragment_LiveMap;
import ranger.eyewer.com.rangerapp.Main.Fragments.Fragment_PanicView;
import ranger.eyewer.com.rangerapp.Main.Fragments.Fragment_ProfileView;

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    public DashboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new Fragment_LiveMap();
            case 1:
                return new Fragment_PanicView();
            case 2:
                return new Fragment_InfoView();
            case 3:
                return new Fragment_ProfileView();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
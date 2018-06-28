package emcorp.studio.mutamtour.Adapter;

/**
 * Created by ASUS on 21/09/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import emcorp.studio.mutamtour.Fragment.BeritaFragment;
import emcorp.studio.mutamtour.Fragment.MoreFragment;
import emcorp.studio.mutamtour.Fragment.TentangKamiFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TentangKamiFragment tab1 = new TentangKamiFragment();
                return tab1;
            case 1:
                TentangKamiFragment tab2 = new TentangKamiFragment();
                return tab2;
            case 2:
                BeritaFragment tab3 = new BeritaFragment();
                return tab3;
            case 3:
                MoreFragment tab4 = new MoreFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

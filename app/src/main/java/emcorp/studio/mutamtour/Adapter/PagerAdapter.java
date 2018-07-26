package emcorp.studio.mutamtour.Adapter;

/**
 * Created by ASUS on 21/09/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import emcorp.studio.mutamtour.Fragment.BeritaFragment;
import emcorp.studio.mutamtour.Fragment.DoaFragment;
import emcorp.studio.mutamtour.Fragment.HomeFragment;
import emcorp.studio.mutamtour.Fragment.LegalitasFragment;
import emcorp.studio.mutamtour.Fragment.MoreFragment;
import emcorp.studio.mutamtour.Fragment.TokohFragment;
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
                HomeFragment tab1 = new HomeFragment();
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
            case 4:
                LegalitasFragment tab5 = new LegalitasFragment();
                return tab5;
            case 5:
                TokohFragment tab6 = new TokohFragment();
                return tab6;
            case 6:
                DoaFragment tab7 = new DoaFragment();
                return tab7;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
//        return mNumOfTabs;
        return 7;
    }
}

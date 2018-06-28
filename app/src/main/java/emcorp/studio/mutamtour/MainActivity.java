package emcorp.studio.mutamtour;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import emcorp.studio.mutamtour.Adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Tentang"));
        tabLayout.addTab(tabLayout.newTab().setText("Berita"));
        tabLayout.addTab(tabLayout.newTab().setText("More"));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_bantuan);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_berita);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_more);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setTitle("HOME");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            switch (extras.getString("MENU")){
                case "BERITA" :
                    viewPager.setCurrentItem(2);
                    break;
                case "MORE" :
                    viewPager.setCurrentItem(3);
                    break;
                default:
                    viewPager.setCurrentItem(0);
                    break;
            }
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        setTitle("HOME");
                        break;
                    case 1:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        setTitle("TENTANG KAMI");
                        break;
                    case 2:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        setTitle("BERITA");
                        break;
                    case 3:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        setTitle("MORE");
                        break;
                    default:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

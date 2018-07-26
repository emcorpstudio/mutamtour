package emcorp.studio.mutamtour;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import emcorp.studio.mutamtour.Adapter.PagerAdapter;
import emcorp.studio.mutamtour.Library.CustomTypefaceSpan;
import emcorp.studio.mutamtour.Library.CustomViewPager;
import emcorp.studio.mutamtour.Library.TypefaceUtil;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ActionBar actionBar;
    Typeface type;
    SpannableStringBuilder SS;
    ImageButton btn1, btn2, btn3, btn4;
    TextView tv1, tv2, tv3, tv4;
    LinearLayout loTop;
    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/barclays.ttf");

        loTop = (LinearLayout) findViewById(R.id.loTop);
        btn1 = (ImageButton) findViewById(R.id.btn1);
        btn2 = (ImageButton) findViewById(R.id.btn2);
        btn3 = (ImageButton) findViewById(R.id.btn3);
        btn4 = (ImageButton) findViewById(R.id.btn4);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

//        btn1.setOnTouchListener(new ButtonClick());
//        btn2.setOnTouchListener(new ButtonClick());
//        btn3.setOnTouchListener(new ButtonClick());
//        btn4.setOnTouchListener(new ButtonClick());

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Beranda"));
        tabLayout.addTab(tabLayout.newTab().setText("Tentang"));
        tabLayout.addTab(tabLayout.newTab().setText("Berita"));
        tabLayout.addTab(tabLayout.newTab().setText("More"));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_beranda);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_bantuan);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_berita);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_more);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        setTitle("HOME");
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, R.color.background_toolbar)))));
        actionBar.setTitle(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(this, R.color.text_toolbar) & 0xffffff)+"'>HOME</font>"));
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.icon_toolbar), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        actionBar.hide();
        setCustomFont();
        type = Typeface.createFromAsset(getAssets(),"fonts/barclays.ttf");
        btn1.setColorFilter(Color.argb(255, 66, 66, 66));
        btn2.setColorFilter(Color.argb(255, 255, 255, 255));
        btn3.setColorFilter(Color.argb(255, 255, 255, 255));
        btn4.setColorFilter(Color.argb(255, 255, 255, 255));
        tv1.setTextColor(Color.argb(255, 66, 66, 66));
        tv2.setTextColor(Color.argb(255, 255, 255, 255));
        tv3.setTextColor(Color.argb(255, 255, 255, 255));
        tv4.setTextColor(Color.argb(255, 255, 255, 255));

        final CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.disableScroll(true);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            switch (extras.getString("MENU")){
                case "BERITA" :
                    viewPager.setCurrentItem(2);
                    actionBar.show();
                    loTop.setVisibility(View.GONE);
                    break;
                case "MORE" :
                    viewPager.setCurrentItem(3);
                    actionBar.show();
                    loTop.setVisibility(View.GONE);
                    break;
                default:
                    actionBar.hide();
                    viewPager.setCurrentItem(0);
                    loTop.setVisibility(View.VISIBLE);
                    btn1.setColorFilter(Color.argb(255, 66, 66, 66));
                    btn2.setColorFilter(Color.argb(255, 255, 255, 255));
                    btn3.setColorFilter(Color.argb(255, 255, 255, 255));
                    btn4.setColorFilter(Color.argb(255, 255, 255, 255));
                    tv1.setTextColor(Color.argb(255, 66, 66, 66));
                    tv2.setTextColor(Color.argb(255, 255, 255, 255));
                    tv3.setTextColor(Color.argb(255, 255, 255, 255));
                    tv4.setTextColor(Color.argb(255, 255, 255, 255));
                    break;
            }
        }



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                actionBar.hide();
                btn1.setColorFilter(Color.argb(255, 66, 66, 66));
                btn2.setColorFilter(Color.argb(255, 255, 255, 255));
                btn3.setColorFilter(Color.argb(255, 255, 255, 255));
                btn4.setColorFilter(Color.argb(255, 255, 255, 255));
                tv1.setTextColor(Color.argb(255, 66, 66, 66));
                tv2.setTextColor(Color.argb(255, 255, 255, 255));
                tv3.setTextColor(Color.argb(255, 255, 255, 255));
                tv4.setTextColor(Color.argb(255, 255, 255, 255));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(5);
                actionBar.hide();
                btn1.setColorFilter(Color.argb(255, 255, 255, 255));
                btn2.setColorFilter(Color.argb(255, 66, 66, 66));
                btn3.setColorFilter(Color.argb(255, 255, 255, 255));
                btn4.setColorFilter(Color.argb(255, 255, 255, 255));
                tv1.setTextColor(Color.argb(255, 255, 255, 255));
                tv2.setTextColor(Color.argb(255, 66, 66, 66));
                tv3.setTextColor(Color.argb(255, 255, 255, 255));
                tv4.setTextColor(Color.argb(255, 255, 255, 255));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(4);
                SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(MainActivity.this, R.color.text_toolbar) & 0xffffff)+"'>LEGALITAS</font>"));
                SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                actionBar.setTitle(SS);
                actionBar.show();
                loTop.setVisibility(View.GONE);
                btn1.setColorFilter(Color.argb(255, 255, 255, 255));
                btn2.setColorFilter(Color.argb(255, 255, 255, 255));
                btn3.setColorFilter(Color.argb(255, 66, 66, 66));
                btn4.setColorFilter(Color.argb(255, 255, 255, 255));
                tv1.setTextColor(Color.argb(255, 255, 255, 255));
                tv2.setTextColor(Color.argb(255, 255, 255, 255));
                tv3.setTextColor(Color.argb(255, 66, 66, 66));
                tv4.setTextColor(Color.argb(255, 255, 255, 255));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(6);
                SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(MainActivity.this, R.color.text_toolbar) & 0xffffff)+"'>DOA</font>"));
                SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                actionBar.setTitle(SS);
                actionBar.show();
                loTop.setVisibility(View.GONE);
                btn1.setColorFilter(Color.argb(255, 255, 255, 255));
                btn2.setColorFilter(Color.argb(255, 255, 255, 255));
                btn3.setColorFilter(Color.argb(255, 255, 255, 255));
                btn4.setColorFilter(Color.argb(255, 66, 66, 66));
                tv1.setTextColor(Color.argb(255, 255, 255, 255));
                tv2.setTextColor(Color.argb(255, 255, 255, 255));
                tv3.setTextColor(Color.argb(255, 255, 255, 255));
                tv4.setTextColor(Color.argb(255, 66, 66, 66));
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        actionBar.setTitle(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(MainActivity.this, R.color.text_toolbar) & 0xffffff)+"'>HOME</font>"));
                        actionBar.hide();
                        loTop.setVisibility(View.VISIBLE);
                        btn1.setColorFilter(Color.argb(255, 66, 66, 66));
                        btn2.setColorFilter(Color.argb(255, 255, 255, 255));
                        btn3.setColorFilter(Color.argb(255, 255, 255, 255));
                        btn4.setColorFilter(Color.argb(255, 255, 255, 255));
                        tv1.setTextColor(Color.argb(255, 66, 66, 66));
                        tv2.setTextColor(Color.argb(255, 255, 255, 255));
                        tv3.setTextColor(Color.argb(255, 255, 255, 255));
                        tv4.setTextColor(Color.argb(255, 255, 255, 255));
                        break;
                    case 1:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(MainActivity.this, R.color.text_toolbar) & 0xffffff)+"'>TENTANG KAMI</font>"));
                        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        actionBar.setTitle(SS);
                        actionBar.show();
                        loTop.setVisibility(View.GONE);
                        break;
                    case 2:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(MainActivity.this, R.color.text_toolbar) & 0xffffff)+"'>BERITA</font>"));
                        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        actionBar.setTitle(SS);
                        actionBar.show();
                        loTop.setVisibility(View.GONE);
                        break;
                    case 3:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(MainActivity.this, R.color.text_toolbar) & 0xffffff)+"'>MORE</font>"));
                        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        actionBar.setTitle(SS);
                        actionBar.show();
                        loTop.setVisibility(View.GONE);
                        break;
                    default:
                        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#9E9E9E"), PorterDuff.Mode.SRC_IN);
                        SS = new SpannableStringBuilder(Html.fromHtml("<font color='"+String.format("#%06x", ContextCompat.getColor(MainActivity.this, R.color.text_toolbar) & 0xffffff)+"'>HOME</font>"));
                        SS.setSpan (new CustomTypefaceSpan("", type), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        actionBar.setTitle(SS);
                        actionBar.hide();
                        loTop.setVisibility(View.VISIBLE);

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

    public void setCustomFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/barclays.ttf"));
                }
            }
        }
    }
}

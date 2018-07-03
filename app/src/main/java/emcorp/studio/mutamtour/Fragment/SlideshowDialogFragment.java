package emcorp.studio.mutamtour.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import emcorp.studio.mutamtour.Library.Image;
import emcorp.studio.mutamtour.R;


public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Image> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    final int REQUEST_WRITE_STORAGE = 101;
    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
//        checkWritingPermission();
        boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_STORAGE);
        }
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);

        images = (ArrayList<Image>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);


        return v;
    }



    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + images.size());

        Image image = images.get(position);
        lblTitle.setText(image.getName());
        lblDate.setText(image.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            final ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
//            ImageButton btnShare = (ImageButton) view.findViewById(R.id.btnShare);
//            ImageButton btnHome = (ImageButton) view.findViewById(R.id.btnHome);
            TextView tvCaption = (TextView) view.findViewById(R.id.tvCaption);

//            btnShare.setOnTouchListener(new ButtonClick());
//            btnHome.setOnTouchListener(new ButtonClick());
            Image image = images.get(position);
            tvCaption.setText(image.getName());
//            Glide.with(getActivity()).load(image.getLarge())
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imageViewPreview);

//            Picasso.with(getActivity()).load(image.getLarge()).into(imageViewPreview);
            Picasso.with(getActivity())
                    .load(image.getFoto())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imageViewPreview);
            Bitmap bmp;
            try {
                URL link = new URL(image.getFoto());
                bmp = getImageBitmapFromUrl(link);
//                if(bmp==null){
//                    Toast.makeText(getContext(),"Image Null",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getContext(),"Image Not Null",Toast.LENGTH_SHORT).show();
//                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
//            btnShare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                     Get access to bitmap image from view
////                     Get access to the URI for the bitmap
//                    Uri bmpUri = getLocalBitmapUri(imageViewPreview);
//                    if (bmpUri != null) {
////                        // Construct a ShareIntent with link to image
//                        Intent shareIntent = new Intent();
//                        shareIntent.setAction(Intent.ACTION_SEND);
//                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                        shareIntent.setType("image/*");
//                        // Launch sharing dialog for image
//                        startActivity(Intent.createChooser(shareIntent, "Share Image"));
//                    } else {
////                        // ...sharing failed, handle error
//                        Toast.makeText(getContext(),"Sharing error", Toast.LENGTH_SHORT).show();
//                    }
////                    Toast.makeText(getContext(),"Share foto ini",Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            btnHome.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(getActivity(), GalleryActivity.class);
//                    startActivity(i);
//                    getActivity().finish();
//                }
//            });

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public static Bitmap getImageBitmapFromUrl(URL url)
    {
        Bitmap bm = null;
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn.getResponseCode() != 200)
            {
                return bm;
            }
            conn.connect();
            InputStream is = conn.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is);
            try
            {
                bm = BitmapFactory.decodeStream(bis);
            }
            catch(OutOfMemoryError ex)
            {
                bm = null;
            }
            bis.close();
            is.close();
        } catch (Exception e) {}

        return bm;
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {

            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "kartu_lebaran_super_keren_" + System.currentTimeMillis() + ".jpg");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
//            Toast.makeText(getContext(),"Save kartu lebaran super keren sukses",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(getContext(),"Save kartu lebaran super keren gagal "+e.toString(),Toast.LENGTH_LONG).show();
        }
        return bmpUri;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                } else
                {
                    Toast.makeText(getActivity(), "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}

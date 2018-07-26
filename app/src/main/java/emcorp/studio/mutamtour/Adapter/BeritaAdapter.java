package emcorp.studio.mutamtour.Adapter;

/**
 * Created by ASUS on 27/11/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import emcorp.studio.mutamtour.Library.Constant;
import emcorp.studio.mutamtour.R;

public class BeritaAdapter extends ArrayAdapter<String> {
    private final Activity context;
    List<String> listid = new ArrayList<String>();
    List<String> listjudul = new ArrayList<String>();
    List<String> listisi = new ArrayList<String>();
    List<String> listthumbnail = new ArrayList<String>();
    List<String> listcreated_date = new ArrayList<String>();
    public BeritaAdapter(Activity context,
                         List<String> listid, List<String> listjudul, List<String> listisi, List<String> listthumbnail, List<String> listcreated_date) {
        super(context, R.layout.berita_list, listid);
        this.context = context;
        this.listid = listid;
        this.listjudul = listjudul;
        this.listisi = listisi;
        this.listthumbnail = listthumbnail;
        this.listcreated_date = listcreated_date;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.berita_list, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        TextView short_content = (TextView) rowView.findViewById(R.id.short_content);
        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        title.setText(listjudul.get(position));
        date.setText(listcreated_date.get(position));
        short_content.setText(listisi.get(position).substring(0, 80)+"...");
        /*Picasso.with(getContext())
                .load(Constant.PICT_URL+listthumbnail.get(position))
                .error(R.drawable.ic_logo)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(image);*/
        Glide.with(getContext())
                .load(Constant.PICT_URL+listthumbnail.get(position))
                .error(R.drawable.ic_logo)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        return rowView;
    }


}
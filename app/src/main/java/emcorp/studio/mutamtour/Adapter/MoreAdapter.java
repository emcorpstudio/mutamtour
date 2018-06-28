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

import java.util.ArrayList;
import java.util.List;

import emcorp.studio.mutamtour.R;

public class MoreAdapter extends ArrayAdapter<String> {
    private final Activity context;
    List<String> listjudul = new ArrayList<String>();
    List<Integer> listthumbnail = new ArrayList<Integer>();
    public MoreAdapter(Activity context,
                       List<String> listjudul, List<Integer> listthumbnail) {
        super(context, R.layout.more_list, listjudul);
        this.context = context;
        this.listjudul = listjudul;
        this.listthumbnail = listthumbnail;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.more_list, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        title.setText(listjudul.get(position));
        image.setImageDrawable(getContext().getResources().getDrawable(listthumbnail.get(position)));
        return rowView;
    }


}
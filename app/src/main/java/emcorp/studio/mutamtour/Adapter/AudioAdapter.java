package emcorp.studio.mutamtour.Adapter;

/**
 * Created by ASUS on 27/11/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import emcorp.studio.mutamtour.R;

public class AudioAdapter extends ArrayAdapter<String> {
    private final Activity context;
    List<String> listid = new ArrayList<String>();
    List<String> listjudul = new ArrayList<String>();
    List<String> listisi = new ArrayList<String>();
    public AudioAdapter(Activity context,
                        List<String> listid, List<String> listjudul, List<String> listisi) {
        super(context, R.layout.testimoni_list, listid);
        this.context = context;
        this.listid = listid;
        this.listjudul = listjudul;
        this.listisi = listisi;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.audio_list, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText(listjudul.get(position));
        return rowView;
    }


}
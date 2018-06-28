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

public class PendaftarAdapter extends ArrayAdapter<String> {
    private final Activity context;
    List<String> listrecid = new ArrayList<String>();
    List<String> listnama = new ArrayList<String>();
    List<String> listtgl_lahir = new ArrayList<String>();
    List<String> listjk = new ArrayList<String>();
    List<String> listhp = new ArrayList<String>();
    List<String> listemail = new ArrayList<String>();
    List<String> listalamat = new ArrayList<String>();
    List<String> listprovinsi = new ArrayList<String>();
    List<String> listkota = new ArrayList<String>();
    List<String> listkecamatan = new ArrayList<String>();
    List<String> listdesa = new ArrayList<String>();
    public PendaftarAdapter(Activity context,
                            List<String> listrecid,List<String> listnama,List<String> listtgl_lahir,List<String> listjk,List<String> listhp,List<String> listemail,List<String> listalamat,List<String> listprovinsi,List<String> listkota,List<String> listkecamatan,List<String> listdesa) {
        super(context, R.layout.pendaftar_list, listrecid);
        this.context = context;
        this.listrecid = listrecid;
        this.listnama = listnama;
        this.listtgl_lahir = listtgl_lahir;
        this.listjk = listjk;
        this.listhp = listhp;
        this.listemail = listemail;
        this.listalamat = listalamat;
        this.listprovinsi = listprovinsi;
        this.listkota = listkota;
        this.listkecamatan = listkecamatan;
        this.listdesa = listdesa;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.pendaftar_list, null, true);
        TextView tvNama = (TextView) rowView.findViewById(R.id.tvNama);
        TextView tvTglLahir = (TextView) rowView.findViewById(R.id.tvTglLahir);
        TextView tvAlamat = (TextView) rowView.findViewById(R.id.tvAlamat);
        TextView tvHp = (TextView) rowView.findViewById(R.id.tvHp);
        TextView tvEmail = (TextView) rowView.findViewById(R.id.tvEmail);
        tvNama.setText(listnama.get(position));
        tvTglLahir.setText(listtgl_lahir.get(position));
        tvHp.setText(listhp.get(position));
        tvEmail.setText(listemail.get(position));
        tvAlamat.setText(listalamat.get(position)+" "+listdesa.get(position)+" "+listkecamatan.get(position)+" "+listkota.get(position)+" "+listprovinsi.get(position));
        return rowView;
    }


}
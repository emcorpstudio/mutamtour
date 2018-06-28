package emcorp.studio.mutamtour.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import emcorp.studio.mutamtour.Adapter.MoreAdapter;
import emcorp.studio.mutamtour.BantuanActivity;
import emcorp.studio.mutamtour.CaraDaftarActivity;
import emcorp.studio.mutamtour.ContactUsActivity;
import emcorp.studio.mutamtour.Library.SharedFunction;
import emcorp.studio.mutamtour.LoginActivity;
import emcorp.studio.mutamtour.PendaftarActivity;
import emcorp.studio.mutamtour.R;
import emcorp.studio.mutamtour.TestimoniActivity;
import emcorp.studio.mutamtour.WebsiteActivity;


public class MoreFragment extends Fragment {
    private ProgressDialog progressDialog;
    List<String> listjudul = new ArrayList<String>();
    List<Integer> listthumbnail = new ArrayList<Integer>();
    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        list = (ListView) view.findViewById(R.id.listView);

        listjudul.clear();
        listjudul.add("Gallery");
        listjudul.add("Cara Daftar");
        listjudul.add("Testimoni");
        listjudul.add("Pendaftaran");
        listjudul.add("Pendaftar");
        listjudul.add("Bantuan");
        listjudul.add("Contact Us");
        listjudul.add("Website");
        listjudul.add("Logout");

        listthumbnail.clear();
        listthumbnail.add(R.drawable.ic_gallery);
        listthumbnail.add(R.drawable.ic_cara);
        listthumbnail.add(R.drawable.ic_testimoni);
        listthumbnail.add(R.drawable.ic_pendaftaran);
        listthumbnail.add(R.drawable.ic_pendaftar);
        listthumbnail.add(R.drawable.ic_help);
        listthumbnail.add(R.drawable.ic_contact);
        listthumbnail.add(R.drawable.ic_website);
        listthumbnail.add(R.drawable.ic_logout);

        getAllData();

        return view;
    }

    public void getAllData(){
        list.setAdapter(null);
        MoreAdapter adapter = new MoreAdapter(getActivity(), listjudul,listthumbnail);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(),listjudul.get(position),Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0 :
                        Toast.makeText(getContext(),"Segera...",Toast.LENGTH_SHORT).show();
                        break;
                    case 1 :
                        CaraDaftar();
                        break;
                    case 2 :
                        Testimoni();
                        break;
                    case 3 :
                        Toast.makeText(getContext(),"Segera...",Toast.LENGTH_SHORT).show();
                        break;
                    case 4 :
                        Pendaftar();
                        break;
                    case 5 :
                        Bantuan();
                        break;
                    case 6 :
                        ContactUs();
                        break;
                    case 7 :
                        Website();
                        break;
                    case 8 :
                        LogoutProcess();
                        break;
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                dialogAdd(position);
                return true;
            }
        });
    }

    public void Testimoni(){
        Intent i = new Intent(getContext(), TestimoniActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    public void Pendaftar(){
        Intent i = new Intent(getContext(), PendaftarActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    public void CaraDaftar(){
        Intent i = new Intent(getContext(), CaraDaftarActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    public void Bantuan(){
        Intent i = new Intent(getContext(), BantuanActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    public void Website(){
        Intent i = new Intent(getContext(), WebsiteActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    public void ContactUs(){
        Intent i = new Intent(getContext(), ContactUsActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    public void LogoutProcess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.ic_logo);
        builder.setMessage("Anda yakin ingin logout?");
        String positiveText = "Ya";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("Mutamtour", Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        SharedFunction.getInstance(getContext()).openActivityFinish(LoginActivity.class);
                        Toast.makeText(getContext(),"Logout berhasil",Toast.LENGTH_LONG).show();
                    }
                });
        String negativeText = "Tidak";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}

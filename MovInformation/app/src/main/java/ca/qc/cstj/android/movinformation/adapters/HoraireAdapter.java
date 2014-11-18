package ca.qc.cstj.android.movinformation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import ca.qc.cstj.android.movinformation.R;
import ca.qc.cstj.android.movinformation.models.Cinemas;
import ca.qc.cstj.android.movinformation.models.Commentaires;
import ca.qc.cstj.android.movinformation.models.Horaires;

/**
 * Created by 1232295 on 2014-11-14.
 */



public class HoraireAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Horaires> horaires;

    public HoraireAdapter(Context context, LayoutInflater inflater, ArrayList<Horaires> pHoraires) {
        mContext = context;
        mInflater = inflater;
        horaires = pHoraires;
    }

    @Override
    public int getCount() {
        return horaires.size();
    }

    @Override
    public Horaires getItem(int position) {
        return horaires.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HorairesViewHolder viewHolder;

        //Est-ce que la view n'existe pas
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_horaires,null);
            viewHolder = new HorairesViewHolder();
            viewHolder.txtNomFilm = (TextView) convertView.findViewById(R.id.row_nom_film);
            viewHolder.Date1 = (TextView) convertView.findViewById(R.id.row_date1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HorairesViewHolder) convertView.getTag();
        }

        Horaires horaire = getItem(position);

        if(horaire.getFilm() != null) {
            viewHolder.txtNomFilm.setText(horaire.getFilm().getTitre());

            String date = horaire.getDateHeure().toString();
            String[] parties = date.split("T");
            String[] parties2 = parties[1].split(":");

            viewHolder.Date1.setText(parties[0]+", "+parties2[0]+":"+parties2[1]);
        } else {
            viewHolder.txtNomFilm.setText("Nom inconnu");
        }

        return convertView;

    }

    private static class HorairesViewHolder{
        public TextView txtNomFilm;
        public TextView Date1;

    }
}

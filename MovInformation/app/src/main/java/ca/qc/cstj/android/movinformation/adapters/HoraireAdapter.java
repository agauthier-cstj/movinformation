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
import ca.qc.cstj.android.movinformation.models.Films;
import ca.qc.cstj.android.movinformation.models.Horaires;

/**
 * Created by 1232295 on 2014-11-14.
 */



public class HoraireAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Films> films;

    public HoraireAdapter(Context context, LayoutInflater inflater, ArrayList<Films> pFilms) {
        mContext = context;
        mInflater = inflater;
        films = pFilms;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Films getItem(int position) {
        return films.get(position);
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
            viewHolder.Date2 = (TextView) convertView.findViewById(R.id.row_date2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HorairesViewHolder) convertView.getTag();
        }

        Films film = getItem(position);

        if(film != null) {
            viewHolder.txtNomFilm.setText(film.getTitre());

            String date1 = film.getListeHoraire().get(0).getDateHeure().toString("yyyy-MM-dd HH:mm");
            if(film.getListeHoraire().size() > 1) {
                String date2 = film.getListeHoraire().get(1).getDateHeure().toString("yyyy-MM-dd HH:mm");
                viewHolder.Date2.setText(date2);
            }

            viewHolder.Date1.setText(date1);

        } else {
            viewHolder.txtNomFilm.setText("Nom inconnu");
        }

        return convertView;

    }

    private static class HorairesViewHolder{
        public TextView txtNomFilm;
        public TextView Date1;
        public TextView Date2;

    }
}

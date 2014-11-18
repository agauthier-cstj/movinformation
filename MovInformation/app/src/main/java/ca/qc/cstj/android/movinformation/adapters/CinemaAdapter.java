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



public class CinemaAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Cinemas> cinemas;

    public CinemaAdapter(Context context, LayoutInflater inflater, ArrayList<Cinemas> pCinemas) {
        mContext = context;
        mInflater = inflater;
        cinemas = pCinemas;
    }

    @Override
    public int getCount() {
        return cinemas.size();
    }

    @Override
    public Cinemas getItem(int position) {
        return cinemas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CinemasViewHolder viewHolder;

        //Est-ce que la view n'existe pas
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_cinema,null);
            viewHolder = new CinemasViewHolder();
            viewHolder.txtNomCinema = (TextView) convertView.findViewById(R.id.row_nom_cinema);
            viewHolder.txtAdresse = (TextView) convertView.findViewById(R.id.row_adresse);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CinemasViewHolder) convertView.getTag();
        }

        Cinemas cinema = getItem(position);

        if(cinema.getNom() != null) {
            viewHolder.txtNomCinema.setText(cinema.getNom());
            viewHolder.txtAdresse.setText(cinema.getAdresse());

        } else {
            viewHolder.txtNomCinema.setText("Nom inconnu");
        }

        return convertView;

    }

    private static class CinemasViewHolder{
        public TextView txtNomCinema;
        public TextView txtAdresse;

    }
}

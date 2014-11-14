package ca.qc.cstj.android.movinformation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.ArrayList;

import ca.qc.cstj.android.movinformation.R;
import ca.qc.cstj.android.movinformation.models.Cinemas;
import ca.qc.cstj.android.movinformation.models.Commentaires;
import ca.qc.cstj.android.movinformation.models.Horaires;

/**
 * Created by 1232295 on 2014-11-14.
 */
public class HoraireAdapter extends ArrayAdapter<Horaires> {
    private ArrayList<Horaires> horaires;
    private LayoutInflater layoutInflater;
    public JsonArray array;

    public Cinemas cinemas;

    public HoraireAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Horaires> listeHoraires) {
        super(context, R.layout.listitem_horaires, listeHoraires);
        this.horaires = listeHoraires;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HorairesViewHolder horairesViewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.listitem_horaires,null,true);
            horairesViewHolder = new HorairesViewHolder();
            horairesViewHolder.txtNomFilm = (TextView)convertView.findViewById(R.id.txtNomFilm);

        } else {
            horairesViewHolder = (HorairesViewHolder)convertView.getTag();
        }

        Horaires commentaire = getItem(position);

        return convertView;
    }

    private static class HorairesViewHolder{
        public TextView txtNomFilm;
    }
}

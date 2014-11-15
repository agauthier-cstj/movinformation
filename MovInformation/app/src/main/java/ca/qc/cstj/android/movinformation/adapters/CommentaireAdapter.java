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
import ca.qc.cstj.android.movinformation.models.Commentaires;
import ca.qc.cstj.android.movinformation.models.Films;

/**
 * Created by Anthony on 2014-11-11.
 */
public class CommentaireAdapter extends ArrayAdapter<Commentaires> {
    private ArrayList<Commentaires> commentaires;
    private LayoutInflater layoutInflater;
    public JsonArray array;

    private Films film;

    public CommentaireAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Commentaires> listeCommentaire){
        super(context, R.layout.listitem_commentaires , listeCommentaire);
        this.commentaires = listeCommentaire;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentairesViewHolder commentairesViewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.listitem_commentaires,null,true);
            commentairesViewHolder = new CommentairesViewHolder();
            commentairesViewHolder.txtCommentaire = (TextView)convertView.findViewById(R.id.txtCommentaire);
            commentairesViewHolder.txtPseudoDate = (TextView)convertView.findViewById(R.id.txtPseudoDate);
            commentairesViewHolder.txtNote = (TextView)convertView.findViewById(R.id.txtNote);
        } else {
            commentairesViewHolder = (CommentairesViewHolder)convertView.getTag();
        }

        Commentaires commentaire = getItem(position);

        String date = commentaire.getDate();
        String[] parties = date.split("T");
        date = parties[0];

        commentairesViewHolder.txtCommentaire.setText(commentaire.getCommentaire());
        commentairesViewHolder.txtPseudoDate.setText(commentaire.getPseudo()+", "+date);
        commentairesViewHolder.txtNote.setText("Note: "+commentaire.getNote()+"/5");

        return convertView;
    }

    private static class CommentairesViewHolder {
        public TextView txtPseudoDate;
        public TextView txtCommentaire;
        public TextView txtNote;
    }
}

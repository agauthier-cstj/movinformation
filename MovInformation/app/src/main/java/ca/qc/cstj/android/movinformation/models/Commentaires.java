package ca.qc.cstj.android.movinformation.models;

import com.google.gson.JsonObject;

/**
 * Created by Anthony on 2014-11-11.
 */
public class Commentaires {

    private String pseudo;
    private String commentaire;
    private String note;
    private String date;

    public Commentaires() {}

    public Commentaires(JsonObject jsonObject){

        pseudo = jsonObject.getAsJsonPrimitive("auteur").getAsString();
        commentaire = jsonObject.getAsJsonPrimitive("texte").getAsString();
        note = jsonObject.getAsJsonPrimitive("note").getAsString();
        date = jsonObject.getAsJsonPrimitive("dateHeure").getAsString();
    }


    public String getCommentaire() {
        return commentaire;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}

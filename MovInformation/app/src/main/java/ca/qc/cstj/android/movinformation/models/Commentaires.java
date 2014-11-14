package ca.qc.cstj.android.movinformation.models;

/**
 * Created by Anthony on 2014-11-11.
 */
public class Commentaires {

    private String pseudo;
    private String commentaire;
    private String note;
    private String date;


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

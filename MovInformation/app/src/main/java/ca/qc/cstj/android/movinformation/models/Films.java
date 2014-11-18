package ca.qc.cstj.android.movinformation.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by 1247935 on 2014-10-31.
 */
public class Films {
    private String href;
    private String titre;
    private String pays;
    private String genre;
    private String classe;
    private String duree;
    private String realisateur;
    private String imageUrl;


    private ArrayList<Horaires> ListeHoraire;

    public Films() {}

    public Films(JsonObject jsonObject){
        href = jsonObject.getAsJsonPrimitive("href").getAsString();
        if (jsonObject.has("titre")) {
            titre = jsonObject.getAsJsonPrimitive("titre").getAsString();
        }
        if (jsonObject.has("pays")) {
            pays = jsonObject.getAsJsonPrimitive("pays").getAsString();
        }
        if (jsonObject.has("genre")) {
            genre = jsonObject.getAsJsonPrimitive("genre").getAsString();
        }
        if (jsonObject.has("classe")) {
            classe = jsonObject.getAsJsonPrimitive("classe").getAsString();
        }
        if (jsonObject.has("duree")) {
            duree = jsonObject.getAsJsonPrimitive("duree").getAsString();
        }
        if (jsonObject.has("realisateur")) {
            realisateur = jsonObject.getAsJsonPrimitive("realisateur").getAsString();
        }
        if (jsonObject.has("horaires")) {

            ListeHoraire = new ArrayList<Horaires>();

            JsonArray horairesTemp = jsonObject.getAsJsonArray("horaires");
            // Pour chaque horaires, je crée un horaire dans la liste du film actuel.
            for(JsonElement jsonElement : horairesTemp)
            {
                ListeHoraire.add(new Horaires(jsonElement.getAsJsonObject()));
            }
        }
        try{
            imageUrl = jsonObject.getAsJsonPrimitive("imageUrl").getAsString();
        }
        catch (Exception e)
        {}
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<Horaires> getListeHoraire() {
        return ListeHoraire;
    }

    public void setListeHoraire(ArrayList<Horaires> listeHoraire) {
        ListeHoraire = listeHoraire;
    }
}


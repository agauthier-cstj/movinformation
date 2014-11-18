package ca.qc.cstj.android.movinformation.models;

import com.google.gson.JsonObject;

/**
 * Created by 1247935 on 2014-10-31.
 */
public class Cinemas {
    private String href;
    private String nom;
    private String adresse;
    private String ville;
    private String codePostal;
    private String telephone;

    public Cinemas() {}

    public Cinemas(JsonObject jsonObject){

        href = jsonObject.getAsJsonPrimitive("href").getAsString();
        if (jsonObject.has("nom")) {
            nom = jsonObject.getAsJsonPrimitive("nom").getAsString();
        }
        if (jsonObject.has("adresse")) {
            adresse = jsonObject.getAsJsonPrimitive("adresse").getAsString();
        }
        if (jsonObject.has("ville")) {
            ville = jsonObject.getAsJsonPrimitive("ville").getAsString();
        }
        if (jsonObject.has("codePostal")) {
            codePostal = jsonObject.getAsJsonPrimitive("codePostal").getAsString();
        }
        if (jsonObject.has("telephone")) {
            telephone = jsonObject.getAsJsonPrimitive("telephone").getAsString();
        }
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Fonction qui permet la comparaison d'un cinéma
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Cinemas cinema = (Cinemas) object;

        if (href != null ? !href.equals(cinema.href) : cinema.href != null) return false;

        return true;
    }
    // Fonction complémentaire pour la comparaison aussi.
    @Override
    public int hashCode() {
        int result = href != null ? href.hashCode() : 0;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
        result = 31 * result + (ville != null ? ville.hashCode() : 0);
        result = 31 * result + (codePostal != null ? codePostal.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        return result;
    }


    public JsonObject toJson()
    {
        JsonObject json = new JsonObject();

        String[] hrefPart = href.split("/");

        int idCinema = Integer.parseInt(hrefPart[hrefPart.length-1]);

        json.addProperty("idCinema", idCinema);

        if (nom != null && !nom.equals("")){
            json.addProperty("nom", nom);
        }
        if (adresse != null && !adresse.equals("")){
            json.addProperty("adresse", adresse);
        }
        if (ville != null && !ville.equals("")){
            json.addProperty("ville", ville);
        }
        if (codePostal != null && !codePostal.equals("")){
            json.addProperty("codePostal", codePostal);
        }
        if (telephone != null && !telephone.equals("")){
            json.addProperty("telephone", telephone);
        }

        return json;
    }
}

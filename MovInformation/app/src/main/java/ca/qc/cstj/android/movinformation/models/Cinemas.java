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
        nom = jsonObject.getAsJsonPrimitive("nom").getAsString();
        adresse = jsonObject.getAsJsonPrimitive("adresse").getAsString();
        ville = jsonObject.getAsJsonPrimitive("ville").getAsString();
        codePostal = jsonObject.getAsJsonPrimitive("codePostal").getAsString();
        telephone = jsonObject.getAsJsonPrimitive("telephone").getAsString();
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
}

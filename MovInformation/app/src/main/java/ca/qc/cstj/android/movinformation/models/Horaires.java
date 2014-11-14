package ca.qc.cstj.android.movinformation.models;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import ca.qc.cstj.android.movinformation.helpers.DateParser;

/**
 * Created by 1232295 on 2014-11-14.
 */
public class Horaires {
    private String nomFilm;
    private DateTime dateHeure;


    public Horaires() {}

    public Horaires(JsonObject jsonObject){
        nomFilm = jsonObject.getAsJsonPrimitive("nomFilm").getAsString();
        dateHeure = DateParser.ParseIso(jsonObject.getAsJsonPrimitive("dateHeure").getAsString());

    }

    public String getNomFilm() {
        return nomFilm;
    }

    public void setNomFilm(String nomFilm) {
        this.nomFilm = nomFilm;
    }

    public DateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(DateTime dateHeure) {
        this.dateHeure = dateHeure;
    }
}

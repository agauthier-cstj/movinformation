package ca.qc.cstj.android.movinformation.models;

import com.google.gson.JsonObject;

import org.joda.time.DateTime;

import ca.qc.cstj.android.movinformation.helpers.DateParser;

/**
 * Created by 1232295 on 2014-11-14.
 */
public class Horaires {
    private Films film;
    private DateTime dateHeure;


    public Horaires() {}

    public Horaires(JsonObject jsonObject){
        film = new Films(jsonObject.getAsJsonObject("film"));
        dateHeure = DateParser.ParseIso(jsonObject.getAsJsonPrimitive("dateHeure").getAsString());

    }

    public Films getFilm() {
        return film;
    }

    public void setFilm(Films film) {
        this.film = film;
    }

    public DateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(DateTime dateHeure) {
        this.dateHeure = dateHeure;
    }
}

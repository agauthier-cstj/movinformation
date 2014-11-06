package ca.qc.cstj.android.movinformation.adapters;

import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

import ca.qc.cstj.android.movinformation.models.Films;

/**
 * Created by Anthony on 2014-11-05.
 */
public class FilmAdapter extends GenericArrayAdapter<Films> {
    public FilmAdapter(Context context, int layoutRes, ArrayList<Films> films) {
        super(context,layoutRes,films);
    }

    @Override
    public void update(TextView textView, Films object) {
        String titre = object.getTitre().toString();
        textView.setText(titre);
    }
}

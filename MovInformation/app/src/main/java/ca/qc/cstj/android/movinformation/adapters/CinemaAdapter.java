package ca.qc.cstj.android.movinformation.adapters;

import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

import ca.qc.cstj.android.movinformation.models.Cinemas;

/**
 * Created by tommy on 2014-11-10.
 */
public class CinemaAdapter extends GenericArrayAdapter<Cinemas> {
    public CinemaAdapter(Context context, int layoutRes, ArrayList<Cinemas> cinemas) {
        super(context,layoutRes,cinemas);
    }

    @Override
    public void update(TextView textView, Cinemas object) {
        String titre = object.getNom().toString();
        textView.setText(titre);
    }

}

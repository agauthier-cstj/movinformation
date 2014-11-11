package ca.qc.cstj.android.movinformation;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import ca.qc.cstj.android.movinformation.adapters.CommentaireAdapter;
import ca.qc.cstj.android.movinformation.adapters.FilmAdapter;
import ca.qc.cstj.android.movinformation.models.Films;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HREF = "href";

    //Variables - Anthony Gauthier
    private ListView lstCommentaire;
    private ProgressDialog progressDialog;
    private FilmAdapter filmAdapter;
    private CommentaireAdapter commentaireAdapter;

    private String href;
    private TextView titreFilm;
    private TextView paysFilm;
    private TextView genreFilm;
    private TextView classeFilm;
    private TextView realisateurFilm;
    private TextView dureeFilm;
    private Button btnAjouterComment;

    private Films film;

    private OnFragmentInteractionListener mListener;

    public static DetailFragment newInstance(String href) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HREF, href);
        fragment.setArguments(args);
        return fragment;
    }
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            href = getArguments().getString(ARG_HREF);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        progressDialog = ProgressDialog.show(getActivity(), "MovInformation", "Chargement des données...",true,false);

        Ion.with(getActivity())
                .load(href)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        film = new Films(jsonObject);

                        titreFilm.setText(film.getTitre());
                        paysFilm.setText(film.getPays());
                        genreFilm.setText(film.getGenre());
                        classeFilm.setText(film.getClasse());
                        realisateurFilm.setText("Réalisé par: "+film.getRealisateur());
                        dureeFilm.setText("Durée: "+film.getDuree()+" minutes");
                        progressDialog.dismiss();
                    }
                });

        Ion.with(getActivity())
                .load(href+"/commentaires")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray jsonElements) {
                        for(JsonElement element : jsonElements)
                        {
                            //commentaireAdapter.getView();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        titreFilm = (TextView) view.findViewById(R.id.titreFilm);
        paysFilm = (TextView) view.findViewById(R.id.paysFilm);
        genreFilm = (TextView) view.findViewById(R.id.genreFilm);
        classeFilm = (TextView) view.findViewById(R.id.classeFilm);
        realisateurFilm = (TextView) view.findViewById(R.id.realisateurFilm);
        dureeFilm = (TextView) view.findViewById(R.id.dureeFilm);
        btnAjouterComment = (Button) view.findViewById(R.id.btnAjouterComment);

        btnAjouterComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

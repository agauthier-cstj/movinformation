package ca.qc.cstj.android.movinformation;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ca.qc.cstj.android.movinformation.adapters.CommentaireAdapter;
import ca.qc.cstj.android.movinformation.adapters.FilmAdapter;
import ca.qc.cstj.android.movinformation.helpers.LimitEditTextNumbers;
import ca.qc.cstj.android.movinformation.models.Commentaires;
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

    //Pour le film
    private String href;
    private TextView titreFilm;
    private TextView paysFilm;
    private TextView genreFilm;
    private TextView classeFilm;
    private TextView realisateurFilm;
    private TextView dureeFilm;
    private ImageView imageFilm;

    //Pour le commentaire
    private EditText texteCommentaire;
    private EditText pseudoCommentaire;
    private EditText noteCommentaire;
    private String dateCommentaire;

    //Le bouton du fragment
    private Button btnAjouterComment;

    private Films film;
    private Commentaires commentaire;

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

        loadDetails();
        loadCommentaires();
    }

    private void loadDetails() {
        progressDialog = ProgressDialog.show(getActivity(), "MovInformation", "Chargement des données...",true,false);

        //On affiche les détails du film
        Ion.with(getActivity())
                .load(href)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        film = new Films(jsonObject);

                        Ion.with(imageFilm)
                                .placeholder(R.drawable.notfound)
                                .load(film.getImageUrl().toString());

                        titreFilm.setText(film.getTitre());
                        paysFilm.setText(film.getPays());
                        genreFilm.setText(film.getGenre());
                        classeFilm.setText(film.getClasse());
                        realisateurFilm.setText("Réalisé par: "+film.getRealisateur());
                        dureeFilm.setText("Durée: "+film.getDuree()+" minutes");
                        progressDialog.dismiss();
                    }
                });
    }

    private void loadCommentaires()
    {
        lstCommentaire = (ListView) getActivity().findViewById(R.id.lstCommentaires);

        //On affiche les commentaires du film
        Ion.with(getActivity())
                .load(href+"/commentaires?order=yes")
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> jsonArrayResponse) {
                        if (jsonArrayResponse.getHeaders().getResponseCode() == HttpStatus.SC_OK) {
                            ArrayList<Commentaires> commentaires = new ArrayList<Commentaires>();
                            JsonArray jsonArray = jsonArrayResponse.getResult();
                            for (JsonElement element : jsonArray) {
                                commentaires.add(new Commentaires(element.getAsJsonObject()));
                            }
                            commentaireAdapter = new CommentaireAdapter(getActivity(), getActivity().getLayoutInflater(), commentaires);
                            lstCommentaire.setAdapter(commentaireAdapter);
                        } else if(jsonArrayResponse.getHeaders().getResponseCode() == HttpStatus.SC_NOT_FOUND) {
                            //Erreur 404 - Les commentaires n'existent pas n'existent pas.
                            Toast.makeText(getActivity(), "Les commentaires n'existent pas.", Toast.LENGTH_SHORT).show();
                        }else{
                            //Erreur 500
                            Toast.makeText(getActivity(), "Erreur interne.", Toast.LENGTH_SHORT).show();
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

        imageFilm = (ImageView) view.findViewById(R.id.imageFilm);

        texteCommentaire = (EditText) view.findViewById(R.id.editComment);
        pseudoCommentaire = (EditText) view.findViewById(R.id.pseudoComment);
        noteCommentaire = (EditText) view.findViewById(R.id.noteComment);
        //On définit un min et un max pour le EditText de nombre
        noteCommentaire.setFilters(new InputFilter[]{ new LimitEditTextNumbers("1", "5")});
        dateCommentaire = getDateTime();


        btnAjouterComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonObject jsonCommentaire = new JsonObject();
                jsonCommentaire.addProperty("texte",texteCommentaire.getText().toString());
                jsonCommentaire.addProperty("note",noteCommentaire.getText().toString());
                jsonCommentaire.addProperty("auteur",pseudoCommentaire.getText().toString());
                jsonCommentaire.addProperty("dateHeure",dateCommentaire);

                Ion.with(getActivity())
                        .load(href+"/commentaires")
                        .setJsonObjectBody(jsonCommentaire)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject jsonObject) {
                                Toast.makeText(getActivity(), "Commentaire ajouté avec succès", Toast.LENGTH_SHORT).show();
                                loadCommentaires();
                                texteCommentaire.setText("");
                                noteCommentaire.setText("");
                                pseudoCommentaire.setText("");
                            }
                        });
            }
        });

        return view;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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
    public void onResume() {
        super.onResume();
        //On remet le titre du fragment a ce qu'il doit être
        getActivity().getActionBar()
                .setTitle(R.string.title_section2);
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

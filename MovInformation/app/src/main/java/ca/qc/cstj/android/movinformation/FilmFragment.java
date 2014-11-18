package ca.qc.cstj.android.movinformation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import java.util.ArrayList;

import ca.qc.cstj.android.movinformation.adapters.FilmAdapter;
import ca.qc.cstj.android.movinformation.models.Films;
import ca.qc.cstj.android.movinformation.services.ServicesURI;


/*
* Créer par Anthony Gauthier - 5 Novembre 2014
* */
public class FilmFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_SECTION_NUMBER = "section_number";

    //Variables - Anthony Gauthier
    private ListView lstFilms;
    private ProgressDialog progressDialog;
    private FilmAdapter filmAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Retourne une nouvelle instance du fragment film - Anthony Gauthier
    public static FilmFragment newInstance(int sectionNumber) {
        FilmFragment fragment = new FilmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public FilmFragment() {}

    //Si on créer la vue, on inflate le fragment, mais d'habitude on remplace le fragment et on ne le créer pas. - Anthony Gauthier
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_film, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swpLayout);

        swipeRefreshLayout.setOnRefreshListener(this);

        // Inflate the layout for this fragment
        return rootView;
    }

    //Méthode qui appelle la méthode pour remplir la ListView et créer un "listener" pour savoir si l'utilisateur a cliqué sur un film de la liste. - Anthony Gauthier
    @Override
    public void onStart() {
        super.onStart();

        lstFilms = (ListView) getActivity().findViewById(R.id.list_films);

        //Fonction qui charge les films dans la liste, avec un ProgressDialog pour faire
        //Comprendre à l'utilisateur que cela peut prendre un peu de temps. - Anthony Gauthier
        loadFilms();

        lstFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String href = filmAdapter.getItem(position).getHref();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, DetailFragment.newInstance(href)).addToBackStack("");
                transaction.commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //On remet le titre du fragment a ce qu'il doit être
        getActivity().getActionBar()
                .setTitle(R.string.title_section2);
    }

    //Sert à remplir la ListView - Anthony Gauthier
    private void loadFilms() {
        progressDialog = ProgressDialog.show(getActivity(), "MovInformation", "Chargement des films...", true, false);

        Ion.with(getActivity())
                .load(ServicesURI.FILMS_SERVICE_URI)
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> jsonArrayResponse) {

                        if(jsonArrayResponse.getHeaders().getResponseCode() == HttpStatus.SC_OK)
                        {
                            ArrayList<Films> films = new ArrayList<Films>();
                            JsonArray jsonArray = jsonArrayResponse.getResult();
                            for(JsonElement element : jsonArray)
                            {
                                films.add(new Films(element.getAsJsonObject()));
                            }
                            filmAdapter = new FilmAdapter(getActivity(), android.R.layout.simple_list_item_1,films);
                            lstFilms.setAdapter(filmAdapter);
                        }else if(jsonArrayResponse.getHeaders().getResponseCode() == HttpStatus.SC_NOT_FOUND) {
                            //Erreur 404 - Les films n'existent pas n'existent pas.
                            Toast.makeText(getActivity(), "Les films n'existent pas.", Toast.LENGTH_SHORT).show();
                        }else{
                            //Erreur 500
                            Toast.makeText(getActivity(), "Erreur interne.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    //Interface obligatoire à avoir  - Anthony Gauthier
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFilms();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}

package ca.qc.cstj.android.movinformation;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.apache.http.HttpStatus;

import java.util.ArrayList;

import ca.qc.cstj.android.movinformation.adapters.FilmAdapter;
import ca.qc.cstj.android.movinformation.adapters.HoraireAdapter;
import ca.qc.cstj.android.movinformation.models.Films;
import ca.qc.cstj.android.movinformation.models.Horaires;
import ca.qc.cstj.android.movinformation.services.ServicesURI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HoraireFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HoraireFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HoraireFragment extends Fragment {
    private static final String ARG_HREF = "href";

    private ListView lstHoraires;
    private ProgressDialog progressDialog;
    private HoraireAdapter horaireAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static HoraireFragment newInstance(String href) {
        HoraireFragment fragment = new HoraireFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HREF, href);
        fragment.setArguments(args);
        return fragment;
    }
    public HoraireFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_film, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        lstHoraires = (ListView) getActivity().findViewById(R.id.list_horaires);

        //Fonction qui charge les films dans la liste, avec un ProgressDialog pour faire
        //Comprendre à l'utilisateur que cela peut prendre un peu de temps. - Anthony Gauthier
        loadHoraires();

        lstHoraires.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                FragmentTransaction transaction = getFragmentManager().beginTransaction();
               // transaction.replace(R.id.container, DetailFragment.newInstance(href)).addToBackStack("");
                transaction.commit();
            }
        });
    }

    private void loadHoraires() {
        progressDialog = ProgressDialog.show(getActivity(), "MovInformation", "Chargement des horaires...", true, false);



        Ion.with(getActivity())
                .load(ServicesURI.CINEMAS_SERVICE_URI)
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> jsonArrayResponse) {

                        if(jsonArrayResponse.getHeaders().getResponseCode() == HttpStatus.SC_OK)
                        {
                            ArrayList<Horaires> horaires = new ArrayList<Horaires>();
                            JsonArray jsonArray = jsonArrayResponse.getResult();
                            for(JsonElement element : jsonArray)
                            {
                                horaires.add(new Horaires(element.getAsJsonObject()));
                            }
                            horaireAdapter = new HoraireAdapter(getActivity().getBaseContext(), getActivity().getLayoutInflater(), horaires);
                            lstHoraires.setAdapter(horaireAdapter);
                        }else{
                            //Erreur 404 - Les films n'existent pas.
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        public void onFragmentInteraction(Uri uri);
    }

}

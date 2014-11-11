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

import ca.qc.cstj.android.movinformation.adapters.CinemaAdapter;
import ca.qc.cstj.android.movinformation.adapters.FilmAdapter;
import ca.qc.cstj.android.movinformation.models.Cinemas;
import ca.qc.cstj.android.movinformation.services.ServicesURI;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CinemaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CinemaFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CinemaFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    //Variables - Tommy
    private ListView lstCinemas;
    private ProgressDialog progressDialog;
    private CinemaAdapter cinemaAdapter;

    private OnFragmentInteractionListener mListener;

    public static CinemaFragment newInstance(int sectionNumber) {
        CinemaFragment fragment = new CinemaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public CinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cinema, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        lstCinemas = (ListView) getActivity().findViewById(R.id.list_cinemas);

        loadCinemas();

        lstCinemas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String href = cinemaAdapter.getItem(position).getHref();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, DetailFragment.newInstance(href)).addToBackStack("");
                transaction.commit();
            }
        });

    }

    private void loadCinemas(){
        progressDialog = ProgressDialog.show(getActivity(), "MovInformation", "Chargement des films...", true, false);

        Ion.with(getActivity())
                .load(ServicesURI.CINEMAS_SERVICE_URI)
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> jsonArrayResponse) {
                        if(jsonArrayResponse.getHeaders().getResponseCode() == HttpStatus.SC_OK)
                        {
                            ArrayList<Cinemas> cinemas = new ArrayList<Cinemas>();
                            JsonArray jsonArray = jsonArrayResponse.getResult();
                            for(JsonElement element : jsonArray)
                            {
                                cinemas.add(new Cinemas(element.getAsJsonObject()));
                            }
                            cinemaAdapter = new CinemaAdapter(getActivity(), android.R.layout.simple_list_item_1, cinemas);
                            lstCinemas.setAdapter((cinemaAdapter));
                        }else{
                         // Autres erreurs
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
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
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

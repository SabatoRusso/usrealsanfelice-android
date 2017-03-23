package app.srusso.usrealsanfelice.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.srusso.usrealsanfelice.DatiActivity;
import app.srusso.usrealsanfelice.R;
import app.srusso.usrealsanfelice.adapter.ClassificaAdapter;
import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.config.Config;
import app.srusso.usrealsanfelice.to.Giocatore;
import app.srusso.usrealsanfelice.to.Squadra;
import cz.msebera.android.httpclient.Header;

/**
 * Created by italdata on 02/12/16.
 */

public class ClassificaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ListView listaClassifica;
    public List<Squadra> listaSquadre  = new ArrayList<Squadra>();
    public ActivityBase activity;
    public ClassificaAdapter adapterC;;


    public ClassificaFragment() {


    }




    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DatiActivity.PlaceholderFragment newInstance(int sectionNumber) {
        DatiActivity.PlaceholderFragment fragment = new DatiActivity.PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classifica, container, false);

        listaClassifica = (ListView) rootView.findViewById(R.id.listaClassifica);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listaClassifica.setNestedScrollingEnabled(true);
        }
        caricaClassifica();
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ActivityBase){
            activity =  (ActivityBase) getActivity();
        }

    }



    public  void load(){
        adapterC  = new ClassificaAdapter(getActivity(),listaSquadre);
        listaClassifica.setAdapter(adapterC);
    }

    public void caricaClassifica() {

        activity.clientHttpClient.get(Config.pathClassifica, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray risp) {
                // called when response HTTP status is "200 OK"





                for (int i = 0; i < risp.length(); i++) {
                    try {
                        JSONObject squadraJson = risp.getJSONObject(i);


                        String nomeSquadra = squadraJson.getString("squadra");


                       // int punti  =calcolaPunti( squadraJson.getInt("vinte"), squadraJson.getInt("pareggiate"));

                       int punti = squadraJson.getInt("punti");

                        Squadra squadra = new Squadra();
                        squadra.setNome(nomeSquadra);
                        squadra.setPunti(punti);
                        squadra.setPosizione(i+1);

                        Log.i("nome",nomeSquadra);




                        listaSquadre.add(squadra);





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }



                load();

            }


            public int calcolaPunti(int vinte,int pareggi){

                int punti = vinte * 3;
                punti += pareggi*1;

                return  punti;

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                //  progressDialog.hide();
                //  showToast(getResources().getString(R.string.erroreConnessione));
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                //   progressDialog.hide();
                //   showToast(getResources().getString(R.string.erroreConnessione));
            }
        });
    }
}

package app.srusso.usrealsanfelice.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import app.srusso.usrealsanfelice.to.Squadra;
import cz.msebera.android.httpclient.Header;

/**
 * Created by italdata on 02/12/16.
 */

public class ProssimoTurnoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ActivityBase activity;

    public LinearLayout contenitore_giornata;
    public TextView giornata;
    public TextView data_giornata;

    public ProssimoTurnoFragment() {


    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prossimoturno, container, false);

        contenitore_giornata = (LinearLayout) rootView.findViewById(R.id.contenitore_giornata);
        data_giornata = (TextView) rootView.findViewById(R.id.data_giornata);
        giornata = (TextView) rootView.findViewById(R.id.giornata);
        prossimoTurno();
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

    }


    public void aggiungiRow(String locale, String ospiti){

        View child = getActivity().getLayoutInflater().inflate(R.layout.row_squ_calendario, null);
        TextView localeText = (TextView) child.findViewById(R.id.locale);
        TextView ospitiText = (TextView) child.findViewById(R.id.ospiti);

        localeText.setText(locale + "");

        if(locale.equalsIgnoreCase("real san felice")){
            localeText.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }

        ospitiText.setText(ospiti + "");

        if(ospiti.equalsIgnoreCase("real san felice")){
            ospitiText.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        }

        contenitore_giornata.addView(child);
    }

    public void prossimoTurno() {

        activity.clientHttpClient.get(Config.pathProssimoTurno, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject risp) {
                // called when response HTTP status is "200 OK"

                JSONArray response = null;
               try {
                    response = risp.getJSONArray("partite");
                     giornata.setText(risp.getString("GIORNATA")+ " Giornata");
                     data_giornata.setText(risp.getString("DATA"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }



                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject squadraJson = response.getJSONObject(i);

                        String locali = squadraJson.getString("locali");
                        String ospiti = squadraJson.getString("ospiti");




                        aggiungiRow(locali,ospiti);






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }



                load();

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

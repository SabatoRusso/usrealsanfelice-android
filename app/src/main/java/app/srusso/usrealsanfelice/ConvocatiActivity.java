package app.srusso.usrealsanfelice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.srusso.usrealsanfelice.adapter.GiocatoreAdapter;
import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.config.Config;
import app.srusso.usrealsanfelice.to.Giocatore;
import cz.msebera.android.httpclient.Header;

public class ConvocatiActivity extends ActivityBase {

    TextView avversari;
    TextView nota;
    ListView listaViewConvocati;
    public GiocatoreAdapter adapterG;;
    public List<Giocatore> listaGiocatori = new ArrayList<Giocatore>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_convocati);


        avversari = (TextView) findViewById(R.id.avversari);
        nota = (TextView) findViewById(R.id.nota);
        listaViewConvocati = (ListView) findViewById(R.id.listaConvocati);
        caricaConvocati();


        listaViewConvocati.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String json_giocatore  = gson.toJson(listaGiocatori.get(position));

                Intent page_dettaglio= new Intent(context, DettaglioGiocatoreActivity.class);
                page_dettaglio.putExtra("GIOCATORE",json_giocatore);
                startActivity(page_dettaglio);

            }
        });

    }



    public  void load(){
        adapterG  = new GiocatoreAdapter(context,this,listaGiocatori);
        listaViewConvocati.setAdapter(adapterG);
    }


    public void caricaConvocati() {

        clientHttpClient.get(Config.pathConvocati, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject risp) {
                // called when response HTTP status is "200 OK"

                JSONArray response = null;
                try {
                    response = risp.getJSONArray("convocati");

                    avversari.setText(risp.getString("avv"));
                    nota.setText(risp.getString("note"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (int i = response.length()-1; i >= 0; i--) {
                    try {
                        JSONObject giocatoreJson = response.getJSONObject(i);

                        String nomeGiocatore = giocatoreJson.getString("nome");
                        String cognomeGiocatore = giocatoreJson.getString("cognome");
                        String avatarPath = giocatoreJson.getString("path_img");
                        int numeroMaglia = giocatoreJson.getInt("num_maglia");
                        String ruolo   = giocatoreJson.getString("ruolo");
                        String nascita   = giocatoreJson.getString("dat_nas");
                        String comune   = giocatoreJson.getString("com_nasc");
                        int peso        = giocatoreJson.getInt("peso");
                        double altezza  = giocatoreJson.getDouble("altezza");


                        Giocatore giocatore = new Giocatore();
                        giocatore.setNome(nomeGiocatore);
                        giocatore.setCognome(cognomeGiocatore);
                        giocatore.setUrlAvatar(avatarPath);
                        giocatore.setNumeroMaglia(numeroMaglia);
                        giocatore.setRuolo(ruolo);
                        giocatore.setAltezza(altezza);
                        giocatore.setComune(comune);
                        giocatore.setPeso(peso);
                        giocatore.setNascita(nascita);





                        listaGiocatori.add(giocatore);





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

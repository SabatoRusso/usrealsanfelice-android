package app.srusso.usrealsanfelice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

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

public class RosaActivity extends ActivityBase{


    ImageView imgRosa;
    ListView listViewRosa;
    public GiocatoreAdapter adapterG;
    public List<Giocatore> listaGiocatori = new ArrayList<Giocatore>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_rosa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgRosa = (ImageView) findViewById(R.id.rosaImg);
        listViewRosa = (ListView) findViewById(R.id.listaRosa);




        Picasso.with(context).load(Config.imgRosa).into(imgRosa);


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewRosa.setNestedScrollingEnabled(true);
        caricaRosa();


        listViewRosa.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
        adapterG  = new GiocatoreAdapter(context,this);
        listViewRosa.setAdapter(adapterG);
    }



    public void caricaRosa() {

        clientHttpClient.get(Config.pathRosa, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // called when response HTTP status is "200 OK"




                for (int i = response.length()-1; i >= 0; i--) {
                    try {
                        JSONObject giocatoreJson = response.getJSONObject(i);

                        String nomeGiocatore = giocatoreJson.getString("nome");
                        String cognomeGiocatore = giocatoreJson.getString("cognome");
                        String avatarPath = giocatoreJson.getString("path_img");
                        int numeroMaglia = giocatoreJson.getInt("num_maglia");
                        String ruolo   = giocatoreJson.getString("ruolo");


                                Giocatore giocatore = new Giocatore();
                                giocatore.setNome(nomeGiocatore);
                                giocatore.setCognome(cognomeGiocatore);
                                giocatore.setUrlAvatar(avatarPath);
                                giocatore.setNumeroMaglia(numeroMaglia);
                                giocatore.setRuolo(ruolo);





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

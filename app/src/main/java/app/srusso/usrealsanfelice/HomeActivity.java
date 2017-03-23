package app.srusso.usrealsanfelice;

import android.content.Intent;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.config.Config;

import app.srusso.usrealsanfelice.notifacation.RegistrationIntentService;
import app.srusso.usrealsanfelice.to.Notizia;
import app.srusso.usrealsanfelice.to.Partita;
import cz.msebera.android.httpclient.Header;

public class HomeActivity extends ActivityBase  {


    TextView giornata_data;
    TextView squadraFuoriCasa;
    TextView squadraCasa;
    TextView risultato;
    LinearLayout layout_ultima_partita;
    RelativeLayout layout_news;
    ImageView copertina;
    TextView titolocopertina;
    private Partita partita;
    private Notizia news = new Notizia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_home);

        getSupportActionBar().hide();

        giornata_data = (TextView) findViewById(R.id.data_giornata);
        squadraFuoriCasa = (TextView) findViewById(R.id.squadraFuoriCasa);
        squadraCasa = (TextView) findViewById(R.id.squadraCasa);
        risultato = (TextView) findViewById(R.id.risultato);
        titolocopertina = (TextView) findViewById(R.id.titolocopertina);
        copertina = (ImageView) findViewById(R.id.copertina);
        layout_ultima_partita = (LinearLayout) findViewById(R.id.layout_utima_partita);
        layout_news = (RelativeLayout) findViewById(R.id.contenitore_news);
        caricaUltimaParita();
        caricaUltimaNotizia();
        //if(checkPlayServices()) {
         //   Intent intent = new Intent(this, RegistrationIntentService.class);
          //  startService(intent);
        //}
        FirebaseMessaging.getInstance().subscribeToTopic("settore_prima_squadra_news");

    }




    public void caricaUltimaParita() {

        clientHttpClient.get(Config.pathUltimaPartita, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject risp) {
                // called when response HTTP status is "200 OK"

                try {

                    partita  = new Partita();
                    partita.setOspiti(risp.getString("ospiti"));
                    partita.setLocali(risp.getString("locali"));
                    partita.setGol_loc(risp.getString("gol_loc"));
                    partita.setGol_osp(risp.getString("gol_osp"));
                    partita.setData(risp.getString("data"));
                    partita.setGiornata(risp.getString("giornata"));

                    squadraFuoriCasa.setText(partita.getOspiti());
                    squadraCasa.setText(partita.getLocali());
                    risultato.setText(partita.getGol_loc()+ " - "+ partita.getGol_osp() );
                    giornata_data.setText(partita.getGiornata() +"  Giornata " + partita.getData()  );

                   JSONArray imgs = risp.getJSONArray("imgs");
                    for (int i = imgs.length()-1; i >= 0; i--) {

                        partita.getImgPartita().add(imgs.getString(i));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                layout_ultima_partita.setVisibility(View.VISIBLE);

                YoYo.with(Techniques.FlipInX)
                        .duration(1000)
                        .playOn(findViewById(R.id.layout_utima_partita));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                //  progressDialog.hide();
                  showToast(getResources().getString(R.string.erroreConnessione));
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                //   progressDialog.hide();
                //   showToast(getResources().getString(R.string.erroreConnessione));
            }
        });
    }


    public void caricaUltimaNotizia() {

        clientHttpClient.get(Config.pathUltimaNotizia, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray risp) {
                // called when response HTTP status is "200 OK"

                try {


                    JSONObject newsJson =  risp.getJSONObject(0);
                    news.setTitolo(newsJson.getString("titolo"));
                    news.setTesto(newsJson.getString("testo"));
                    news.setData(newsJson.getString("data"));
                    news.setUrlImmagine(newsJson.getString("img"));



                    Picasso.with(context).load(news.getUrlImmagine()).into(copertina);
                    titolocopertina.setText(Html.fromHtml(news.getTitolo()));

                    layout_news.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.ZoomIn)
                            .duration(1000)
                            .playOn(findViewById(R.id.contenitore_news));

                } catch (JSONException e) {
                    e.printStackTrace();
                }




               /* YoYo.with(Techniques.FlipInX)
                        .duration(1000)
                        .playOn(findViewById(R.id.layout_utima_partita));*/

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                //  progressDialog.hide();
                  showToast(getResources().getString(R.string.erroreConnessione));
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                //   progressDialog.hide();
                //   showToast(getResources().getString(R.string.erroreConnessione));
            }
        });
    }





    public void goRosaActivity(View v){

        Intent i = new Intent(context, RosaActivity.class);

        startActivity(i);

    }


    public void goConvocatiActivity(View v){

        Intent i = new Intent(context, ConvocatiActivity.class);

        startActivity(i);

    }


    public void goNewsActivity(View v){

        Intent i = new Intent(context, NewsDettaglioActivity.class);
        String json_news  = gson.toJson(news);

        i.putExtra("NEWS",json_news);

        startActivity(i);

    }


    public void goDatiActivity(View v){

        Intent i = new Intent(context, DatiActivity.class);

        startActivity(i);

    }
    public void goGalleryActivity(View v){

        Intent i = new Intent(context, GalleryActivity.class);
        String json_partita  = gson.toJson(partita);

        i.putExtra("PARTITA",json_partita);
        startActivity(i);

    }



}

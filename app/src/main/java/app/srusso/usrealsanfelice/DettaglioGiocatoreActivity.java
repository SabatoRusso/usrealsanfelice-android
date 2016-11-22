package app.srusso.usrealsanfelice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.to.Giocatore;

public class DettaglioGiocatoreActivity extends ActivityBase {

    ImageView avatarGiocatore;
    TextView  nomeGiocatore;
    TextView  numeroMaglia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_dettaglio_giocatore);
        Bundle bundle = getIntent().getExtras();
        avatarGiocatore = (ImageView) findViewById(R.id.avatarGiocatore);
        nomeGiocatore   = (TextView)  findViewById(R.id.nomeGiocatore);
        numeroMaglia   = (TextView)  findViewById(R.id.numeroMaglia);

        if (bundle != null) {

            String giocatore_json = bundle.getString("GIOCATORE");

            Giocatore giocatore   =  gson.fromJson(giocatore_json, Giocatore.class);

            Picasso.with(context).load(giocatore.getUrlAvatar()).into(avatarGiocatore);
            nomeGiocatore.setText(giocatore.getNome() + " "+giocatore.getCognome());
            numeroMaglia.setText(giocatore.getNumeroMaglia()+"");


        }

    }
}

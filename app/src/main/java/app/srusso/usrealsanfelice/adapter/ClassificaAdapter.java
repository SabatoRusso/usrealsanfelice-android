package app.srusso.usrealsanfelice.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.srusso.usrealsanfelice.R;
import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.to.Giocatore;
import app.srusso.usrealsanfelice.to.Squadra;

/**
 * Created by Sabato Russo on 18/11/16.
 */

public class ClassificaAdapter extends BaseAdapter {

    private Context mContext;
    private int width;
    private ActivityBase actRs;
    private List<Squadra> listaSquadra;


    public ClassificaAdapter(Context c, List<Squadra>  lista) {
        mContext = c;

        listaSquadra = lista;

    }

    public int getCount() {

        return this.listaSquadra.size();

    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = ((ActivityBase) mContext).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_squ_classifica, parent, false);

        ImageView scudettoSquadra = (ImageView) convertView.findViewById(R.id.scudettoSquadra);
        TextView nomeSquadra= (TextView) convertView.findViewById(R.id.nomeSquadra);
        TextView posizione= (TextView) convertView.findViewById(R.id.posizioneClassifica);
        TextView punti = (TextView) convertView.findViewById(R.id.punti);
        LinearLayout row = (LinearLayout) convertView.findViewById(R.id.row_squadra);


       // String  url_immagini = actPr.prodotti.get(position).getImmagini().get(0);
        Squadra squadra = this.listaSquadra.get(position);

        nomeSquadra.setText(squadra.getNome());
        punti.setText(squadra.getPunti() + "" );
        posizione.setText(squadra.getPosizione() + "");

         if (squadra.getNome().equalsIgnoreCase("real san felice")){
             Picasso.with(mContext).load(R.drawable.scudetto).into(scudettoSquadra);
             row.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));

            row.invalidate();
        }


        return convertView;
    }

    // references to our images

}
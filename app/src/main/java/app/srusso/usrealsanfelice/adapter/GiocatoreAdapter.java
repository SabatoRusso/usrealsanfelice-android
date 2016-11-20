package app.srusso.usrealsanfelice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.srusso.usrealsanfelice.R;
import app.srusso.usrealsanfelice.RosaActivity;
import app.srusso.usrealsanfelice.to.Giocatore;

/**
 * Created by Sabato Russo on 18/11/16.
 */

public class GiocatoreAdapter extends BaseAdapter {

    private Context mContext;
    private int width;
    private RosaActivity actRs;

    public GiocatoreAdapter(Context c, RosaActivity act) {
        mContext = c;
        actRs = act;
    }

    public int getCount() {

        return actRs.listaGiocatori.size();

    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = ((RosaActivity) mContext).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_giocatore, parent, false);

        ImageView avatarGiocatore = (ImageView) convertView.findViewById(R.id.avatarGiocatore);
        TextView nomeGiocatore = (TextView) convertView.findViewById(R.id.nomeGiocatore);
        TextView numeroMaglia = (TextView) convertView.findViewById(R.id.numeroMaglia);
        TextView ruoloGiocatore = (TextView) convertView.findViewById(R.id.ruoloGiocatore);


       // String  url_immagini = actPr.prodotti.get(position).getImmagini().get(0);
        Giocatore giocatore = actRs.listaGiocatori.get(position);
        Picasso.with(mContext).load(giocatore.getUrlAvatar()).into(avatarGiocatore);
        nomeGiocatore.setText(giocatore.getCognome() + " " + giocatore.getNome());
        numeroMaglia.setText(giocatore.getNumeroMaglia() +"" );
        ruoloGiocatore.setText(giocatore.getRuolo());

        return convertView;
    }

    // references to our images

}
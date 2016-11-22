package app.srusso.usrealsanfelice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.to.Giocatore;

public class DettaglioGiocatoreActivity extends ActivityBase {

    ImageView avatarGiocatore;
    TextView  nomeGiocatore;
    TextView  numeroMaglia;
    TextView ruoloGiocatore;
    TextView nascitaGiocatore;
    TextView comuneGiocatore;
    TextView pesoGiocatore;
    TextView altezzaGiocatore;
    LinearLayout figurina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_dettaglio_giocatore);
        Bundle bundle = getIntent().getExtras();
        avatarGiocatore = (ImageView) findViewById(R.id.avatarGiocatore);
        nomeGiocatore   = (TextView)  findViewById(R.id.nomeGiocatore);
        numeroMaglia   = (TextView)  findViewById(R.id.numeroMaglia);
        ruoloGiocatore   = (TextView)  findViewById(R.id.ruoloGiocatore);
        comuneGiocatore   = (TextView)  findViewById(R.id.comuneGiocatore);
        pesoGiocatore   = (TextView)  findViewById(R.id.pesoGiocatore);
        altezzaGiocatore   = (TextView)  findViewById(R.id.altezzaGiocatore);
        nascitaGiocatore   = (TextView)  findViewById(R.id.nascitaGiocatore);
        figurina =          (LinearLayout) findViewById(R.id.figurina);

        if (bundle != null) {

            String giocatore_json = bundle.getString("GIOCATORE");

            Giocatore giocatore   =  gson.fromJson(giocatore_json, Giocatore.class);

            Picasso.with(context).load(giocatore.getUrlAvatar()).into(avatarGiocatore);
            nomeGiocatore.setText(giocatore.getNome() + " "+giocatore.getCognome());
            numeroMaglia.setText(giocatore.getNumeroMaglia()+"");
            nascitaGiocatore.setText(giocatore.getNascita()+"");
            ruoloGiocatore.setText(giocatore.getRuolo()+"");
            comuneGiocatore.setText(giocatore.getComune()+"");
            pesoGiocatore.setText("Peso " + giocatore.getPeso());
            altezzaGiocatore.setText("Altezza " +giocatore.getAltezza()+"");




        }


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) figurina.getLayoutParams();
        params.width= width-180;
        figurina.setLayoutParams(params);
        //shareResultToFacebook();

    }




    private void shareResultToFacebook(){
        try {
            Bitmap bitmap = getBitmapFromView(figurina);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(this, bitmap));
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Prova"));

        }catch (Exception e){
            e.getMessage();
        }

    }

    private Bitmap getBitmapFromView(LinearLayout view) {
        try {

            view.setDrawingCacheEnabled(true);

            view.measure(View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

            view.buildDrawingCache(true);
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getDrawingCache());

            //Define a bitmap with the same size as the view
            view.setDrawingCacheEnabled(false);

            return returnedBitmap;
        }catch (Exception e){
            //Global.logError("getBitmapFromView", e);
        }
        return null;
    }





    public Uri getImageUri(Context inContext, Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                    inImage, "", "");
            return Uri.parse(path);
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

}

package app.srusso.usrealsanfelice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    LinearLayout screen;
    Giocatore giocatore;
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
        screen =          (LinearLayout) findViewById(R.id.screen);

        if (bundle != null) {

            String giocatore_json = bundle.getString("GIOCATORE");

             giocatore   =  gson.fromJson(giocatore_json, Giocatore.class);

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_dettaglio_giocatore, menu);

        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        // Return true to display menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.share) {
            permissionShare();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private  void permissionShare(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        10);


            } else {



                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        10);


            }
        }
        else{
            shareResultToFacebook();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    shareResultToFacebook();

                } else {

                    showToast(getResources().getString(R.string.erroreShare));
                }
                return;
            }


        }
    }




    private void shareResultToFacebook(){
        try {
            Bitmap bitmap = getBitmapFromView(figurina);


            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(this, bitmap));
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent,giocatore.getNome()+giocatore.getCognome() ));

        }catch (Exception e){
            e.getMessage();
        }

    }

    private Bitmap getBitmapFromView(LinearLayout view) {
        try {

            view.setDrawingCacheEnabled(true);

            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
            //view.layout(0, 0, view.getMeasuredWidth(),view.getMeasuredHeight());

            view.buildDrawingCache(true);
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getDrawingCache());

            //Define a bitmap with the same size as the view
            view.setDrawingCacheEnabled(false);

            return returnedBitmap;
        }catch (Exception e){

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

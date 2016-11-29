package app.srusso.usrealsanfelice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;
import com.veinhorn.scrollgalleryview.loader.DefaultVideoLoader;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.base.PicassoImageLoader;
import app.srusso.usrealsanfelice.to.Partita;

public class GalleryActivity extends ActivityBase {

    private ScrollGalleryView scrollGalleryView;
     private Partita partita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_gallery);
        Bundle bundle = getIntent().getExtras();


               // .addMedia(MediaInfo.mediaLoader(new DefaultVideoLoader(movieUrl, R.drawable.default_video)));


        if (bundle != null) {

            String partita_json = bundle.getString("PARTITA");
            partita   =  gson.fromJson(partita_json, Partita.class);

            setTitle( partita.getGiornata() +" Giornata " );
            List<MediaInfo> infos = new ArrayList<>(partita.getImgPartita().size());
            for (String url : partita.getImgPartita()) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));

            scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
            scrollGalleryView
                    .setThumbnailSize(200)
                    .setZoom(true)
                    .setFragmentManager(getSupportFragmentManager())
                    .addMedia(infos);

        }
    }

    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }
}

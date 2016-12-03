package app.srusso.usrealsanfelice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.srusso.usrealsanfelice.base.ActivityBase;
import app.srusso.usrealsanfelice.to.Notizia;

public class NewsDettaglioActivity extends ActivityBase {

    TextView testoNews;
    ImageView img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_news_dettaglio);
        Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testoNews = (TextView) findViewById(R.id.testo_news);
        img_news = (ImageView) findViewById(R.id.img_news);


        if (bundle != null) {

            String news_json = bundle.getString("NEWS");
            Notizia news =  gson.fromJson(news_json, Notizia.class);


            testoNews.setText(Html.fromHtml(news.getTesto()));
            Picasso.with(context).load(news.getUrlImmagine()).into(img_news);
            setTitle(news.getTitolo());
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

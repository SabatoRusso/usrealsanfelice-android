package app.srusso.usrealsanfelice.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import app.srusso.usrealsanfelice.R;

/**
 * Created by Sabato Russo on 17/11/16.
 */

public class ActivityBase extends AppCompatActivity {
    public Context context;
    public AsyncHttpClient clientHttpClient;
    public  final Gson gson = new Gson();


    protected void onCreate(Bundle savedInstanceState , int layout) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        context = this;
        clientHttpClient = new AsyncHttpClient();
        clientHttpClient.setTimeout(60000);

    }



    public void showToast(String msg) {
        Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        t.show();
    }

}

package app.srusso.usrealsanfelice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.srusso.usrealsanfelice.base.ActivityBase;

public class HomeActivity extends ActivityBase {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_home);

        getSupportActionBar().hide();

    }




    public void goRosaActivity(View v){

        Intent i = new Intent(context, RosaActivity.class);

        startActivity(i);

    }


    public void goConvocatiActivity(View v){

        Intent i = new Intent(context, ConvocatiActivity.class);

        startActivity(i);

    }
}

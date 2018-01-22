package com.example.gnud.dulich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.gnud.dulich.database.DatabaseTrip;

import java.sql.SQLException;

public class splash extends AppCompatActivity {
    private DatabaseTrip database = new DatabaseTrip(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            database.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(database.getIdMax() == 0){
                    Intent intent = new Intent(splash.this,NewTrip.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(splash.this,DetailTrip.class);
                    intent.putExtra("check", "1");
                    startActivity(intent);
                    finish();
                }
            }
        },2000);

    }
}

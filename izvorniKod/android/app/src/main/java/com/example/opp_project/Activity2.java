package com.example.opp_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Activity2 extends AppCompatActivity {

    //Toolbar toolbar = findViewById(R.id.toolbar);
    private Button buttonRacuni;
    private Button buttonKartice;
    private Button buttonKrediti;
    private Button buttonProfil;

    private String jsessionid;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        jsessionid = getIntent().getStringExtra("SESSION_ID");


        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        //getSupportActionBar().setTitle("Bugbusters banka");
        //mtoolbar.setSubtitle("Dobrodošli!");

        buttonRacuni = (Button) findViewById(R.id.button);
        buttonRacuni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openRacuni();
            }
        });

        buttonKartice = (Button) findViewById(R.id.button4);
        buttonKartice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openKartice();
            }
        });

        buttonKrediti = (Button) findViewById(R.id.button5);
        buttonKrediti.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openKrediti();
            }
        });

        buttonProfil = (Button) findViewById(R.id.buttonProfil);
        buttonProfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openProfil();
            }
        });
    }


    public void openRacuni(){
        Intent intent = new Intent(this, Racuni.class);
        startActivity(intent);
    }

    public void openKartice(){
        Intent intent = new Intent(this, Kartice.class);
        startActivity(intent);
    }

    public void openKrediti(){
        Intent intent = new Intent(this, Krediti.class);
        startActivity(intent);
    }

    public void openProfil(){
        Intent intent = new Intent(this, Profil.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

   // public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu,menu);
        //return true;

}

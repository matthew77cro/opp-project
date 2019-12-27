package com.example.opp_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Racuni extends AppCompatActivity {
    private Button buttonTekuci;
    private Button buttonStedni;
    private Button buttonZiro;

    private String jsessionid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.racuni);
        //setSupportActionBar(toolbar);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setTitle("Racuni");
 //       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jsessionid = getIntent().getStringExtra("SESSION_ID");


        buttonTekuci = (Button) findViewById(R.id.buttonTekuci);
        buttonTekuci.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openTekuci();
            }
        });

        buttonStedni = (Button) findViewById(R.id.buttonStedni);
        buttonStedni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStedni();
            }
        });

        buttonZiro = (Button) findViewById(R.id.buttonZiro);
        buttonZiro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openZiro();
            }
        });
    }


    public void openTekuci(){
        Intent intent = new Intent(this, TekuciRacuni.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

    public void openZiro(){
        Intent intent = new Intent(this, ZiroRacuni.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

    public void openStedni(){
        Intent intent = new Intent(this, StedniRacuni.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }
}

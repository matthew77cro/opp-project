package com.example.opp_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Kartice extends AppCompatActivity {
    private Button buttonDebitne;
    private Button buttonKreditne;

    private String jsessionid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kartice);
        //setSupportActionBar(toolbar);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
      //  getSupportActionBar().setTitle("Kartice");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        jsessionid = getIntent().getStringExtra("SESSION_ID");


        buttonDebitne = (Button) findViewById(R.id.buttonDebitne);
        buttonDebitne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openDebitne();
            }
        });

        buttonKreditne = (Button) findViewById(R.id.buttonKreditne);
        buttonKreditne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openKreditne();
            }
        });
    }

    public void openDebitne(){
        Intent intent = new Intent(this, DebitneKartice.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

    public void openKreditne(){
        Intent intent = new Intent(this, KreditneKartice.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }
}

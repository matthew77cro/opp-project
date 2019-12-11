package com.example.opp_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TekuciRacuni extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tekuci_racuni);

        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setTitle("Tekući računi");
  //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

package com.example.opp_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ZiroRacuni extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ziro_racuni);

        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setTitle("Žiro računi");
  //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

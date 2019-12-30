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
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity2 extends AppCompatActivity {

    private Button buttonRacuni;
    private Button buttonKartice;
    private Button buttonKrediti;
    private Button buttonProfil;
    private Button buttonOdjava;

    private String jsessionid;
    private String username;
    private Retrofit retrofit;
    private BBService service;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();

        jsessionid = getIntent().getStringExtra("SESSION_ID");
        username = getIntent().getStringExtra("USERNAME");

        buttonRacuni = (Button) findViewById(R.id.button);
        buttonRacuni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRacuni();
            }
        });

        buttonKartice = (Button) findViewById(R.id.button4);
        buttonKartice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKartice();
            }
        });

        buttonKrediti = (Button) findViewById(R.id.button5);
        buttonKrediti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKrediti();
            }
        });

        buttonProfil = (Button) findViewById(R.id.buttonProfil);
        buttonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfil();
            }
        });

        buttonOdjava = (Button) findViewById(R.id.buttonOdjava);
        buttonOdjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service = retrofit.create(BBService.class);
                Call<Void> call = service.logout(jsessionid);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        Toast.makeText(getApplicationContext(), Integer.toString(response.code()) + jsessionid, Toast.LENGTH_LONG).show();

                        if (response.isSuccessful()) {
                            openMain();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Nemogućnost odjave", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public void openRacuni(){
        Intent intent = new Intent(this, Racuni.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

    public void openKartice(){
        Intent intent = new Intent(this, Kartice.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

    public void openKrediti(){
        Intent intent = new Intent(this, Krediti.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

    public void openProfil(){
        Intent intent = new Intent(this, Profil.class);
        intent.putExtra("SESSION_ID", jsessionid);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }

   // public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu,menu);
        //return true;

}

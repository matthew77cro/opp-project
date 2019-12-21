package com.example.opp_project;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText ime;
    private EditText lozinka;
    private Button login;

    private Retrofit retrofit;
    private BBService service;

    private String jsessionid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();

        setContentView(R.layout.activity_main);


        ime = (EditText) findViewById(R.id.poljeIme);
        lozinka = (EditText) findViewById(R.id.poljeLozinka);
        login = (Button) findViewById(R.id.prijavaButton);


        Toolbar mtoolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Bugbusters banka");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service = retrofit.create(BBService.class);
                Call<Void> call = service.login(ime.getText().toString(), lozinka.getText().toString());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        jsessionid = response.headers().values("Set-Cookie").get(0).split(";\\s+")[0];

                        Toast.makeText(getApplicationContext(), Integer.toString(response.code()) + jsessionid, Toast.LENGTH_LONG).show();

                        if(response.isSuccessful()) {
                            openHomeScreen();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Pogrešno korisničko ime ili lozinka!", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), Integer.toString(getStatusCode()) + jsessionid, Toast.LENGTH_SHORT).show();

                    }

                });

            }
        });

    }

    public void openHomeScreen(){
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("SESSION_ID", jsessionid);
        startActivity(intent);
    }


    }



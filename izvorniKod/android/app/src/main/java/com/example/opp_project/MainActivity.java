package com.example.opp_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.async.http.NameValuePair;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

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
        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92:8080/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();

        setContentView(R.layout.activity_main);

        ime = (EditText) findViewById(R.id.poljeIme);
        lozinka = (EditText) findViewById(R.id.poljeLozinka);
        login = (Button) findViewById(R.id.prijavaButton);

        //Toolbar mtoolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(mtoolbar);
        //getSupportActionBar().setTitle("OPP banka");
        //mtoolbar.setSubtitle("Dobrodošli!");


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
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });
    }




    }



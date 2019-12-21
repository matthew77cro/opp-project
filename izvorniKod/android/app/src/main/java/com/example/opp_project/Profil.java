package com.example.opp_project;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Profil extends AppCompatActivity implements Serializable {

    private TextView firstNameView;
    private TextView lastNameView;
    private TextView addressView;
    private TextView cityPostCodeView;
    private TextView cityNameView;
    private TextView countNameView;
    private TextView oibView;
    private TextView birthdayView;
    private TextView emailView;

    private Retrofit retrofit;
    private BBService service;


    private String jsessionid;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        Toolbar mtoolbar = findViewById(R.id.toolbar);

     //   setSupportActionBar(mtoolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setTitle("Profil");
    //    mtoolbar.setSubtitle("Dobrodošli!");



        firstNameView = (TextView) findViewById(R.id.textViewValueIme);
        lastNameView = (TextView) findViewById(R.id.textViewValuePrezime);
        addressView = (TextView) findViewById(R.id.textViewValueAdresa);
        oibView = (TextView) findViewById(R.id.textViewValueOib);
        birthdayView = (TextView) findViewById(R.id.textViewValueDatum);
        emailView = (TextView) findViewById(R.id.textViewValueMail);
        cityPostCodeView = (TextView) findViewById((R.id.textViewValuePbr));
        cityNameView = (TextView) findViewById(R.id.textViewValueGrad);
        countNameView = (TextView) findViewById(R.id.textViewValueDrzava);



        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(BBService.class);



        jsessionid = getIntent().getStringExtra("SESSION_ID");



        try {
            Call<ProfilPodaci> call = service.profil(jsessionid);

            call.enqueue(new Callback<ProfilPodaci>() {
                             @Override
                             public void onResponse(Call<ProfilPodaci> call, Response<ProfilPodaci> response) {


                                 if(response.isSuccessful()) {
                                     firstNameView.setText(response.body().getFirstName());
                                     lastNameView.setText(response.body().getLastName());
                                     addressView.setText(response.body().getAddress());
                                     oibView.setText(response.body().getOib());
                                     birthdayView.setText(response.body().getBirthday());
                                     emailView.setText(response.body().getEmail());
                                     cityPostCodeView.setText(response.body().getCityPostCode());
                                     cityNameView.setText(response.body().getCityName());
                                     countNameView.setText(response.body().getCountyName());

                                 }else{
                                     Toast.makeText(getApplicationContext(), "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                                 }

                             }

                             @Override
                             public void onFailure(Call<ProfilPodaci> call, Throwable t) {
                                 Toast.makeText(getApplicationContext(), "Pogreška", Toast.LENGTH_SHORT).show();
                             }
                         }
            );


        }catch(Exception e){

        }
    }
}

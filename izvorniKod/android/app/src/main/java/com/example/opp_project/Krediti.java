package com.example.opp_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Krediti extends AppCompatActivity {

    private Retrofit retrofit;
    private BBService service;

    private String jsessionid;

    LinearLayout linearLayout;


    private TextView brKredita;
    private TextView oib;
    private TextView iznos;
    private TextView sifraVrste;
    private TextView nazivVrste;
    private TextView kamStopa;
    private TextView datUgovaranja;
    private TextView periodOtplate;
    private TextView datRate;
    private TextView dugovanje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.krediti);


        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(BBService.class);

        jsessionid = getIntent().getStringExtra("SESSION_ID");



        Call<List<KreditPodaci>> call = service.krediti(jsessionid);

        call.enqueue(new Callback<List<KreditPodaci>>() {
                         @Override
                         public void onResponse(Call<List<KreditPodaci>> call, Response<List<KreditPodaci>> response) {


                             if(response.isSuccessful()) {

                                 List<KreditPodaci> json = response.body();


                                 LayoutInflater inflater = getLayoutInflater();


                                 for(KreditPodaci kredit : json){

                                     linearLayout = findViewById(R.id.LinearLayoutKrediti);

                                     View v = inflater.inflate(R.layout.krediti_view, null);



                                     brKredita = (TextView) v.findViewById(R.id.textViewValueBrojKredita);
                                     oib = (TextView) v.findViewById(R.id.textViewValueOibKredit);
                                     iznos = (TextView) v.findViewById(R.id.textViewValueIznosKredit);
                                     sifraVrste = (TextView) v.findViewById(R.id.textViewValueSifVrsteKredita);
                                     nazivVrste = (TextView) v.findViewById(R.id.textViewValueNazivVrsteKredita);
                                     kamStopa = (TextView) v.findViewById(R.id.textViewValueKamatnaStopaKredit);
                                     datUgovaranja = (TextView) v.findViewById(R.id.textViewValueDatumKredit);
                                     periodOtplate = (TextView) v.findViewById(R.id.textViewValuePeriodOtplateKredit);
                                     datRate = (TextView) v.findViewById(R.id.textViewValueDatRateKredit);
                                     dugovanje = (TextView) v.findViewById(R.id.textViewValueDugovanjeKredit);



                                     brKredita.setText(String.valueOf(kredit.getBrKredit()));
                                     oib.setText(kredit.getOib());
                                     iznos.setText(kredit.getIznos());
                                     sifraVrste.setText(String.valueOf(kredit.getVrstaKredita().getSifVrsteKredita()));
                                     nazivVrste.setText(kredit.getVrstaKredita().getNazVrsteKredita());
                                     kamStopa.setText(kredit.getVrstaKredita().getKamStopa());
                                     datUgovaranja.setText(kredit.getDatUgovaranja());
                                     periodOtplate.setText(String.valueOf(kredit.getPeriodOtplate()));
                                     datRate.setText(String.valueOf(kredit.getDatRate()));
                                     dugovanje.setText(kredit.getPreostaloDugovanje());


                                     linearLayout.addView(v);

                                 }


                             }else{
                                 Toast.makeText(getApplicationContext(), "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                             }

                         }

                         @Override
                         public void onFailure(Call<List<KreditPodaci>> call, Throwable t) {
                             Toast.makeText(getApplicationContext(), "Pogreška", Toast.LENGTH_SHORT).show();
                         }
                     }
        );


    }
}

package com.example.opp_project;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZiroRacuni extends AppCompatActivity {

    private Retrofit retrofit;
    private BBService service;

    private String jsessionid;

    private TextView brRacunView;
    private TextView iznosView;
    private TextView oibView;
    private TextView datOtvaranjaView;
    private TextView prekoracenjeView;
    private TextView kamStopaView;
    private TextView sifVrsteRacunaView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ziro_racuni);

        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setTitle("Žiro računi");
  //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        brRacunView = (TextView) findViewById(R.id.textViewValueZiro1);
        iznosView = (TextView) findViewById(R.id.textViewValueStedniIznos1);
        oibView = (TextView) findViewById(R.id.textViewValueOibZiro);
        datOtvaranjaView = (TextView) findViewById(R.id.textViewValueDatumZiro);
        prekoracenjeView = (TextView) findViewById(R.id.textViewValuePrekoracenjeZiro);
        kamStopaView = (TextView) findViewById(R.id.textViewValueKamatnaStopaZiro);
        sifVrsteRacunaView = (TextView) findViewById(R.id.textViewValueSifraVrsteRacunaZiro);



        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(BBService.class);


        jsessionid = getIntent().getStringExtra("SESSION_ID");



        Call<List<RacuniPodaci>> call = service.racuni(jsessionid);
        call.enqueue(new Callback<List<RacuniPodaci>>() {
                         @Override
                         public void onResponse(Call<List<RacuniPodaci>> call, Response<List<RacuniPodaci>> response) {


                             if(response.isSuccessful()) {

                                 List<RacuniPodaci> json = response.body();

                                 for(RacuniPodaci racun : json){
                                     if(racun.getVrstaRacuna().getNazVrsteRacuna().equals("Žiro račun")){

                                         brRacunView.setText(racun.getBrRacun());
                                         iznosView.setText(racun.getStanje());
                                         oibView.setText(racun.getOib());
                                         datOtvaranjaView.setText(racun.getDatOtvaranja());
                                         prekoracenjeView.setText(racun.getPrekoracenje());
                                         kamStopaView.setText(racun.getKamStopa());
                                         sifVrsteRacunaView.setText(racun.getVrstaRacuna().getSifVrsteRacuna());
                                     }
                                 }


                             }else{
                                 Toast.makeText(getApplicationContext(), "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                             }

                         }

                         @Override
                         public void onFailure(Call<List<RacuniPodaci>> call, Throwable t) {
                             Toast.makeText(getApplicationContext(), "Pogreška", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
    }
}

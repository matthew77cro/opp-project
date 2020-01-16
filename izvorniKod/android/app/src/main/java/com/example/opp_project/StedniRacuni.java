package com.example.opp_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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

public class StedniRacuni extends AppCompatActivity {

    private Retrofit retrofit;
    private BBService service;

    private String jsessionid;

    private LinearLayout linearLayout;

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
        setContentView(R.layout.stedni_racuni);
        //setSupportActionBar(toolbar);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setTitle("Štedni računi");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(BBService.class);


        jsessionid = getIntent().getStringExtra("SESSION_ID");



        Call<List<RacuniPodaci>> call = service.racuni(jsessionid);
        call.enqueue(new Callback<List<RacuniPodaci>>() {
                         @Override
                         public void onResponse(Call<List<RacuniPodaci>> call, Response<List<RacuniPodaci>> response) {


                             if(response.isSuccessful()) {

                                 List<RacuniPodaci> json = response.body();

                                 LayoutInflater inflater = getLayoutInflater();


                                 linearLayout = findViewById(R.id.LinearLayoutStedni);


                                 for(RacuniPodaci racun : json){
                                     if(racun.getVrstaRacuna().getNazVrsteRacuna().equals("Štedni račun")){

                                         View v = inflater.inflate(R.layout.stedni_racuni_view, null);


                                         brRacunView = (TextView) v.findViewById(R.id.textViewValueStedni1);
                                         iznosView = (TextView) v.findViewById(R.id.textViewValueStedniIznos1);
                                         oibView = (TextView) v.findViewById(R.id.textViewValueOibStedni);
                                         datOtvaranjaView = (TextView) v.findViewById(R.id.textViewValueDatumStedni);
                                         prekoracenjeView = (TextView) v.findViewById(R.id.textViewValuePrekoracenjeStedni);
                                         kamStopaView = (TextView) v.findViewById(R.id.textViewValueKamatnaStopaStedni);
                                         sifVrsteRacunaView = (TextView) v.findViewById(R.id.textViewValueSifraVrsteRacunaStedni);



                                         brRacunView.setText(racun.getBrRacun());
                                         iznosView.setText(racun.getStanje());
                                         oibView.setText(racun.getOib());
                                         datOtvaranjaView.setText(racun.getDatOtvaranja());
                                         prekoracenjeView.setText(racun.getPrekoracenje());
                                         kamStopaView.setText(racun.getKamStopa());
                                         sifVrsteRacunaView.setText(racun.getVrstaRacuna().getSifVrsteRacuna());


                                         linearLayout.addView(v);
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

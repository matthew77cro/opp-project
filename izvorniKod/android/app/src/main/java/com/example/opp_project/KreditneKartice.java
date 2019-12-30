package com.example.opp_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KreditneKartice  extends AppCompatActivity {

    private Retrofit retrofit;
    private BBService service;

    private String jsessionid;

    private TextView vrstaKartice;

    private TextView brKartica;
    private TextView oib;
    private TextView stanje;
    private TextView valjanost;
    private TextView limitKartice;
    private TextView kamatnaStopa;
    private TextView datRate;

    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kreditne_kartice);
      Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        //     getSupportActionBar().setTitle("Kreditne kartice");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(BBService.class);


        jsessionid = getIntent().getStringExtra("SESSION_ID");


        Call<List<KarticaPodaci>> call = service.kartice(jsessionid);
        call.enqueue(new Callback<List<KarticaPodaci>>() {
                         @Override
                         public void onResponse(Call<List<KarticaPodaci>> call, Response<List<KarticaPodaci>> response) {


                             if(response.isSuccessful()) {

                                 List<KarticaPodaci> json = response.body();

                                 LayoutInflater inflater = getLayoutInflater();


                                 linearLayout = findViewById(R.id.LinearLayoutKreditneKartice);


                                 for(KarticaPodaci kartica : json){
                                     if(kartica.getBrRacun() == null) {

                                         View v = inflater.inflate(R.layout.kreditne_kartice_view, null);


                                         vrstaKartice = (TextView) v.findViewById(R.id.textViewValueVrstaKreditne);

                                         brKartica = (TextView) v.findViewById(R.id.textViewValueBrKreditne1);
                                         oib = (TextView) v.findViewById(R.id.textViewValueOibKreditna);
                                         stanje = (TextView) v.findViewById(R.id.textViewValueStanjeKreditna);
                                         valjanost = (TextView) v.findViewById(R.id.textViewValueValjanostKreditna);
                                         limitKartice = (TextView) v.findViewById(R.id.textViewValueLimitKreditna);
                                         kamatnaStopa = (TextView) v.findViewById(R.id.textViewValueKamatnaStopaKreditna);
                                         datRate = (TextView) v.findViewById(R.id.textViewValueDatRateKreditna);



                                         vrstaKartice.setText(kartica.getVrstaKartice().getNazVrsteKartice());

                                         brKartica.setText(kartica.getBrKartica());
                                         oib.setText(kartica.getOib());
                                         stanje.setText(kartica.getStanje());
                                         valjanost.setText(kartica.getValjanost());
                                         limitKartice.setText(kartica.getLimitKartice());
                                         kamatnaStopa.setText(kartica.getKamStopa());
                                         datRate.setText(String.valueOf(kartica.getDatRate()));


                                         linearLayout.addView(v);

                                     }
                                 }


                             }else{
                                 Toast.makeText(getApplicationContext(), "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                             }

                         }

                         @Override
                         public void onFailure(Call<List<KarticaPodaci>> call, Throwable t) {
                             Toast.makeText(getApplicationContext(), "Pogreška", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
    }
}

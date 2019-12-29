package com.example.opp_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DebitneKartice extends AppCompatActivity {

    private Retrofit retrofit;
    private BBService service;

    private String jsessionid;

    private TextView brKartica;
    private TextView brRacun;
    private TextView valjanost;

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debitne_kartice);
        //setSupportActionBar(toolbar);
        Toolbar mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
//        getSupportActionBar().setTitle("Debitne kartice");
  //      getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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


                                 for(KarticaPodaci kartica : json){
                                     if(kartica.getBrRacun() != null) {

                                         linearLayout = findViewById(R.id.LinearLayoutDebitna);


                                         View v = inflater.inflate(R.layout.debitne_kartice_view, null);

                                         brKartica = (TextView) v.findViewById(R.id.textViewValueDebitnaR1);
                                         brRacun = (TextView) v.findViewById(R.id.textViewValueDebitna1);
                                         valjanost = (TextView) v.findViewById(R.id.textViewValueValjanostDebitna);


                                         brKartica.setText(kartica.getBrKartica());
                                         brRacun.setText(kartica.getBrRacun());
                                         valjanost.setText(kartica.getValjanost());


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

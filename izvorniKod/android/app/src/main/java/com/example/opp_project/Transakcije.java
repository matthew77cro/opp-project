package com.example.opp_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transakcije extends AppCompatActivity {

    private Retrofit retrofit;
    private BBService service;

    private String jsessionid;

    LinearLayout linearLayout;

    private TextView brTransakcija;
    private TextView racTerecenja;
    private TextView racOdobrenja;
    private TextView iznos;
    private TextView datTransakcije;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transakcije);


        retrofit = new Retrofit.Builder().baseUrl("http://104.45.11.92/bugbusters/").addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(BBService.class);

        jsessionid = getIntent().getStringExtra("SESSION_ID");



        Call<List<TransakcijaPodaci>> call = service.transakcije(jsessionid);

        call.enqueue(new Callback<List<TransakcijaPodaci>>() {
                         @Override
                         public void onResponse(Call<List<TransakcijaPodaci>> call, Response<List<TransakcijaPodaci>> response) {


                             if(response.isSuccessful()) {

                                 List<TransakcijaPodaci> json = response.body();


                                 LayoutInflater inflater = getLayoutInflater();


                                 for(TransakcijaPodaci transakcija : json){

                                     linearLayout = findViewById(R.id.LinearLayoutTransakcija);

                                     View v = inflater.inflate(R.layout.transakcije_view, null);


                                     brTransakcija = (TextView) v.findViewById(R.id.textViewValueBrTransakcija);
                                     racTerecenja = (TextView) v.findViewById(R.id.textViewValueRacunTerecenja);
                                     racOdobrenja = (TextView) v.findViewById(R.id.textViewValueRacunOdobrenja);
                                     iznos = (TextView) v.findViewById(R.id.textViewValueIznosTransakcije);
                                     datTransakcije = (TextView) v.findViewById(R.id.textViewValueDatumTransakcije);


                                     brTransakcija.setText(String.valueOf(transakcija.getBrTransakcija()));
                                     racTerecenja.setText(transakcija.getRacTerecenja());
                                     racOdobrenja.setText(transakcija.getRacOdobrenja());
                                     iznos.setText(transakcija.getIznos());
                                     datTransakcije.setText(transakcija.getDatTransakcije());


                                     linearLayout.addView(v);

                                 }


                             }else{
                                 Toast.makeText(getApplicationContext(), "Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                             }

                         }

                         @Override
                         public void onFailure(Call<List<TransakcijaPodaci>> call, Throwable t) {
                             Toast.makeText(getApplicationContext(), "Pogreška", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
    }
}

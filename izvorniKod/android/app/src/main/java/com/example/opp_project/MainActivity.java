package com.example.opp_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    private EditText ime;
    private EditText lozinka;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Ion.with(getApplicationContext())
                        .load("http://52.137.15.78:8080/bugbusters/rest/login")
                        .setBodyParameter("username", "ime")
                        .setBodyParameter("password", "lozinka")
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                try {
                                    JSONObject json = new JSONObject(result);    // Converts the string "result" to a JSONObject
                                    String json_result = json.getString("result"); // Get the string "result" inside the Json-object
                                    if (json_result.equalsIgnoreCase("ok")){ // Checks if the "result"-string is equals to "ok"
                                        // Result is "OK"
                                        int customer_id = json.getInt("customer_id"); // Get the int customer_id
                                    } else {
                                        // Result is NOT "OK"
                                        String error = json.getString("error");
                                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show(); // This will show the user what went wrong with a toast
                                        Intent to_main = new Intent(getApplicationContext(), MainActivity.class); // New intent to MainActivity
                                        startActivity(to_main); // Starts MainActivity
                                        finish(); // Add this to prevent the user to go back to this activity when pressing the back button after we've opened MainActivity
                                    }
                                } catch (JSONException exc){
                                    // This method will run if something goes wrong with the json, like a typo to the json-key or a broken JSON.
                                   Log.e(getClass().getName(), exc.getMessage());
                                    Toast.makeText(getApplicationContext(), "Please check your internet connection.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }



    }



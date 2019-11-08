package com.example.opp_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    Name = (EditText)findViewById(R.id.textView);
    Password = (EditText)findViewById(R.id.editText2);
    Login = (FrameLayout)findViewById(R.id.singInBtn);


    Login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view){
            if(//poslati login na provjeru) {
                Toast.makeText(getApplicationContext(),
                        "Prijavljeni!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Krivo uneseni podaci",Toast.LENGTH_SHORT).show();

            }
        }
    });


}



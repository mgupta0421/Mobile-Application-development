package com.example.week_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SecondActivity extends AppCompatActivity {


        TextView textViewgreeting;
        EditText sendbackback;
    final static public String DATA_ENTERED = "DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textViewgreeting = findViewById(R.id.textViewgreeting);
        sendbackback = findViewById(R.id.sendbackback);

        if(getIntent() !=null  && getIntent().hasExtra("USER")){

            User user = getIntent().getParcelableExtra(MainActivity.USER_KEY);
           /* ArrayList<User> users= (ArrayList<User>)getIntent().getSerializableExtra(MainActivity.USERS_KEY);
            Collections.shuffle(users);
            User user = users.get(0); */
            textViewgreeting.setText("Hello " +user.name);

            //User user = (User)getIntent().getSerializableExtra(MainActivity.USER_KEY);
            //String name = getIntent().getStringExtra("Name");
            //textViewgreeting.setText("Hello " + name);
            //textViewgreeting.setText("Hello " +user.name);
        }

        findViewById(R.id.sendback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredData = sendbackback.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(DATA_ENTERED,enteredData);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
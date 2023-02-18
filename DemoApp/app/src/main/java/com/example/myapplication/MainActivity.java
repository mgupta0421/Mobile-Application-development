package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    final String TAG = "demo";
    EditText editText;
    TextView textViewer;
    TextView textViewPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewer = findViewById(R.id.textViewer);
        textViewPrice = findViewById(R.id.textViewPrice);
        editText = findViewById(R.id.edittext);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateDiscount(5.0);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateDiscount(10.0);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateDiscount(15.0);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateDiscount(20.0);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateDiscount(50.0);
            }
        });
        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              editText.setText("");
              textViewer.setText("");
              textViewPrice.setText("");

          }
        });


        Log.d(TAG, "onCreate: ");

        Log.d(TAG, "onCreate: ");
        Toast.makeText(this, "Refreshing screen", Toast.LENGTH_LONG).show();
    }




    public void calculateDiscount(double discountValue) {
    try {
        double price = Double.valueOf(String.valueOf(editText.getText()));
        double calVal = (100 - discountValue) * discountValue / 100.0;
        textViewer.setText(String.valueOf(discountValue) + "%");
        textViewPrice.setText(String.valueOf(calVal));
    }catch(NumberFormatException e){
        Toast.makeText(MainActivity.this, "Enter a valid number", Toast.LENGTH_SHORT).show();

    }
    }
}
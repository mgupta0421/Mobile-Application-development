package com.example.week_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week_3.databinding.ActivityInclass3Binding;

public class Inclass_3 extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityInclass3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inclass3);
        setTitle("Main");

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inclass_3.this, inclass3_second.class);
                startActivity(intent);

            }
        });

    }
}
package com.example.week_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week_3.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityProfileBinding binding;
    TextView ONE, two, three, four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ONE = findViewById(R.id.ONE);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        if (getIntent() != null && getIntent().hasExtra(inclass3_second.Registration_Key)){
                Registration data = (Registration)getIntent().getSerializableExtra(inclass3_second.Registration_Key);
                ONE.setText(data.name);
                two.setText(data.email);
                three.setText(data.id);
                four.setText(data.department);
            Log.d("VALUES", "onCreate: " +ONE.getText().toString() + two.getText().toString()+ three.getText().toString() + four.getText().toString());
        }

    }

}
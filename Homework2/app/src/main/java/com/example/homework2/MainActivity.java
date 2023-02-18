package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.homework2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    TextView weightvalue_b;
    Weight value;
    ArrayList<Drink> drinks = new ArrayList<>();

    private ActivityResultLauncher<Intent> startGetProfileForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("RESULT COMING", "onActivityResult: ");
                    if(result != null && result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null && result.getData().getSerializableExtra(WeightActivity.WEIGHT_KEY) != null) {
                            Log.d("FINE", "onActivityResult: ");
                            value = (Weight) result.getData().getSerializableExtra(WeightActivity.WEIGHT_KEY);
                            Log.d("Weight value", value.gender + value.weight);
                            weightvalue_b.setText(value.weight + "lbs" + value.gender);
                        }else{
                            Log.d("NULL CHECK", "NULL CHECK");
                        }
                    }else{
                         value = null;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightvalue_b = findViewById(R.id.weightvalue_b);
        findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeightActivity.class);
                startGetProfileForResult.launch(intent);
            }
        });
    }
}
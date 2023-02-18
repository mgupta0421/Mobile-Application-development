package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.homework2.databinding.ActivityWeightBinding;

public class WeightActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityWeightBinding binding;
    EditText enterweight;
    RadioGroup radioGroup;
    public static final String WEIGHT_KEY = "WEIGHT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        enterweight = findViewById(R.id.enterweight);
        radioGroup = findViewById(R.id.radioGroup);

        findViewById(R.id.set_w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gendervalue = "Female";
                if(radioGroup.getCheckedRadioButtonId() == R.id.male){
                    gendervalue = "Male";
                }
                try{
                    double weightvalue = Double.valueOf(enterweight.getText().toString());
                   // Log.d("Weight value", String.valueOf(weightvalue));
                    if(weightvalue >0){
                        Weight weight = new Weight(weightvalue, gendervalue);
                        Log.d("Weight value", weight.weight + weight.gender);
                        Intent intent = new Intent();
                        intent.putExtra(WEIGHT_KEY, weight);
                        //intent.putExtra(WEIGHT_KEY, weight);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }catch (NumberFormatException ex){
                    Toast.makeText(WeightActivity.this, "Enter a valid weight", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

}
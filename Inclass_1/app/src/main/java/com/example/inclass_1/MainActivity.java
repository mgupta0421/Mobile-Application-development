package com.example.inclass_1;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.inclass_1.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText number1, number2;
    TextView result;
    RadioGroup group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        result = findViewById(R.id.result);
        group =findViewById(R.id.group);

        String text_1 = number1.getText().toString();
        String text_2 = number2.getText().toString();


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   if(group.getCheckedRadioButtonId() == (R.id.add)){
                    calculateadd(value_1, value_2);
                } else if (group.getCheckedRadioButtonId() == (R.id.subtract)) {
                    calculatesubtract(text_1, text_2);
                }else if (group.getCheckedRadioButtonId() == (R.id.multiply)) {
                    calculatemultiply(text_1, text_2);
                }else if (group.getCheckedRadioButtonId() == (R.id.divide)) {
                    calculatedivide(text_1, text_2);
                } */

                try{
                    double value_1 = Double.valueOf(text_1);
                    double value_2 = Double.valueOf(text_2);

                    if (value_1 > 0 && value_2 > 0) {
                        double calculated_value = (value_1 / value_2);
                        result.setText(String.format("%.2f", calculated_value));
                    }else if(value_2 == 0){
                        Toast.makeText(MainActivity.this, "B cannot be zero", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Enter the value A and B", Toast.LENGTH_SHORT).show();
                    }

                }catch(NumberFormatException e){
                    Toast.makeText(MainActivity.this, "Exception Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number1.setText("");
                number2.setText("");
                result.setText("");
            }
        });

    }

   /*  void calculateadd(Double value_1, Double value_2){
        try{

            if(group.getCheckedRadioButtonId() == (R.id.add)){
                //calculateadd();
            }
            if (value_1 > 0 && value_2 > 0) {
                double calculated_value = (value_1 + value_2);
                result.setText(String.format("%.2f", calculated_value));
            } else{
                Toast.makeText(MainActivity.this, "Enter the value A and B", Toast.LENGTH_SHORT).show();
            }

        }catch(NumberFormatException e){
            Toast.makeText(MainActivity.this, "Exception Occured", Toast.LENGTH_SHORT).show();
        }
    }

    void calculatesubtract(String text_1, String text_2){
        try{
            double value_1 = Double.valueOf(text_1);
            double value_2 = Double.valueOf(text_2);

            if (value_1 > 0 && value_2 > 0) {
                double calculated_value = (value_1 - value_2);
                result.setText(String.format("%.2f", calculated_value));
            } else{
                Toast.makeText(MainActivity.this, "Enter the value A and B", Toast.LENGTH_SHORT).show();
            }

        }catch(NumberFormatException e){
            Toast.makeText(MainActivity.this, "Exception Occured", Toast.LENGTH_SHORT).show();
        }
    }

    void calculatemultiply(String text_1, String text_2){
        try{
            double value_1 = Double.valueOf(text_1);
            double value_2 = Double.valueOf(text_2);

            if (value_1 > 0 && value_2 > 0) {
                double calculated_value = (value_1 * value_2);
                result.setText(String.format("%.2f", calculated_value));
            } else{
                Toast.makeText(MainActivity.this, "Enter the value A and B", Toast.LENGTH_SHORT).show();
            }

        }catch(NumberFormatException e){
            Toast.makeText(MainActivity.this, "Exception Occured", Toast.LENGTH_SHORT).show();
        }
    }

    void calculatedivide(String text_1, String text_2){
        try{
            double value_1 = Double.valueOf(text_1);
            double value_2 = Double.valueOf(text_2);

            if (value_1 > 0 && value_2 > 0) {
                double calculated_value = (value_1 / value_2);
                result.setText(String.format("%.2f", calculated_value));
            }else if(value_2 == 0){
                Toast.makeText(MainActivity.this, "B cannot be zero", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Enter the value A and B", Toast.LENGTH_SHORT).show();
            }

        }catch(NumberFormatException e){
            Toast.makeText(MainActivity.this, "Exception Occured", Toast.LENGTH_SHORT).show();
        }
    } */
}
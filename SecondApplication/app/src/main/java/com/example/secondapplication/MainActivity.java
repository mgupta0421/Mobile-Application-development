package com.example.secondapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    EditText enterweight;
    RadioGroup radiogroupgender, driksize;
    TextView alcohol, weightvalue, drink2, BAC, textviewstatus, alcoholpercent;
    SeekBar seekBar2;
    View view2;

    Profile profile;
    ArrayList<Drink> drinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        enterweight = findViewById(R.id.enterweight);
        radiogroupgender = findViewById(R.id.radiogroupgender);
        weightvalue = findViewById(R.id.weightvalue);
        drink2 = findViewById(R.id.drink2);
        alcoholpercent = findViewById(R.id.alcoholpercent);
        seekBar2 = findViewById(R.id.seekBar2);
        driksize = findViewById(R.id.driksize);
        BAC = findViewById(R.id.BAC);
        textviewstatus = findViewById(R.id.textviewstatus);
        view2 = findViewById(R.id.view2);

        findViewById(R.id.setweight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weight_value = enterweight.getText().toString();
                try {
                    double weight = Double.valueOf(weight_value);

                    if (weight > 0) {
                        String gender = "Female";
                        if (radiogroupgender.getCheckedRadioButtonId() == R.id.radioButtonMale) {
                            gender = "Male";
                        }
                        profile = new Profile(gender, weight);
                        //Display the entered weight and gender as shown in Fig 1(c).
                        // Clear the weight EditText.
                        weightvalue.setText(weight + " lbs (" + gender + ")");
                        enterweight.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Enter a valid weight !!", Toast.LENGTH_SHORT).show();
                    }
                    drinks.clear();
                    calcucaleBAC();
                    // Clear any of the previously added drinks, clear the BAC and UI should be as shown in Fig 1(c).
                } catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "Enter a valid weight !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                alcoholpercent.setText(i + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        findViewById(R.id.adddrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weightvalue != null) {
                    double size = 1;
                    if (driksize.getCheckedRadioButtonId() == R.id.five) {
                        size = 5;
                    } else if (driksize.getCheckedRadioButtonId() == R.id.twelve) {
                        size = 12;
                    }
                    double percentage = seekBar2.getProgress();
                    if (percentage > 0) {
                        Drink drink = new Drink(size, percentage);
                        drinks.add(drink);
                        calcucaleBAC();
                    } else {
                        Toast.makeText(MainActivity.this, "Set the weight and gender", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile = null;
                weightvalue.setText("");
                drinks.clear();
                calcucaleBAC();

                radiogroupgender.check(R.id.radioButtonFemale);
                enterweight.setText("");

                driksize.check(R.id.one);
                seekBar2.setProgress(12);

            }
        });

    }
        void calcucaleBAC() {

            drink2.setText(" # Drinks: " + String.valueOf(drinks.size()));
            if (drinks.size() == 0) {
                BAC.setText("BAC Level: 0.000");
                textviewstatus.setText("Yor're safe");
                view2.setBackgroundColor(getColor(R.color.safe_color));
            } else {
                double valueA = 0.00;

                for (Drink drink : drinks) {
                    valueA = valueA + drink.getSize() * drink.getPercentage() / 100.0;

                }

                double valueR = 0.73;
                if (profile.getGender() == "Female") {
                    valueR = 0.66;
                }

                double bac = valueA * 5.14 / (profile.getWeight() * valueR);
                BAC.setText("BAC Level: " + String.format("%.3f", bac));

                if (bac <= 0.08) {
                    textviewstatus.setText("You're safe");
                    view2.setBackgroundColor(getColor(R.color.safe_color));
                } else if (bac <= 0.2) {
                    textviewstatus.setText("Be careful");
                    view2.setBackgroundColor(getColor(R.color.be_careful));
                } else if ((bac > 0.2)){
                    textviewstatus.setText("Over the limit");
                    view2.setBackgroundColor(getColor(R.color.limit));
                } else {
                    Toast.makeText(this, "Nothing available", Toast.LENGTH_SHORT).show();
                }


            }


    }

}
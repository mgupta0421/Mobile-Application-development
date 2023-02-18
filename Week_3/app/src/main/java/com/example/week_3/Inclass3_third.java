package com.example.week_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week_3.databinding.ActivityInclass3ThirdBinding;

public class Inclass3_third extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityInclass3ThirdBinding binding;
    final static public String Depy_key = "Dept";

    RadioGroup maingroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inclass3_third);

        findViewById(R.id.submitbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maingroup = findViewById(R.id.maingroup);
                String deptData = "Computer Science";
                if(maingroup.getCheckedRadioButtonId() == (R.id.sis)){
                    deptData = "Software Implementation";
                } else if (maingroup.getCheckedRadioButtonId() == (R.id.bio)) {
                    deptData = "Bio Informatics";
                } else if (maingroup.getCheckedRadioButtonId() == (R.id.ds)){
                    deptData = "Data Science";
                }
                Intent intent = new Intent(Inclass3_third.this, inclass3_second.class);
                intent.putExtra(Depy_key,deptData);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
package com.example.week_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

public class inclass3_second extends AppCompatActivity {

    final static public String Registration_Key = "REGISTRATION";
    final static public int REQ_CODE = 100;

    EditText nametext, emailtext, idtext;
    TextView deptvalue;
    String enteredDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inclass3_second);

        nametext = findViewById(R.id.nametext);
        emailtext = findViewById(R.id.emailtext);
        idtext = findViewById(R.id.idtext);
        deptvalue = findViewById(R.id.deptvalue);
       // deptvalue.setText("");

        findViewById(R.id.godepartmentlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inclass3_second.this, Inclass3_third.class);
                //intent.putExtra(Registration_Key, new Registration(namevalue, emailvalue, idvalue));
             //  startActivityForResult(intent, REQ_CODE);
                startActivityForResult(intent, REQ_CODE);
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namevalue =nametext.getText().toString();
                String emailvalue =emailtext.getText().toString();
                String idvalue = idtext.getText().toString();
                String departmentvalue = enteredDept;
                Intent intent = new Intent(inclass3_second.this, ProfileActivity.class);
               // Registration registration = new Registration()
                intent.putExtra(Registration_Key, new Registration(namevalue, emailvalue, idvalue,departmentvalue));

                //intent.putExtra(intent);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("inside rESULT", "onActivityResult: ");
        if(data != null && data.hasExtra(Inclass3_third.Depy_key)){
            enteredDept = data.getStringExtra(Inclass3_third.Depy_key);
            deptvalue.setText(enteredDept);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
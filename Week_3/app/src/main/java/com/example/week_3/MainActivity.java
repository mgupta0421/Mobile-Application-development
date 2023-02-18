package com.example.week_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week_3.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    TextView retrievedata;
    final static public String USER_KEY = "USER";
    final static public String USERS_KEY = "USERS";
    final static public String DATA_ENTERED = "DATA";
    final static public int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
        findViewById(R.id.navigate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.week_3.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, REQ_CODE);

                //intent.putExtra("Name", "Alex");
               // intent.putExtra("USER", new User("BOB", 22));

               /* ArrayList<User> users= new ArrayList<>();
                users.add(new User("BOB", 22));
                users.add(new User("EMILY", 25));
                intent.putExtra(USER_KEY,users);
                startActivity(intent); */
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null && data.hasExtra(SecondActivity.DATA_ENTERED)){
            String dataEntered = data.getStringExtra(SecondActivity.DATA_ENTERED);
            Log.d("Hello", "onActivityResult: " + dataEntered);
        }
        //retrievedata.setText(data.toString());
        Log.d("BACK", "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
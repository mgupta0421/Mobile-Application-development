package com.example.week5;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week5.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String TAG ="DEMO";
    ListView listview;
    String[] colors = {"Red", "Green", "Blue"};
    ArrayList<User> users = new ArrayList<>();
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users.add(new User("Bob", 34));
        users.add(new User("Alice", 41));

        listview = findViewById(R.id.listview);
        adapter = new UserAdapter(this, R.layout.user_row_list, users);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //User value = new User("Marshal", 56);
                    //users.add(value);
                    //adapter.notifyDataSetChanged();
            }
        });

    }
}
package com.example.class4;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.class4.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainFragment.MainListener, RegistrationFragment.RegistrationListener
, DeprtmentFragment.DeparmentListner {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentview, new MainFragment())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void register() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentview, new RegistrationFragment(), "RegistrationFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setDept() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentview, new DeprtmentFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendUser(User user) {
        Log.d("USERVALUE", "sendUser: " + user.getDept());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentview, ProfileFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void selectDept(String value) {
        Log.d("DEPARTMENT", "selectDept: " + value);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentview, RegistrationFragment.newInstance(value))
                .addToBackStack(null)
                .commit();
       /* RegistrationFragment registerationFragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("RegistrationFragment");
        if(registerationFragment != null){
            registerationFragment.setDept(value);
        }
        getSupportFragmentManager().popBackStack(); */


    }
}
package com.example.week4;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week4.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity implements SecondFragment.MainInterface, SettingFragment.SettingsListener, EducaionFragment.EdusubmitListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySecondBinding binding;

                public static final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // setSupportActionBar(binding.toolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentview, new SecondFragment(), "SecondFragment")
                .commit();

    }


    @Override
    public void sendProfile(Profile profile) {
       // Log.d(TAG, "sendProfile: " + profile.getName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentview, ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();
    }

    // implement the interface defined in the fragment
    @Override
    public void sendUsername(String username) {
       // Log.d(TAG, "sendUsername: " + username);
    }

    @Override
    public void goToSettings() {
       // Log.d(TAG, "goToSettings: ");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentview, new SettingFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToeducation() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentview, new EducaionFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void goBackToHomeFragment() {
        Log.d(TAG, "goBackToHomeFragment: ");

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void course(String Education) {
        Log.d(TAG, "course: " +Education );

        // find the fragment to send the data(by tag)
        //send the data
        //do popstack

        SecondFragment secondfragment =(SecondFragment)getSupportFragmentManager().findFragmentByTag("SecondFragment");
        if(secondfragment !=null){
            secondfragment.setSelectedEdu(Education);
            getSupportFragmentManager().popBackStack();
        }

    }
}
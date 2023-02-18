package com.example.week4;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.week4.databinding.ActivityViewBinding;

public class ViewActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityViewBinding binding;
    ViewPager2 viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewpager = findViewById(R.id.pager);
    }

}
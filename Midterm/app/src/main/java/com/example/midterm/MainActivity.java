package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.midterm.models.Product;

public class MainActivity extends AppCompatActivity implements ProductsFragment.ProductListerner, ReviewsFragment.ReviewListerner, CreateReviewFragment.CreateListerner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.rootView, new ProductsFragment())
                .commit();
    }

    @Override
    public void productname(Product product) {

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.rootView, ReviewsFragment.newInstance(product))
                .commit();
    }

    @Override
    public void writeReview(Product product) {

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.rootView, CreateReviewFragment.newInstance(product))
                .commit();
    }

    @Override
    public void completeForum() {
        getSupportFragmentManager().popBackStack();
    }
}
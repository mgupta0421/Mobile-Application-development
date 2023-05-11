package edu.uncc.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.midtermapp.models.Question;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeListener, TriviaFragment.TriviaListener, StatsFragment.StatsListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new WelcomeFragment())
                .commit();
    }

    @Override
    public void sendQuestionsToTriva(ArrayList<Question> questions) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, TriviaFragment.newInstance(questions))
                .commit();
    }

    @Override
    public void gotoStats(int stat, int size) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, StatsFragment.newInstance(stat, size))
                .commit();
    }

    @Override
    public void startNewGame() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new WelcomeFragment())
                .commit();
    }
}
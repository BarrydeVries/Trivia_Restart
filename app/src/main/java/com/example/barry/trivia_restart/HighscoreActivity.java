package com.example.barry.trivia_restart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HighscoreActivity extends AppCompatActivity implements HighscoresHelper.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        HighscoresHelper highscoreshelper = new HighscoresHelper();
        highscoreshelper.getHighscores(HighscoreActivity.this);
    }

    public void restartGame (View v) {
        // redirect to start of game
        Intent intent = new Intent(HighscoreActivity.this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        Collections.sort(highscores);

        // set up adapter for scores
        HighscoresAdapter adapter = new HighscoresAdapter(this, R.layout.highscore_display, highscores);
        ListView listView = findViewById(R.id.scoreboard);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(HighscoreActivity.this, message, Toast.LENGTH_LONG).show();
    }
}

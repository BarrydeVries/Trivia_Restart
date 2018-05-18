package com.example.barry.trivia_restart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EndOfGameActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference scoreDatabase;
    private FirebaseUser user;
    private int score;


    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        scoreDatabase = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            Intent intent = new Intent(EndOfGameActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        // retrieve score from intent
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

    }

    public void restartGame (View v) {
        // redirect to start of game
        Intent intent = new Intent(EndOfGameActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void postScore (View v) {
        // get text from namefield
        EditText nameView = findViewById(R.id.nameField);
        String nameText = nameView.getText().toString();

        // ensure name is not empty
        if (!nameText.equals("")){
            // post highscore and go to highscores screen
            Highscore aHighscore = new Highscore(nameText, score);
            scoreDatabase.child("highscoresDatabase").child(user.getUid() + nameText).setValue(aHighscore);
            Intent intent = new Intent(EndOfGameActivity.this, HighscoreActivity.class);
            startActivity(intent);
        }
    }

    public void showHighscores(View V) {
        Intent intent = new Intent(EndOfGameActivity.this, HighscoreActivity.class);
        startActivity(intent);
    }
}

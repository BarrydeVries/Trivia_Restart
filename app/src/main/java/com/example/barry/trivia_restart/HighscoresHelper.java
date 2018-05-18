package com.example.barry.trivia_restart;

import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HighscoresHelper {
    private ArrayList<Highscore> highscoresArrayList = new ArrayList<Highscore>();

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

    public void getHighscores (Callback activity) {
        // get refrence to database
        final Callback activityFinal = activity;
        DatabaseReference scoreDatabase = FirebaseDatabase.getInstance().getReference();
        scoreDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot highscoreSnapshot: dataSnapshot.child("highscoresDatabase").getChildren()) {
                    Highscore aHighscore = highscoreSnapshot.getValue(Highscore.class);
                    highscoresArrayList.add(aHighscore);
                    Log.d("say hi","a first item to show");
                }
                activityFinal.gotHighscores(highscoresArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activityFinal.gotHighscoresError(databaseError.getMessage());
            }
        });
    }

}

package com.example.barry.trivia_restart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback{
    private int questionAmount = 10;
    private int questionNow = 1;
    private int correctAnswers = 0;
    String realAnswear;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(GameActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // create request for new question
        TriviaHelper request = new TriviaHelper(GameActivity.this);
        request.getNextQuestion(this);
    }

    @Override
    public void gotQuestion(Question question){
        TextView titleView = findViewById(R.id.title);
        TextView questionView = findViewById(R.id.question);

        String titleText = "Question " + questionNow + "/" + questionAmount;

        titleView.setText(titleText);
        questionView.setText(question.getQuestion());
        realAnswear = question.getAnswer();
        Log.d(realAnswear, "the correct answer");
    }

    @Override
    public void gotQuestionError(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, message, duration).show();
    }

    public void confirm_button_clicked(View v) {
        // check answer
        EditText editText = (EditText)findViewById(R.id.answer);
        String answer = editText.getText().toString();
        if (answer.equals(realAnswear)) {
            correctAnswers += 1;
            Log.d(String.valueOf(correctAnswers), "correct");
        }

        // redirect to next question or scoreboard
        questionNow += 1;
        if (questionNow <= questionAmount) {
            // create request for new question
            TriviaHelper request = new TriviaHelper(GameActivity.this);
            editText.setText("");
            request.getNextQuestion(this);
        }
        else {
            // compute score
            int score = correctAnswers * 10;


            // add score to database
            Intent intent = new Intent(GameActivity.this, EndOfGameActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }
    }

    public void logout_clicked(View v) {
        mAuth.signOut();
        Intent intent = new Intent(GameActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}


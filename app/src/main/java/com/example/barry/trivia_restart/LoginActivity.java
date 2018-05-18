package com.example.barry.trivia_restart;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        onStart();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, GameActivity.class);
            startActivity(intent);
        }
    }

    public void createClicked (View v) {
        // get email and password
        EditText emailView = (EditText)findViewById(R.id.email);
        EditText passwordView = (EditText)findViewById(R.id.password);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if(!password.equals("") && !(password.length() < 8)){
            createUser(email, password);
        }
        else {
            Context context = getApplicationContext();
            String text = "Put in an email address and a password of length 8 or longer";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void loginClicked (View v) {
        // get email and password
        EditText emailView = (EditText)findViewById(R.id.email);
        EditText passwordView = (EditText)findViewById(R.id.password);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        signIn(email, password);
    }

    public void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("user created", "createUserWithEmail:success");
                        user = mAuth.getCurrentUser();
                        // redirect to game
                        Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("something went wrong", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        user = null;
                    }
                }
            });
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("signed in", "signInWithEmail:success");
                        user = mAuth.getCurrentUser();

                        // redirect to game
                        Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("something went wrong", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        user = null;
                    }
                }
            });
    }
}

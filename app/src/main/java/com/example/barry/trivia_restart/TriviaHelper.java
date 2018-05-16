package com.example.barry.trivia_restart;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TriviaHelper implements Response.Listener<JSONArray>, Response.ErrorListener{
    public interface Callback {
        void gotQuestion(Question question);
        void gotQuestionError(String message);
    }

    private final String URL_MENU = "http://jservice.io//api/random";
    private Context globalContext;
    private Callback globalActivity;

    @Override
    public void onErrorResponse(VolleyError error) {
        globalActivity.gotQuestionError(error.getMessage());
    }

    @Override
    public void onResponse(JSONArray response) {

        try {
            // get necessary fields from JSON
            JSONObject questionObject = response.getJSONObject(0);

            String questionString = questionObject.getString("question");
            String answer         = questionObject.getString("answer");

            Question question = new Question(questionString, answer);

            // give the result back to the callback activity
            globalActivity.gotQuestion(question);
        } catch (JSONException e) {
            // catch and log JSON exceptions
            Log.e("MYAPP", "unexpected JSON exception", e);
            String message = "Something went wrong";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(globalContext, message, duration).show();
        }
    }

    public TriviaHelper(Context context) {
        globalContext = context;
    }

    public void getNextQuestion(Callback activity) {
        globalActivity = activity;

        // get the next question from api as JSON
        RequestQueue queue = Volley.newRequestQueue(globalContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_MENU,
                null, this, this);
        queue.add(jsonArrayRequest);
    }
}


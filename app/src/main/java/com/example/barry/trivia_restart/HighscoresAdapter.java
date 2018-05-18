package com.example.barry.trivia_restart;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoresAdapter extends ArrayAdapter<Highscore>{
    private ArrayList<Highscore> Highscores;

    public HighscoresAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> objects) {
        super(context, resource, objects);
        Highscores = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_display, parent, false);
        }
        TextView nameView = convertView.findViewById(R.id.name);
        TextView scoreView = convertView.findViewById(R.id.score);

        Highscore highscore = (Highscore) Highscores.get(position);

        nameView.setText(highscore.getName());
        scoreView.setText(Integer.toString(highscore.getScore()));
        return convertView;
    }
}

package com.udacity.jokeshower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeShowerActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA_KEY = "jokeExtrakey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_shower);

        Intent intent = this.getIntent();

        if(intent != null) {
            String joke = intent.getExtras().getString(JOKE_EXTRA_KEY);

            TextView view = (TextView) this.findViewById(R.id.textView_joke);
            view.setText(joke);
        }
    }
}

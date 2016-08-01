package com.example.android.jokedisplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String DISPLAY_JOKE_KEY = "display_joke_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        String joke = getIntent().getStringExtra(DISPLAY_JOKE_KEY);
        TextView tv = (TextView) findViewById(R.id.display_joke_textView);
        tv.setText(joke);

    }
}

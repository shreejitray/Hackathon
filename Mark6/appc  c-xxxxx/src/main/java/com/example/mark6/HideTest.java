package com.example.mark6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HideTest extends AppCompatActivity {

    static int toggle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_test);
    }

    public void hideMe(View view){
        if(toggle == 0) {
            TextView textView = findViewById(R.id.conditionalText);
            textView.setVisibility(View.GONE);
            Button button = view.findViewById(R.id.button2);
            button.setText("Show text");
            toggle = 1;
        }else{
            TextView textView = findViewById(R.id.conditionalText);
            textView.setVisibility(View.VISIBLE);
            Button button = view.findViewById(R.id.button2);
            button.setText("Hide Text");
            toggle = 0;
        }

    }
}

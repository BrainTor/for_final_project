package com.example.forthefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int [] ids = {R.id.b1,R.id.b2,R.id.b3,R.id.b4, R.id.b5};
    int current_lvl=0;

    LinearLayout location_main, location_quest;

    TextView profession , question_text;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location_main = findViewById(R.id.location_main);
        location_quest = findViewById(R.id.location_quest);
        profession = findViewById(R.id.prof_text);
        question_text = findViewById(R.id.prof_quest);

    }
    public  void select_quetion(View view){
        Question question = null;

        for(int i = 0; i< ids.length;i++)
            if(ids[i] == view.getId())
                question = new Question(i);
        String [] questions  = question.getQuestions();
        String [][] varinats = question.getOptions();
        int[] correct_variant = question.getCorrectAnswers();

        profession.setText(question.getProfessionName());
        question_text.setText(questions[current_lvl]);
        location_main.setVisibility(View.GONE);
        location_quest.setVisibility(View.VISIBLE);
    }

    public void back(View view){
        location_quest.setVisibility(View.GONE);
        location_main.setVisibility(View.VISIBLE);

    }
}
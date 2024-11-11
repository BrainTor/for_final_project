package com.example.forthefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final int [] ids = {R.id.b1,R.id.b2,R.id.b3,R.id.b4, R.id.b5};
    int current_lvl=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public  void select_quetion(View view){
        Question question = null;
        for(int i = 0; i< ids.length;i++)
            if(ids[i] == view.getId())
                question = new Question(i);
        String [] questions  = question.getQuestions();
        String [][] varinats = question.getOptions();
        int[] correct_variant = question.getCorrectAnswers();
    }
}
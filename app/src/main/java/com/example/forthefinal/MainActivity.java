package com.example.forthefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int[] ids = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5};
    final int[] ids_vars = {R.id.an1, R.id.an2, R.id.an3, R.id.an4};
    int current_lvl = 0;
    int stars = 0;
    ImageView image_quest;
    Question question = null;
    LinearLayout current_window = null;
    String[] questions;
    String[][] varinats;
    int[] correct_variant;
    FrameLayout animation_container;
    Button an1, an2, an3, an4;
    int music_length = 0;
    LinearLayout location_main, location_quest;

    TextView profession, question_text;
    Ainimator animator = new Ainimator();
    TextView lvl_text;

     MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location_main = findViewById(R.id.location_main);
        location_quest = findViewById(R.id.location_quest);
        setTheme(R.style.AppTheme_Custom);
        profession = findViewById(R.id.prof_text);
        question_text = findViewById(R.id.prof_quest);
        an1 = findViewById(R.id.an1);
        an2 = findViewById(R.id.an2);
        an3 = findViewById(R.id.an3);
        an4 = findViewById(R.id.an4);

        image_quest =  findViewById(R.id.image_quest);
        lvl_text = findViewById(R.id.lvl_text);
        animation_container = findViewById(R.id.animation_container);
        // Инициализация кнопок с их исходным фоном
        setupButton(an1);
        setupButton(an2);
        setupButton(an3);
        setupButton(an4);
        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(true); // Optional: Loop the music

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.music);
            mp.setLooping(true); // Optional: Loop the music
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mp != null && !mp.isPlaying()) {
            mp.start();
            mp.seekTo(music_length);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null && mp.isPlaying()) {
            music_length = mp.getCurrentPosition();
            mp.pause();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            music_length = 0;
            mp.release();
            mp = null;
        }
    }


    public void control_music(View view){
        if(mp.isPlaying()){
            mp.pause();
            ((ImageView) view).setImageResource(R.drawable.nosound);
        }else {
            mp.start();
            ((ImageView) view).setImageResource(R.drawable.sound);
        }
    }
    /**
     * Метод для установки начальных параметров кнопки
     *
     * @param button Кнопка, которую нужно настроить
     */
    private void setupButton(Button button) {
        button.setBackgroundColor(Color.rgb(19, 62, 135));
        button.setTextColor(Color.WHITE);
    }

     /**
     * Метод для изменения цвета заливки кнопки, сохраняя скруглённые углы
     *
     * @param button Кнопка, цвет которой нужно изменить
     * @param color Новый цвет заливки
     */
    private void setButtonColor(Button button, int color) {
       button.setBackgroundColor(color);
       button.setTextColor(Color.WHITE);
    }

    /**
     * Метод для сброса цвета кнопки к исходному состоянию
     *
     * @param button Кнопка, цвет которой нужно сбросить
     */
    private void resetButtonColor(Button button) {
        button.setBackgroundColor(Color.rgb(19, 62, 135));
    }

    public void set_data() {
        if (questions != null && varinats != null && current_lvl < questions.length) {
            image_quest.setImageResource(question.imgs[question.cur_prof_num][current_lvl]);
            question_text.setText(questions[current_lvl]);
            an1.setText(varinats[current_lvl][0]);
            an2.setText(varinats[current_lvl][1]);
            an3.setText(varinats[current_lvl][2]);
            an4.setText(varinats[current_lvl][3]);
        }

    }

    public void select_quetion(View view) {
        for (int i = 0; i < ids.length; i++)
            if (ids[i] == view.getId())
                question = new Question(i);
        setupButton(an1);
        setupButton(an2);
        setupButton(an3);
        setupButton(an4);
        questions = question.getQuestions();
        varinats = question.getOptions();
        correct_variant = question.getCorrectAnswers();
        profession.setText(question.getProfessionName());
        set_data();
        location_main.setVisibility(View.GONE);
        location_quest.setVisibility(View.VISIBLE);
    }

    public void answer(View view) {

        // Отключаем все кнопки ответов, чтобы предотвратить повторные нажатия
        an1.setEnabled(false);
        an2.setEnabled(false);
        an3.setEnabled(false);
        an4.setEnabled(false);

        final Button selectedButton = (Button) view;
        boolean isCorrect = false;

        for (int i = 0; i < ids_vars.length; i++) {
            if (ids_vars[i] == view.getId()) {
                if (current_lvl < 5 && correct_variant[current_lvl] == i) { // Проверка правильности ответа
                    stars++;
                    lvl_text.setText(String.valueOf(stars));
                    setButtonColor(selectedButton, Color.GREEN); // Изменяем цвет на зелёный
                    isCorrect = true;
                    animator.startAtomAnimation(animation_container, this);
                    Log.d("MainActivity", "Correct answer. Stars: " + stars);
                } else {
                    setButtonColor(selectedButton, Color.RED); // Изменяем цвет на красный
                    Log.d("MainActivity", "Incorrect answer.");
                }
                break;
            }
        }

        // Используем Handler для задержки без блокировки UI
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Сбрасываем цвет кнопки к исходному состоянию
                resetButtonColor(selectedButton);
                current_lvl++;
                if (current_lvl < 5) { // Проверяем, есть ли ещё вопросы
                    set_data();
                    // Включаем кнопки ответов снова
                    an1.setEnabled(true);
                    an2.setEnabled(true);
                    an3.setEnabled(true);
                    an4.setEnabled(true);
                } else {
                    an1.setEnabled(true);
                    an2.setEnabled(true);
                    an3.setEnabled(true);
                    an4.setEnabled(true);
                    if(stars >3)
                        current_window = findViewById(R.id.window_best);
                    else if(stars<=3 && stars>=2)
                        current_window = findViewById(R.id.good);
                    else
                        current_window = findViewById(R.id.bad_window);
                    location_quest.setVisibility(View.GONE);
                    current_window.setVisibility(View.VISIBLE);
                }
            }
        }, 1000); // Задержка в 1 секунду
    }

    void reset_all() {
        current_lvl = 0;
        stars = 0;
        lvl_text.setText("0");
        question = null;
        questions = null;
        varinats = null;
        correct_variant = null;
    }

    public void back(View view) {
        location_quest.setVisibility(View.GONE);
        location_main.setVisibility(View.VISIBLE);
        reset_all();
    }
    public  void back_from_res(View view){
        current_window.setVisibility(View.GONE);
        current_window = null;
        location_main.setVisibility(View.VISIBLE);
        reset_all();
    }

    public void open_web(View view){
        String url = "https://www.mediafire.com/view/aago5ab7c8gwydl/f91c2e4b-9327-4c62-b424-b0556878ce16.jfif/file";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}

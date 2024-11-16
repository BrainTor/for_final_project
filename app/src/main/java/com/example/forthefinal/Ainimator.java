package com.example.forthefinal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

public class Ainimator {

    public void startAtomAnimation(FrameLayout animationContainer, Context context) {
        animationContainer.post(new Runnable() {
            @Override
            public void run() {
                int containerWidth = animationContainer.getWidth();
                int containerHeight = animationContainer.getHeight();

                for (int i = 0; i < 15; i++) { // Количество атомов
                    final ImageView atomView = new ImageView(context);
                    atomView.setImageResource(R.drawable.atom); // Замените на ваше изображение атома
                    int size = 90 + new Random().nextInt(150); // Размер от 30 до 70 пикселей
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
                    atomView.setLayoutParams(params);
                    animationContainer.addView(atomView);

                    // Устанавливаем начальную позицию атома внизу экрана с случайной горизонтальной позицией
                    float startX = new Random().nextInt(containerWidth - size);
                    float startY = containerHeight - size;
                    atomView.setX(startX);
                    atomView.setY(startY);

                    // Создаем путь для анимации
                    Path path = new Path();
                    path.moveTo(startX, startY);

                    // Генерируем случайные контрольные точки для кривой
                    int numPoints = 3 + new Random().nextInt(3); // От 3 до 5 точек
                    for (int j = 0; j < numPoints; j++) {
                        float x = new Random().nextInt(containerWidth);
                        float y = new Random().nextInt((int) startY);
                        path.lineTo(x, y);
                    }

                    // Анимация движения по пути
                    ObjectAnimator pathAnimator = ObjectAnimator.ofFloat(atomView, View.X, View.Y, path);
                    pathAnimator.setDuration(3000 + new Random().nextInt(2000)); // Длительность от 3 до 5 секунд

                    // Анимация прозрачности
                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(atomView, "alpha", 1f, 0f);
                    alphaAnimator.setDuration(pathAnimator.getDuration());

                    // Объединяем анимации
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(pathAnimator, alphaAnimator);
                    animatorSet.setStartDelay(new Random().nextInt(1000)); // Случайная задержка до 1 секунды
                    animatorSet.start();

                    final View finalAtomView = atomView;
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationContainer.removeView(finalAtomView);
                        }
                    });
                }
            }
        });
    }

}

package com.example.jmfarrabal.flagquizgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.Map;
import java.util.Random;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TableLayout;

public class main extends AppCompatActivity {

    //Variáveis de instânica

    private List<String> fileNameList; //nomes dos aquivos das bandeiras
    private List<String> quizCountriestList; // Nomes dos países do teste
    private Map<String, Boolean> regionsMap; // Quais regiões estão habilitadas
    private String correctAnswer; // País correto para a bandeira corrente
    private int totalGuesses; // Número de palpites dados
    private int correctAnswers; //Número de palpites certos
    private int guessRows; // Número de linhas que exibem escolhas
    private Random random; // gerador de números aleatórios
    private Handler handler; // Usado para atrasar o carregamento da próxima bandeira
    private Animation shakeAnimation; // Animação para palpite incorreto

    private TextView answerTextView;
    private TextView questionNumberTextView;
    private ImageView flagImageView;
    private TableLayout buttonTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

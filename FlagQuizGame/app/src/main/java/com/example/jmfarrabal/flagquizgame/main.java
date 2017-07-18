package com.example.jmfarrabal.flagquizgame;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TableLayout;

public class main extends AppCompatActivity {

    //Variáveis de instânica

    private static final String TAG = "FlagQuizGame Activity";

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

        fileNameList = new ArrayList<String>();
        quizCountriestList = new ArrayList<String>();
        regionsMap = new HashMap<String,Boolean>();
        guessRows = 1;
        random = new Random();
        handler = new Handler();

        shakeAnimation = AnimationUtils.loadAnimation(this,R.anim.incorrect_shaking);
        shakeAnimation.setRepeatCount(3);

        String[] regionNames = getResources().getStringArray(R.array.regionList);

        for(String region: regionNames)
            regionsMap.put(region,true);

        questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
        flagImageView = (ImageView) findViewById(R.id.flagImageView);
        buttonTableLayout = (TableLayout) findViewById (R.id.buttonTableLayout);
        answerTextView = (TextView) findViewById(R.id.answerTextView);

        questionNumberTextView.setText(getResources().getString(R.string.question) + "1 " +
        getResources().getString(R.string.of) + " 10" );

        resetQuiz();
    }



    private void resetQuiz(){

        AssetManager assets = getAssets();
        fileNameList.clear();

        try{

            Set<String> regions = regionsMap.keySet();

            for(String region : regions){

                if(regionsMap.get(region)) {
                    String[] paths = assets.list(region);

                    for (String path : paths)
                        fileNameList.add(path.replace(".png", ""));
                }
            }

        }
        catch(IOException e){
            Log.e(TAG,"Error loading image files ! :(", e);
        }

        correctAnswers = 0;
        totalGuesses = 0;
        quizCountriestList.clear();

        int flagCounter = 1;
        int numberOfFlags = fileNameList.size();

        // Sorteando as bandeiras e salvando na lista quizCountriesList
        while(flagCounter <= 10){
            int randomIndex = random.nextInt(numberOfFlags);
            String fileName = fileNameList.get(randomIndex);

            if(!quizCountriestList.contains(fileName)){
                quizCountriestList.add(fileName);
                ++flagCounter;
            }
        }
        //loadNextFlag();
    }
}

package com.example.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView topLabel;
    private TextView questionLabel;
    private Button FirstAnswerBtn;
    private Button SecondAnswerBtn;
    private Button ThirdAnswerBtn;
    private Button FourthAnswerBtn;

    private String rightAnswer;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 7;
    private int points = 0;

    ArrayList<ArrayList<String>> quizArr = new ArrayList<>();

    String quizData[][] = {
            //{"Въпрос", "Правилен отговор", "Друг отговор", "Друг отговор", "Друг отговор"}
            {"Когато критикуваме някого, казваме, че го правим на:", "бъз и коприва", "сол и пипер", "горчица и кетчуп", "сминдух и джоджен"}, {"В кой отговор американският щат не съвпада с посочената до него столица?","Върмонт - Вълчедръм", "Монтана - Хелена", "Канзас - Топика", "Арканзас - Литъл Рок"},
            {"Коя е столицата на Малта?", "Валета", "Малта", "Биркиркара", "Рабат"},
            {"Коя от длъжностите не е характерна за една рекламна агенция?", "Шлосер", "Копирайтър", "Арт директор", "Графичен дизайнер"},
            {"Какво огрява слънцето в националния химн на Република България?", "Тракия", "Дунав", "Стара планина", "Пирин"},
            {"Кой е държавен глава на България на 9 септември 1944 година?", "Симеон II", "Георги Димитров", "Борис II", "Йоанна Савойска"},
            {"Коя топка ще ни остане, когато премахнем жълтата, най-тежката и най-леката?", "Волейболната", "За тенис на маса", "Баскетболната", "За тенис на корт"}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topLabel = (TextView)findViewById(R.id.topLabel);
        questionLabel = (TextView)findViewById(R.id.questionLabel);
        FirstAnswerBtn = (Button)findViewById(R.id.FirstAnswerBtn);
        SecondAnswerBtn = (Button)findViewById(R.id.SecondAnswerBtn);
        ThirdAnswerBtn = (Button)findViewById(R.id.ThirdAnswerBtn);
        FourthAnswerBtn = (Button)findViewById(R.id.FourthAnswerBtn);


        for(int i = 0; i < quizData.length; i++) {
            //Инициализиране на въпроса, правилния отговор и останалите отговори
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(quizData[i][0]); //Въпрос
            tmp.add(quizData[i][1]); //Правилен отговор
            tmp.add(quizData[i][2]); //Друг отговор
            tmp.add(quizData[i][3]); //Друг отговор
            tmp.add(quizData[i][4]); //Друг отговор

            //Добавяме конфигурацията (въпросът и отговорите) към quizArr
            quizArr.add(tmp);
        }
        showNextLevel();
    }

    public void showNextLevel(){
        topLabel.setText("Въпрос номер " + quizCount);

        //Генерираме случайно число, което да определи следващия въпрос на случаен принцип
        Random r = new Random();
        int random = r.nextInt(quizArr.size());

        //Инициализираме въпрос на случаен принцип
        ArrayList<String> quiz = quizArr.get(random);

        //Взимаме въпросът и определяме, че първият отговор е правилният
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        //Премахваме въпроса и разбъркваме отговорите
        quiz.remove(0);
        Collections.shuffle(quiz);

        //Сетваме вече разбърканите отговори (стойности) в бутоните
        FirstAnswerBtn.setText(quiz.get(0));
        SecondAnswerBtn.setText(quiz.get(1));
        ThirdAnswerBtn.setText(quiz.get(2));
        FourthAnswerBtn.setText(quiz.get(3));

        //Махаме въпросът от другите, неотговорени до момента
        quizArr.remove(random);
    }

    public void checkAnswer(View view){
        //Натиснат бутон
        Button answerBtn = (Button)findViewById(view.getId());

        String btnText = answerBtn.getText().toString();

        final String alert;

        if (btnText.equals(rightAnswer)){
            //При правилен отговор
            points++;
            alert = "Браво! Правилен отговор. =)\nВерни отговори: " + points + " от " + QUIZ_COUNT;
        }
        else{
            //При грешен отговор
            alert = "Грешен отговор... :< \nВерни отговори: " + points + " от " + QUIZ_COUNT;
        }

        //Alert съобщение, което се появява при отговаряне/натискане на бутон
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alert);

        //Ако е натиснат правилният бутон, то да не показва кой е правилният отговор
        if(answerBtn.getText() != rightAnswer) {

            builder.setMessage("Верният отговор е: " + rightAnswer);
        }


        builder.setPositiveButton("OK! Следващият.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (quizCount == QUIZ_COUNT) {
                    //Toast съобщение, че играта приключи и при натискане на бутон, да я затваря
                    Toast.makeText(getBaseContext(), "Играта приключи." + "\nВерни отговори: " + points + " от " + QUIZ_COUNT,
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    quizCount++;
                    showNextLevel();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}

package com.example.hamiltontevin_ce02;
// Name: Hamilton Tevin
// Term: JAV1 2002
// File Name: MainActivity.java

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private final Random mRandom = new Random();
    private final int[] mRandNumArray = new int[4];

    private Toast mToast = null;

    private int numOfAttempts = 4;

    private final int[] colorId = { R.color.colorBlue,R.color.colorGreen,R.color.colorRed,R.color.colorInitial};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_SubmitGuess).setOnClickListener(mClickListenerSubmitGuess);
        for (int i = 0; i < 4; i++) {
            int currentNum = mRandom.nextInt(9);
            mRandNumArray[i] = currentNum;
            Log.i("number", String.valueOf(currentNum));
        }

    }

    private final View.OnClickListener mClickListenerSubmitGuess = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final EditText ed1 = findViewById(R.id.et_R1C1);
            final EditText ed2 = findViewById(R.id.et_R1C2);
            final EditText ed3 = findViewById(R.id.et_R1C3);
            final EditText ed4 = findViewById(R.id.et_R1C4);
            
            String userChoice1 = ed1.getText().toString();
            String userChoice2 = ed2.getText().toString();
            String userChoice3 = ed3.getText().toString();
            String userChoice4 = ed4.getText().toString();
            if (userChoice1.isEmpty() && userChoice2.isEmpty() && userChoice3.isEmpty() && userChoice4.isEmpty()
                    || (userChoice1.isEmpty() || userChoice2.isEmpty()|| userChoice3.isEmpty() || userChoice4.isEmpty())) {
                mToast = Toast.makeText(MainActivity.this, R.string.empty_editText, LENGTH_SHORT);
                mToast.show();
            } else {
                String[] choiceStringArray = new String[4];

                choiceStringArray[0] = userChoice1;
                choiceStringArray[1] = userChoice2;
                choiceStringArray[2] = userChoice3;
                choiceStringArray[3] = userChoice4;
                int numRightNumbers = 0;
                int hotOrCold;
                numOfAttempts --;
                for (int i = 0; i < mRandNumArray.length; i++) {
                        int currentNum = 0;

                        try {
                            currentNum = Integer.parseInt(choiceStringArray[i]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        if (mRandNumArray[i] == currentNum) {
                            hotOrCold = 1;
                        } else if (mRandNumArray[i] > currentNum) {
                            hotOrCold = 0;
                        } else {
                            hotOrCold = 2;
                        }

                        int color = 0;
                        switch (hotOrCold) {
                            case 0:
                                color = getColor(colorId[0]);
                                break;
                            case 1:
                                color = getColor(colorId[1]);
                                numRightNumbers ++;
                                break;
                            case 2:
                                color = getColor(colorId[2]);
                                break;
                        }

                        switch (i) {
                            case 0:
                                ed1.setTextColor(color);
                                break;
                            case 1:
                                ed2.setTextColor(color);
                                break;
                            case 2:
                                ed3.setTextColor(color);
                                break;
                            case 3:
                                ed4.setTextColor(color);
                                break;
                        }
                    MakeAToast();
                    }

                if (numRightNumbers == 4) {
                    GameRestart(true);
                }else if (numOfAttempts == 0){
                    GameRestart(false);
                    }
                }
        }
    };

    private void GameRestart(Boolean b){

        final EditText ed1 = findViewById(R.id.et_R1C1);
        final EditText ed2 = findViewById(R.id.et_R1C2);
        final EditText ed3 = findViewById(R.id.et_R1C3);
        final EditText ed4 = findViewById(R.id.et_R1C4);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        if(b){
            builder.setTitle(R.string.you_won);
        }
        else{
            builder.setTitle(R.string.you_lost);
        }
        if(b){
            builder.setMessage(R.string.congrats);
        }
        else{
            builder.setMessage(R.string.end_of_guesses);
        }
        builder.setIcon(R.drawable.info_icon);
        builder.setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int intiColor;
                intiColor = getColor(colorId[3]);

                ed1.setText(R.string.blank_text);
                ed1.setTextColor(intiColor);

                ed2.setText(R.string.blank_text);
                ed2.setTextColor(intiColor);

                ed3.setText(R.string.blank_text);
                ed3.setTextColor(intiColor);

                ed4.setText(R.string.blank_text);
                ed4.setTextColor(intiColor);

                for (int a = 0; a < 4; a++) {
                    int currentNum = mRandom.nextInt(9);
                    mRandNumArray[a] = currentNum;
                }
                numOfAttempts = 4;
                MakeAToast();
            }
        });
        builder.show();

    }

    private void MakeAToast(){
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(MainActivity.this,  numOfAttempts +" "+ getString(R.string.remaining), LENGTH_SHORT);
        mToast.show();
    }
}

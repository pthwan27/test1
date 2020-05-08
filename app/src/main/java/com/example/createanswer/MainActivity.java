package com.example.createanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<16; i++){
                    Button bt = findViewById(R.id.bt1+i);
                    bt.setTextColor(Color.WHITE);
                }
            }
        }, 3000);

    }

    private void setting() {
        Random random = new Random();
        int checkarr[] = new int[16];
        //중복숫자 안들어가도록 체크하기위해 만든 array.

        for (int i = 0; i < 16; ) {
            boolean check = false;
            int num = random.nextInt(100) + 2; //2~101`
            for (int j = 0; j < checkarr.length; j++) {
                if (checkarr[j] == num) {
                    check = false;
                } else check = true;
            }

            if (check) {
                checkarr[i] = num;
                Button bt = findViewById(R.id.bt1 + i);

                bt.setText(String.valueOf(num));
                i++;
            } else {
            }
        }

        int temp = 0;
        String[] operarr = new String[]{"+", "-", "*", "/", "%", "^"};
        //6개의 연산자

        int checkarr2[] = new int[6];

        while (temp < 6) {

            //연산자 들어간 곳에 들어가면 안되니깐 체크하기위해

            int operNum = random.nextInt(16);
            //어디에 연산자 넣을지 정하는 숫자

            boolean check = false;
            for (int i = 0; i < checkarr2.length; i++) {
                if (operNum == checkarr2[i]) {
                    check = false;
                    break;
                } else check = true;
            }

            if (check == true) {
                checkarr2[temp] = operNum;
                Button bt = findViewById(R.id.bt1 + operNum);
                bt.setText(operarr[temp]);
                //연산자 넣기. ^ -> % -> / -> * -> - -> + 순서대로 들어간다.

                temp++;
            } else {
            }
        }
    }
    public void onClick(View view) {
        Button bt = (Button)view;
        String calStr = "";
        calStr += bt.getText().toString();
    }
}

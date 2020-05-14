package com.example.createanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int count = 3;
    String calStr = "";
    Long answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting();

        //3초뒤에 숫자가 안보기도록 하기위해서.
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 16; i++) {
//                    Button bt = findViewById(R.id.bt1 + i);
//                    bt.setTextColor(Color.WHITE);
//                }
//            }
//        }, 3000);

    }

    private void setting() {
        Random random = new Random();
        int checkarr[] = new int[16];
        //중복숫자 안들어가도록 체크하기위해 만든 array.

        for (int i = 0; i < 16; ) {
            boolean check = false;
            int num = random.nextInt(20) + 2; //2~22
            for (int j = 0; j < checkarr.length; j++) {
                if (checkarr[j] == num) {
                    check = false;
                    break;
                } else check = true;
            }

            if (check) {
                checkarr[i] = num;
                Button bt = (Button) findViewById(R.id.bt1 + i);

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


        int[] numarr = new int[10];
        temp = 0;
        for (int i = 0; i < 16; i++) {
            Button bt = (Button) findViewById(R.id.bt1 + i);
            //Character.isDigit -> if CharAt(0) is num TRUE , else False
            if (Character.isDigit(bt.getText().toString().charAt(0))) {
                numarr[temp] = Integer.parseInt(bt.getText().toString());
                temp++;
            } else continue;
        }

        //버튼에 있는 숫자를 배열에 저장해둔뒤
        // 배열을 가지고 모든 답을 구한뒤 그 중에 문제에 대한 정답 하나를 뽑아온다.
        //정답으로 맞춰야 될 숫자.
        answer = calc(numarr);

        TextView quiztext = (TextView) findViewById(R.id.quiztext);
        quiztext.setText(String.valueOf(answer));
    }

    private static long calc(int[] numarr) {
        ArrayList<Long> answerArr = new ArrayList<>();
        char[] oper = new char[]{'+', '-', '*', '/', '%', '^'};
        for (int i = 0; i < oper.length; i++) {
            for (int j = 0; j < numarr.length; j++) {
                for (int k = 0; k < numarr.length; k++) {
                    if (j == k) continue;
                    else {
                        switch (oper[i]) {
                            case '+':
                                answerArr.add((long) (numarr[j] + numarr[k]));
                                answerArr.add((long) (numarr[k] + numarr[j]));
                                break;
                            case '-':
                                answerArr.add((long) (numarr[j] - numarr[k]));
                                answerArr.add((long) (numarr[k] - numarr[j]));
                                break;
                            case '*':
                                answerArr.add((long) (numarr[j] * numarr[k]));
                                answerArr.add((long) (numarr[k] * numarr[j]));
                                break;
                            case '%':
                                answerArr.add((long) (numarr[j] % numarr[k]));
                                answerArr.add((long) (numarr[k] % numarr[j]));
                                break;
                            case '/':
                                answerArr.add((long) (numarr[j] / numarr[k]));
                                answerArr.add((long) (numarr[k] / numarr[j]));
                                break;
                            case '^': {
                                double a, b;
                                a = (double) numarr[j];
                                b = (double) numarr[k];
                                if ((long) Math.pow(a, b) < 2100000000)
                                    answerArr.add((long) Math.pow(a, b));

                                if ((long) Math.pow(b, a) < 2100000000)
                                    answerArr.add((long) Math.pow(b, a));

                                break;
                            }
                        }
                    }
                }
            }
        }

        Random random = new Random();
        //temp 는 어디에 있는 숫자를 뽑아올지 위치를 나타내는 숫자
        //temp2 는 temp위치에 있는 숫자
        int temp = random.nextInt(answerArr.size());
        long temp2 = answerArr.get(temp);

        //정답으로 나오는 숫자가 클릭할 수 있는 숫자랑 똑같은 숫자가 나오지 않도록
        ArrayList<Long> numarrlist = new ArrayList<>();
        for (long temp3 : numarr) {
            numarrlist.add(temp3);
        }

        while (temp2 == 0 || temp2 == 1 || numarrlist.contains(temp2)) {
            temp = random.nextInt(answerArr.size());
            temp2 = answerArr.get(temp);
        }

        return temp2;
    }

    public void onClick(View view) {
        TextView resulttext = (TextView) findViewById(R.id.resultText);
        //계산식 보여주는 tv
        TextView resulttext2 = (TextView) findViewById(R.id.resultText2);
        //정답인지 아닌지 안내문구 보여주는 tv

        Button bt = (Button) view;
        calStr += bt.getText().toString() + " ";
        //계산식

        resulttext.setText(calStr);
        count--;

        if (count == 0) {
            //계산.
            String[] calarr = new String[3];
            calarr = calStr.split(" ");

            if (Character.isDigit(calarr[0].charAt(0)) && !Character.isDigit(calarr[1].charAt(0)) && Character.isDigit(calarr[2].charAt(0))) {
                resulttext.setText(calStr);
                long temp = 0;
                switch (calarr[1]) {
                    case "+":
                        temp = Integer.parseInt(calarr[0]) + Integer.parseInt(calarr[2]);
                        break;
                    case "-":
                        temp = Integer.parseInt(calarr[0]) - Integer.parseInt(calarr[2]);
                        break;
                    case "*":
                        temp = Integer.parseInt(calarr[0]) * Integer.parseInt(calarr[2]);
                        break;
                    case "%":
                        temp = Integer.parseInt(calarr[0]) % Integer.parseInt(calarr[2]);
                        break;
                    case "/":
                        temp = Integer.parseInt(calarr[0]) / Integer.parseInt(calarr[2]);
                        break;
                    case "^": {
                        double a, b;
                        a = Double.parseDouble(calarr[0]);
                        b = Double.parseDouble(calarr[2]);
                        if ((long) Math.pow(a, b) > 2100000000) {
                            resulttext.setText("X X X 범위초과 X X X");
                        } else {
                            temp = (long) Math.pow(a, b);
                        }
                        break;
                    }
                }
                if (temp == answer) {
                    resulttext.setText(calStr + " = " + String.valueOf(temp));
                    resulttext2.setText("정답입니다");
                    setting();
                } else {
                    resulttext.setText(calStr + " = ?");
                    resulttext2.setText("오답입니다");
                }

            } else {
                resulttext.setText("X X X 잘못된 식 X X X");
            }
            calStr = "";
            count = 3;
        }
    }
}

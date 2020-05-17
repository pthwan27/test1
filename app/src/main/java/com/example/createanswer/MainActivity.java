package com.example.createanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    static char[] operarr = new char[]{'+', '-', '*', '/', '%', '^'};  //6개의 연산자

    int count = 3; //계산식을 완성했을때 계산이 되도록 ex) 1 + 3 누르면 계산이됌.
    String calStr = ""; //계산식 저장
    Long answer;//맞춰야할 정답

    String[] arr44 = new String[16];
    //4*4, 총 16개가 저장되어있는 배열

    int playercheck; //2명의 플레이어가 있을때 누구의 순서인지 알기위해
    int player1score = 3; // 3점따면 승리하도록 설정
    int player2score = 3;
    String player1 = "", player2 = ""; //Login activity에서 받아온 플레이어 이름.

    //player1,2 이름과 점수 표시하는 곳
    TextView P1text;
    TextView P2text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        //누가 먼저할지 정함.
        playercheck = random.nextInt();


        setting();

        Intent intent = getIntent();
        player1 = intent.getStringExtra("P1");
        player2 = intent.getStringExtra("P2");

        P1text = (TextView) findViewById(R.id.p1text);
        P2text = (TextView) findViewById(R.id.p2text);
        P1text.setText(player1 + " score : " + String.valueOf(player1score));
        P2text.setText(player2 + " score : " + String.valueOf(player2score));
        //3초뒤에 숫자가 안보기도록 하기위해서.
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public     void run() {
//                for (int i = 0; i < 16; i++) {
//                    Button bt = findViewById(R.id.bt1 + i);
//                    bt.setTextColor(Color.WHITE);
//                }
//            }
//        }, 3000);

    }

    //setting -> 1. 2~22숫자를 중복없이 16개의 버튼에 넣는다.
    //2. 연산자 6개를 16개 버튼중 겹치지않게 6개에 넣는다 그럼 10개의숫자버튼, 6개의 연산자 버튼으로 이뤄진 16개 버튼이 만들어짐
    //3. arr44[]에 16개의 버튼 값 저장.
    //4. 숫자만 onlyNumarr[]에 따로 저장하고 이 값을 calc에 보내 문제로 쓸 답 한개를 뽑아온다.
    private void setting() {
        Random random = new Random();
        //중복숫자 안들어가도록 체크하기위해 만든 array.
        int checkarr[] = new int[16];


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
                bt.setEnabled(true);
                bt.setText(String.valueOf(num));
                i++;
            } else {
            }
        }

        int temp = 0;

        operarr = new char[]{'+', '-', '*', '/', '%', '^'};  //6개의 연산자

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
                bt.setText(String.valueOf(operarr[temp]));
                temp++;
            } else {
            }
        }

        for (int i = 0; i < arr44.length; i++) {
            Button bt = (Button) findViewById(R.id.bt1 + i);
            arr44[i] = bt.getText().toString();
        }

        int[] onlyNumarr = new int[10];
        temp = 0;
        for (int i = 0; i < 16; i++) {
            Button bt = (Button) findViewById(R.id.bt1 + i);
            //Character.isDigit -> if CharAt(0) is num TRUE , else False
            if (Character.isDigit(bt.getText().toString().charAt(0))) {
                onlyNumarr[temp] = Integer.parseInt(bt.getText().toString());
                temp++;
            } else continue;
        }

        //버튼에 있는 숫자를 배열에 저장해둔뒤
        // 배열을 가지고 모든 답을 구한뒤 그 중에 문제에 대한 정답 하나를 뽑아온다.
        //정답으로 맞춰야 될 숫자.
        answer = calc(onlyNumarr);

        TextView quiztext = (TextView) findViewById(R.id.quiztext);
        quiztext.setText(String.valueOf(answer));
    }

    //reset -> 문제를 틀렸거나, 잘못된 식을 만들었을 경우 다시 클릭할수있게 만들기위해
    private void reset() {
        for (int i = 0; i < arr44.length; i++) {
            Button bt = (Button) findViewById(R.id.bt1 + i);
            bt.setEnabled(true);
            bt.setText(arr44[i].toString());

        }
    }

    //calc -> 숫자만있는 배열을 받아 숫자와 연산자로 계산될 수 있는 값들을 answerlist에 저장한다
    //이때 answerlist는 같은숫자 연산(ex 4 * 4), 값이 너무 큰 경우(ex 18 ^ 19)는 답으로 나오지 않도록하기위해 list에 저장하지 않는다.
    //return 값으로 문제를 보내준다.
    private static long calc(int[] onlyNumarr) {
        ArrayList<Long> answerArr = new ArrayList<>();
        operarr = new char[]{'+', '-', '*', '/', '%', '^'};
        for (int i = 0; i < operarr.length; i++) {
            for (int j = 0; j < onlyNumarr.length; j++) {
                for (int k = 0; k < onlyNumarr.length; k++) {
                    if (j == k) continue;
                    else {
                        switch (operarr[i]) {
                            case '+':
                                answerArr.add((long) (onlyNumarr[j] + onlyNumarr[k]));
                                answerArr.add((long) (onlyNumarr[k] + onlyNumarr[j]));
                                break;
                            case '-':
                                answerArr.add((long) (onlyNumarr[j] - onlyNumarr[k]));
                                answerArr.add((long) (onlyNumarr[k] - onlyNumarr[j]));
                                break;
                            case '*':
                                answerArr.add((long) (onlyNumarr[j] * onlyNumarr[k]));
                                answerArr.add((long) (onlyNumarr[k] * onlyNumarr[j]));
                                break;
                            case '%':
                                answerArr.add((long) (onlyNumarr[j] % onlyNumarr[k]));
                                answerArr.add((long) (onlyNumarr[k] % onlyNumarr[j]));
                                break;
                            case '/':
                                answerArr.add((long) (onlyNumarr[j] / onlyNumarr[k]));
                                answerArr.add((long) (onlyNumarr[k] / onlyNumarr[j]));
                                break;
                            case '^': {
                                double a, b;
                                a = (double) onlyNumarr[j];
                                b = (double) onlyNumarr[k];
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
        //array에선 contains 가 안되서 list형태로바꿔준다.
        ArrayList<Long> numarrlist = new ArrayList<>();
        for (long temp3 : onlyNumarr) {
            numarrlist.add(temp3);
        }

        while (temp2 == 0 || temp2 == 1 || numarrlist.contains(temp2)) {
            temp = random.nextInt(answerArr.size());
            temp2 = answerArr.get(temp);
        }

        return temp2;
    }

    //전역변수 calstr을 통해 계산을 하고 값이 정답과 맞는지 확인하고
    //정답이 맞을 경우 정해둔 점수를 모두 채운 플레이어가 승리하도록한다.
    public void onClick(View view) {
        TextView resulttext = (TextView) findViewById(R.id.resultText);
        //계산식 보여주는 tv
        TextView turntext = (TextView) findViewById(R.id.turntext);
        //정답인지 아닌지 안내문구 보여주는 tv

        Button bt = (Button) view;
        bt.setEnabled(false);
        calStr += bt.getText().toString() + " ";
        //계산식

        resulttext.setText(calStr);
        count--;

        //3번 눌렀을 때 계산이 되게함.
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
                            resulttext.setText("X X X excess range X X X");
                        } else {
                            temp = (long) Math.pow(a, b);
                        }
                        break;
                    }
                }
                if (temp == answer) {
                    resulttext.setText(calStr + " = " + String.valueOf(temp) + "\n correct!");
                    if (playercheck % 2 == 0) {
                        player1score--;
                        turntext.setText(player2 + " turn");
                        P1text.setText(player1 + " score : " + String.valueOf(player1score));
                    } else {
                        player2score--;
                        turntext.setText(player1 + " turn");
                        P2text.setText(player2 + " score : " + String.valueOf(player2score));
                    }
                    setting();
                } else {
                    if (playercheck % 2 == 0) {
                        turntext.setText(player2 + " turn");
                    } else {
                        turntext.setText(player1 + " turn");
                    }
                    resulttext.setText(calStr + " = ? \n incorrect");

                    reset();
                }

            } else {
                if (playercheck % 2 == 0) {
                    turntext.setText(player2 + " turn");
                } else {
                    turntext.setText(player1 + " turn");
                }
                resulttext.setText("X X X wrong calc X X X");
                reset();
            }
            calStr = "";
            count = 3;

            if (player1score == 0) {
                player1score = 3;
                player2score = 3;
                turntext.setText(player1 + "win!");

            } else if (player2score == 0) {
                player1score = 3;
                player2score = 3;
                turntext.setText(player2 + "win!");
            } else playercheck++;
        }
    }
}

package com.example.createanswer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Long answer;//맞춰야할 정답

    String[] backArr = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "+", "-", "*", "/"};

    String[] frontArr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"};

    int[] onlyNumarr = new int[12];

    int count = 3; //계산식을 완성했을때 계산이 되도록 ex) 1 + 3 누르면 계산이됌.
    String calStr = ""; //계산식 저장
    int playercheck = 0; //2명의 플레이어가 있을때 누구의 순서인지 알기위해
    int player1score = 1; // 3점따면 승리하도록 설정
    int player2score = 1;
    String player1 = "", player2 = ""; //Login activity에서 받아온 플레이어 이름.

    //player1,2 이름과 점수 표시하는 곳
    TextView P1text;
    TextView P2text;

    //뒤로가기 2번눌렀을때 종료되도록하게함
    private long backKeyPressedTime = 0;
    private Toast toast;

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
        P2text.setText(String.valueOf(player2score) + " score : " + player2);
    }

    private void setting() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int rand = random.nextInt(16);
            int rand2 = random.nextInt(16);

            String temp = frontArr[rand];
            frontArr[rand] = frontArr[rand2];
            frontArr[rand2] = temp;
        }
        for (int i = 0; i < frontArr.length; i++) {
            Button bt = findViewById(R.id.bt1 + i);
            bt.setText(frontArr[i]);
        }

        for (int i = 0; i < 10; i++) {
            int rand = random.nextInt(16);
            int rand2 = random.nextInt(16);

            String temp = backArr[rand];
            backArr[rand] = backArr[rand2];
            backArr[rand2] = temp;
        }

        for (int i = 0; i < onlyNumarr.length; i++) {
            onlyNumarr[i] = i + 2;
        }
        //   3초뒤에 숫자가 안보기도록 하기위해서.
        ////        new Handler().postDelayed(new Runnable() {
        ////            @Override
        ////            public void run() {
        ////                for (int i = 0; i < 16; i++) {
        ////                    Button bt = findViewById(R.id.bt1 + i);
        ////                    bt.setTextColor(Color.WHITE);
        ////                }
        ////            }
        ////        }, 2000);

        //버튼에 있는 숫자를 배열에 저장해둔뒤
        // 배열을 가지고 모든 답을 구한뒤 그 중에 문제에 대한 정답 하나를 뽑아온다.
        //정답으로 맞춰야 될 숫자.
        answer = calc(onlyNumarr);

        TextView quiztext = (TextView) findViewById(R.id.quiztext);
        quiztext.setText(String.valueOf(answer));
    }

    private void nextQ() {
        for (int i = 0; i < frontArr.length; i++) {
            Button bt = findViewById(R.id.bt1 + i);
            bt.setEnabled(true);
            bt.setText(frontArr[i]);
        }
        answer = calc(onlyNumarr);

        TextView quiztext = (TextView) findViewById(R.id.quiztext);
        quiztext.setText(String.valueOf(answer));
    }

    //reset -> 문제를 틀렸거나, 잘못된 식을 만들었을 경우 다시 클릭할수있게 만들기위해
    private void reset() {
        for (int i = 0; i < frontArr.length; i++) {
            Button bt = (Button) findViewById(R.id.bt1 + i);
            bt.setEnabled(true);
            bt.setText(frontArr[i]);
        }
    }

    private void disable() {
        for (int i = 0; i < 16; i++) {
            Button button = findViewById(R.id.bt1 + i);
            button.setEnabled(false);
        }
    }

    //calc -> 숫자만있는 배열을 받아 숫자와 연산자로 계산될 수 있는 값들을 answerlist에 저장한다
    //이때 answerlist는 같은숫자 연산(ex 4 * 4), 값이 너무 큰 경우(ex 18 ^ 19)는 답으로 나오지 않도록하기위해 list에 저장하지 않는다.
    //return 값으로 문제를 보내준다.
    private static long calc(int[] onlyNumarr) {
        ArrayList<Long> tempanswerArr = new ArrayList<>();
        char[] operarr = new char[]{'+', '-', '*', '/'};
        for (int i = 0; i < operarr.length; i++) {
            for (int j = 0; j < onlyNumarr.length; j++) {
                for (int k = 0; k < onlyNumarr.length; k++) {
                    if (j == k) continue;
                    else {
                        switch (operarr[i]) {
                            case '+':
                                tempanswerArr.add((long) (onlyNumarr[j] + onlyNumarr[k]));
                                tempanswerArr.add((long) (onlyNumarr[k] + onlyNumarr[j]));
                                break;
                            case '-':
                                if (((long) (onlyNumarr[j] - onlyNumarr[k])) > 0) {
                                    tempanswerArr.add((long) (onlyNumarr[j] - onlyNumarr[k]));
                                }
                                if ((long) (onlyNumarr[k] - onlyNumarr[j]) > 0) {
                                    tempanswerArr.add((long) (onlyNumarr[k] - onlyNumarr[j]));
                                }
                                break;
                            case '*':
                                tempanswerArr.add((long) (onlyNumarr[j] * onlyNumarr[k]));
                                tempanswerArr.add((long) (onlyNumarr[k] * onlyNumarr[j]));
                                break;
                            case '/':
                                if (onlyNumarr[j] % onlyNumarr[k] == 0) {
                                    tempanswerArr.add((long) (onlyNumarr[j] / onlyNumarr[k]));
                                }
                                if (onlyNumarr[k] % onlyNumarr[j] == 0) {
                                    tempanswerArr.add((long) (onlyNumarr[k] / onlyNumarr[j]));
                                }
                                break;
                        }
                    }
                }
            }
        }
        HashSet<Long> temparr = new HashSet<Long>(tempanswerArr);
        ArrayList<Long> answerArr = new ArrayList<Long>(temparr);

        Random random = new Random();
        //temp 는 어디에 있는 숫자를 뽑아올지 위치를 나타내는 숫자
        //temp2 는 temp위치에 있는 숫자
        int temp = random.nextInt(answerArr.size());
        long temp2 = answerArr.get(temp);


        //0, 1 안나오도록.
        while (temp2 == 0 || temp2 == 1) {
            temp = random.nextInt(answerArr.size());
            temp2 = answerArr.get(temp);
        }

        return temp2;
    }

    public void onClick(View view) {
        int id = 0;
        int[] btarr = new int[16];

        for (int i = 0; i < btarr.length; i++) {
            btarr[i] = R.id.bt1 + i;
        }

        TextView resulttext = (TextView) findViewById(R.id.resultText);
        //계산식 보여주는 tv
        TextView turntext = (TextView) findViewById(R.id.turntext);
        //정답인지 아닌지 안내문구 보여주는 tv

        Button bt = (Button) view;
        bt.setEnabled(false);

        for (int i = 0; i < btarr.length; i++) {
            if (view.getId() == btarr[i]) {
                id = i;
                break;
            }
        }
        bt.setText(backArr[id]);

        calStr += backArr[id] + " ";
        //계산식

        resulttext.setText(calStr);
        count--;

        //3번 눌렀을 때 계산이 되게함.
        if (count == 0) {
            //계산.
            String[] calarr = new String[3];
            calarr = calStr.split(" ");

            //isDigit 숫자인지 판별
            if (Character.isDigit(calarr[0].charAt(0)) && !Character.isDigit(calarr[1].charAt(0)) && Character.isDigit(calarr[2].charAt(0))) {
                resulttext.setText(calStr);
                long temp = 0; // 사용자 정답 임시저장
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
                    case "/":
                        if (Integer.parseInt(calarr[0]) % Integer.parseInt(calarr[2]) == 0) {
                            temp = Integer.parseInt(calarr[0]) / Integer.parseInt(calarr[2]);
                        } else
                            temp = 0;
                        break;
                }
                if (temp == answer) {
                    resulttext.setText(calStr + " = " + String.valueOf(temp) + "\n correct!");
                    if (playercheck % 2 == 0) {
                        player1score--;
                        turntext.setText(player1 + " turn");
                        P1text.setText(player1 + " score : " + String.valueOf(player1score));

                        disable();
                        //3초뒤 nextQ
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nextQ();
                            }
                        }, 2000);
                    } else {
                        player2score--;
                        turntext.setText(player2 + " turn");
                        P2text.setText(String.valueOf(player2score) + " : score  " + player2);
                        disable();
                        // 3초뒤에 nextQ
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nextQ();
                            }
                        }, 2000);
                    }
                } else {
                    if (playercheck % 2 == 0) {
                        turntext.setText(player2 + " turn");
                        playercheck++;
                    } else {
                        turntext.setText(player1 + " turn");
                        playercheck++;
                    }
                    resulttext.setText(calStr + " = ? \n 틀렸습니다");

                    disable();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                        }
                    }, 2000);
                }

            } else {
                if (playercheck % 2 == 0) {
                    turntext.setText(player2 + " turn");
                    playercheck++;
                } else {
                    turntext.setText(player1 + " turn");
                    playercheck++;
                }
                resulttext.setText("X X X 잘못된 계산식 X X X");
                disable();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, 2000);
            }
            calStr = "";
            count = 3;

            if (player1score == 0) {
                player1score = 1;
                player2score = 1;
                playercheck++;
                show(player1);
                disable();
            } else if (player2score == 0) {
                player1score = 1;
                player2score = 1;
                playercheck++;
                show(player2);
                disable();
            }
        }
    }

    void show(final String playername) {

        AlertDialog.Builder alter = new AlertDialog.Builder(this);
        alter.setCancelable(false);
        alter.setTitle("alter");
        alter.setMessage(playername + " win");
        alter.setPositiveButton("재시작", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setting();
                finish();
            }
        });
        alter.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setting();
                Intent myintent = new Intent(getApplicationContext(), LastActivity.class);
                myintent.putExtra("winner", playername);
                startActivity(myintent);
            }
        });
        alter.show();
    }
    public void onBackPressed(){
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 반번더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finishAffinity();
            System.runFinalization();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }
}

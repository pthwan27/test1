package com.example.createanswer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    String player1, player2;

    Boolean check1 = false, check2 = false;

    Button play1bt;
    Button play2bt;
    Button startbt;

    EditText play1et;
    EditText play2et;

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        play1bt = (Button) findViewById(R.id.bt1);
        play2bt = (Button) findViewById(R.id.bt2);

        play1bt.setEnabled(true);
        play2bt.setEnabled(true);


        startbt = (Button) findViewById(R.id.startbt);
        play1bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play1et = (EditText) findViewById(R.id.player1text);
                player1 = play1et.getText().toString();
                if (play1et.isEnabled() && nameCheck(player1)) {
                    play1et.setEnabled(false);
                    check1 = true;
                    play1bt.setText("cancel");
                    if (nameCheck(player2)) {
                        startbt.setEnabled(true);
                    } else {
                        startbt.setEnabled(false);
                    }
                } else if (play1et.isEnabled() && !nameCheck(player1)) {
                    Toast.makeText(getApplicationContext(), "at least 1 letters!", Toast.LENGTH_SHORT).show();
                } else if (!play1et.isEnabled() && nameCheck(player1)) {
                    play1et.setEnabled(true);
                    check1 = false;
                    play1bt.setText("ok");
                }
            }
        });
        play2bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play2et = (EditText) findViewById(R.id.player2text);
                player2 = play2et.getText().toString();
                if (play2et.isEnabled() && nameCheck(player2)) {
                    play2et.setEnabled(false);
                    check2 = true;
                    play2bt.setText("cancel");
                    if (nameCheck(player1)) {
                        startbt.setEnabled(true);
                    } else {
                        startbt.setEnabled(false);
                    }
                } else if (play2et.isEnabled() && !nameCheck(player2)) {
                    Toast.makeText(getApplicationContext(), "at least 1 letters!", Toast.LENGTH_SHORT).show();
                } else if (!play2et.isEnabled() && nameCheck(player2)) {
                    play2et.setEnabled(true);
                    check2 = false;
                    play2bt.setText("ok");
                }
            }
        });
    }

    public void onClick(View v) {
        if ((check1 && check2)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("P1", player1);
            intent.putExtra("P2", player2);
            startActivity(intent);
            check1=false; check2=false;
            play1et.setEnabled(true);
            play2et.setEnabled(true);
            play1bt.setEnabled(true);
            play1bt.setText("ok");
            play2bt.setEnabled(true);
            play2bt.setText("ok");
            startbt.setEnabled(false);

        } else {
            Toast.makeText(this, "input player1, 2 name!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void exitClick(View v){
        finishAffinity();
        System.runFinalization();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private Boolean nameCheck(String player) {
        boolean check1 = false;
        if (player != null && player.length() > 0) {
            check1 = true;
        }
        return check1;
    }

    public void onBackPressed(){
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
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
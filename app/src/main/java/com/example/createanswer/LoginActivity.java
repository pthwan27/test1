package com.example.createanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    String player1, player2;

    Boolean check1,check2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Button play1bt = (Button) findViewById(R.id.bt1);
        play1bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText play1et = (EditText) findViewById(R.id.player1text);
                player1 = play1et.getText().toString();
                if (play1et.isEnabled() && nameCheck(player1)) {
                    play1et.setEnabled(false);
                    check1 = true;
                } else if (play1et.isEnabled() && !nameCheck(player1)) {
                    Toast.makeText(getApplicationContext(), "1글자 이상으로 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if (!play1et.isEnabled() && nameCheck(player1)) {
                    play1et.setEnabled(true);
                    check1 = false;
                }
            }
        });

        Button play2bt = (Button) findViewById(R.id.bt2);
        play2bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText play2et = (EditText) findViewById(R.id.player2text);
                player2 = play2et.getText().toString();
                if (play2et.isEnabled() && nameCheck(player2)) {
                    play2et.setEnabled(false);
                    check2 = true;
                } else if (play2et.isEnabled() && !nameCheck(player2)) {
                    Toast.makeText(getApplicationContext(), "1글자 이상으로 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if (!play2et.isEnabled() && nameCheck(player2)) {
                    play2et.setEnabled(true);
                    check2 = false;
                }
            }
        });

    }

    public void onClick(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(player1, player1);
        intent.putExtra(player2, player2);
        if ((check1 && check2)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "이름을 둘 다 입력해주세요!", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean nameCheck(String player1) {
        boolean check1 = false;
        if (player1 != null && player1.length() > 0) {
            check1 = true;
        }
        return check1;
    }
}
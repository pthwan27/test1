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

    Boolean check1 = false,check2 = false;

    Button play1bt;
    Button play2bt;

    EditText play1et;
    EditText play2et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        play1bt = (Button) findViewById(R.id.bt1);
        play2bt = (Button) findViewById(R.id.bt2);


        final Button startbt = (Button) findViewById(R.id.startbt);
        play1bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play1et = (EditText) findViewById(R.id.player1text);
                player1 = play1et.getText().toString();
                if (play1et.isEnabled() && nameCheck(player1)) {
                    play1et.setEnabled(false);
                    check1 = true;
                    play1bt.setText("cancel");
                    if(play2et.isEnabled() == false){
                        startbt.setEnabled(true);
                    }else;
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
                    if(play1et.isEnabled() == false){
                        startbt.setEnabled(true);
                    }else;
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
        } else{
            Toast.makeText(this,"input player1, 2 name!!", Toast.LENGTH_SHORT).show();
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
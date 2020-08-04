package com.example.createanswer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LastActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        String playername = getIntent().getStringExtra("winner");

        TextView tv = findViewById(R.id.playertv);
        tv.setText(playername + "승리! \n 뒤로가기를 2번 누르시면 종료됩니다");
    }

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }

    public void onClick_01(View view) {
        Intent logintent = new Intent(getApplicationContext(), LoginActivity.class);
                /*새로 생성하려는 액티비티와 동일한 액티비티가 스택에 있을경우
                동일한 액티비티 위의 모든 액티비티를 종료시키고 기존 액티비티를 새로 생성된
                액티비티로 교체하는 플레그*/
        logintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logintent);

    }
}

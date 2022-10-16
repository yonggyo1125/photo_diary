package org.koreait.diary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.koreait.diary.commons.ConfirmMessage;

public class MainActivity extends AppCompatActivity {

    EditText loginUser;
    EditText loginPassword;
    Button loginBtn;
    Button findLoginUser;
    Button findLoginPassword;
    Button joinUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        loginUser = findViewById(R.id.loginUser);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        findLoginUser = findViewById(R.id.findLoginUser);
        findLoginPassword = findViewById(R.id.findLoginPassword);
        joinUser = findViewById(R.id.joinUser);

        // 로그인 버튼 클릭 처리
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String user = loginUser.getText().toString();
                    String password = loginPassword.getText().toString();
                    Resources res = getResources();
                    if (user.trim().isEmpty()) {
                        throw new RuntimeException(res.getString(R.string.requiredLoginUser));
                    }

                    if (password.trim().isEmpty()) {
                        throw new RuntimeException(res.getString(R.string.requiredLoginPassword));
                    }

                    processLogin(user, password);
                } catch (RuntimeException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
             }
        });

        // 아이디 찾기 클릭 처리
        findLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindLoginUser.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 클릭 처리
        findLoginPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindUserPassword.class);
                startActivity(intent);
            }
        });

        // 회원 가입 버튼 클릭 처리
        joinUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    /**
     * 로그인 처리
     * @param user 아이디
     * @param password 비밀번호
     */
    private void processLogin(String user, String password) {

    }
}
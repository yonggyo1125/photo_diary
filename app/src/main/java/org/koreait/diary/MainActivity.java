package org.koreait.diary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.koreait.diary.commons.AppMenus;
import org.koreait.diary.member.FindLoginPasswordFragment;
import org.koreait.diary.member.FindLoginUserFragment;
import org.koreait.diary.member.JoinUserFragment;
import org.koreait.diary.member.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private FindLoginPasswordFragment findLoginPasswordFragment;
    private FindLoginUserFragment findLoginUserFragment;
    private JoinUserFragment joinUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        /** 회원 관련 Fragment S */
        loginFragment = (LoginFragment)getSupportFragmentManager().findFragmentById(R.id.loginFragment); // 로그인
        findLoginPasswordFragment = new FindLoginPasswordFragment(); // 로그인 비밀번호 찾기
        findLoginUserFragment = new FindLoginUserFragment(); // 로그인 아이디 찾기
        joinUserFragment = new JoinUserFragment(); // 회원가입
        /** 회원 관련 Fragment E */
    }

    public void onFragmentChanged(int menu) {
        switch (menu) {
            case AppMenus.LOGIN : // 로그인
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, loginFragment).commit();
                break;
            case AppMenus.FIND_LOGIN_PASSWORD: // 로그인 비밀번호 찾기
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, findLoginPasswordFragment).commit();
                break;
            case AppMenus.FIND_LOGIN_USER:  // 로그인 아이디 찾기
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, findLoginUserFragment).commit();
                break;
            case AppMenus.JOIN_USER: // 회원가입
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, joinUserFragment);
                break;
        }
    }
}
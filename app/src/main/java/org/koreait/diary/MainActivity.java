package org.koreait.diary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.koreait.diary.commons.AppMenus;
import org.koreait.diary.commons.LoginSession;
import org.koreait.diary.member.FindLoginPasswordFragment;
import org.koreait.diary.member.FindLoginUserFragment;
import org.koreait.diary.member.JoinUserFragment;
import org.koreait.diary.member.LoginFragment;
import org.koreait.diary.member.MainFragment;

public class MainActivity extends AppCompatActivity {

    /** 메인 화면 */
    private MainFragment mainFragment;

    /** 회원 관련 S */
    private LoginFragment loginFragment;
    private FindLoginPasswordFragment findLoginPasswordFragment;
    private FindLoginUserFragment findLoginUserFragment;
    private JoinUserFragment joinUserFragment;
    /** 회원 관련 E */


    private ImageButton moreMenuBtn;
    private FrameLayout slideMenuBg;
    private LinearLayout slideMenu;
    private Animation slideOpenAnim;
    private Animation slideCloseAnim;
    private boolean isSlideAnimDone = true;
    private boolean isOpen = false;
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

        /** 메인 관련 Fragment */
        mainFragment = new MainFragment();

        // 로그인 상태라면 메인 화면으로 변경
        if (LoginSession.isLogin()) {
            onFragmentChanged(AppMenus.MAIN);
        }

        /** 더보기 메뉴 관련 S */
        moreMenuBtn = findViewById(R.id.moreMenuBtn);
        slideMenuBg = findViewById(R.id.slideMenuBg);
        slideMenu = findViewById(R.id.slideMenu);

        slideOpenAnim = AnimationUtils.loadAnimation(this, R.anim.slide_open);
        slideCloseAnim = AnimationUtils.loadAnimation(this, R.anim.slide_close);
        SlideMenuAnimationListener animListener = new SlideMenuAnimationListener();
        slideOpenAnim.setAnimationListener(animListener);
        slideCloseAnim.setAnimationListener(animListener);

        /** 더보기 메뉴 버튼 클릭처리 */
        moreMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideMenuOpen();
            }
        });

        /** 더보기 메뉴 닫기 버튼 클릭 처리 */
        slideMenuBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideMenuClose();
            }
        });


        /** 더보기 메뉴 관련 E */
    }


    @Override
    protected void onResume() {
        super.onResume();

        // 로그인 회원 정보 조회
        if (!LoginSession.isLogin()) {
            SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
            if (pref != null && pref.contains("memNo")) {
                long memNo = pref.getLong("memNo", 0);
                if (memNo > 0) {
                    LoginSession.setMember(this, memNo);
                }
            }
        }
    }

    public void onFragmentChanged(int menu) {
        Toast.makeText(this, "menu : " + menu, Toast.LENGTH_LONG).show();
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
            case AppMenus.MAIN:  // 메인화면
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mainFragment);
                break;
        }
    }

    /**
     * 더보기 메뉴 열기
     *
     */
    public void slideMenuOpen() {
        if (!isSlideAnimDone) {
            return;
        }
        isOpen = true;
        slideMenu.setVisibility(View.VISIBLE);
        slideMenu.startAnimation(slideOpenAnim);


    }

    /**
     * 더보기 메뉴 닫기
     *
     */
    public void slideMenuClose() {
        if (!isSlideAnimDone) {
            return;
        }
        isOpen = false;
        slideMenu.startAnimation(slideCloseAnim);
        slideMenu.setVisibility(View.INVISIBLE);

    }

    private class SlideMenuAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            isSlideAnimDone = false;
            if (isOpen) {
                slideMenuBg.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isSlideAnimDone = true;
            if (!isOpen) {
                slideMenuBg.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }
}
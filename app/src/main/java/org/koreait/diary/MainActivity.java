package org.koreait.diary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.koreait.diary.commons.AppMenus;
import org.koreait.diary.commons.LoginSession;
import org.koreait.diary.member.FindLoginPasswordFragment;
import org.koreait.diary.member.FindLoginUserFragment;
import org.koreait.diary.member.JoinUserFragment;
import org.koreait.diary.member.LoginFragment;
import org.koreait.diary.member.MainFragment;
import org.koreait.diary.member.Member;
import org.koreait.diary.member.MemberButtons;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** 메인 화면 */
    private MainFragment mainFragment;

    /** 회원 관련 S */
    private LoginFragment loginFragment;
    private FindLoginPasswordFragment findLoginPasswordFragment;
    private FindLoginUserFragment findLoginUserFragment;
    private JoinUserFragment joinUserFragment;
    /** 회원 관련 E */

    /** 읽기 쓰기 관련 S */
    private DiaryFragment diaryFragment;
    /** 읽기 쓰기 관련 E */

    private Button homeBtn;
    private ImageButton moreMenuBtn;

    private FrameLayout memberOnlyButtons; // 회원 전용 버튼 추가
    private MemberButtons memberButtons; // 회원 전용 버튼

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
        loginFragment =  new LoginFragment(); // 로그인
        findLoginPasswordFragment = new FindLoginPasswordFragment(); // 로그인 비밀번호 찾기
        findLoginUserFragment = new FindLoginUserFragment(); // 로그인 아이디 찾기
        joinUserFragment = new JoinUserFragment(); // 회원가입
        /** 회원 관련 Fragment E */

        /** 메인 관련 Fragment */
        mainFragment = new MainFragment();

        /** 일기 쓰기 Fragment */
        diaryFragment = new DiaryFragment();

        // 로그인 정보 복구 처리
        resumeLogin();

        // 로그인 상태라면 메인 화면으로 변경
        checkAccess();

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

        slideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideCloseAnim.cancel();
            }
        });

        /** 더보기 메뉴 관련 E */

        /** 홈 버튼 클릭 처리 S */
        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentChanged(AppMenus.MAIN);
            }
        });

        /** 홈 버튼 클릭 처리 E */

        /** 회원 전용 버튼 영역 */
        memberOnlyButtons = findViewById(R.id.memberOnlyButtons);
        memberButtons = new MemberButtons(MainActivity.this);

        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.CAMERA,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("허용된 권한 개수: " + permissions.size());
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("거부된 권한 개수 : " + permissions.size());
                    }
                })
                .start();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 로그인 정보 복구 처리
        resumeLogin();

        // 로그인 상태라면 메인 화면으로 변경
        checkAccess();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 로그인 정보 복구 처리
        resumeLogin();

        // 로그인 상태라면 메인 화면으로 변경
        checkAccess();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, joinUserFragment).commit();
                break;
            case AppMenus.MAIN:  // 메인화면
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mainFragment).commit();
                break;
            case AppMenus.WRITE_DIARY : // 읽기 쓰기
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, diaryFragment).commit();
                break;

        }

        if (menu != AppMenus.MAIN && memberOnlyButtons != null) {
            memberOnlyButtons.removeAllViews();
        }
    }

    // 로그인 정보 복구 처리
    private void resumeLogin() {
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

    /**
     * 접속 권한 체크
     *
     * 로그인을 한 경우는 메인페이지, 아닌 경우는 로그인 페이지로 이동
     */
    public void checkAccess() {
        if (memberOnlyButtons == null) {
            memberOnlyButtons = findViewById(R.id.memberOnlyButtons);
        }

        if (memberButtons == null) {
            memberButtons = new MemberButtons(getApplicationContext());
        }

        if (LoginSession.isLogin()) {
            onFragmentChanged(AppMenus.MAIN);

            //  로그인한 경우 하단에 로그아웃, 마이페이지 버튼 추가
            memberOnlyButtons.addView(memberButtons);
        } else {
            onFragmentChanged(AppMenus.LOGIN);
            memberOnlyButtons.removeView(memberButtons);
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
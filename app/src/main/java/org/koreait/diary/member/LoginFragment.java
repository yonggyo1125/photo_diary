package org.koreait.diary.member;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.koreait.diary.MainActivity;
import org.koreait.diary.R;
import org.koreait.diary.commons.AppMenus;
import org.koreait.diary.commons.LoginSession;

/**
 * 로그인 페이지
 *
 */
public class LoginFragment extends Fragment {
    private EditText loginUser;
    private EditText loginPassword;
    private Button loginBtn;
    private Button findLoginUser;
    private Button findLoginPassword;
    private Button joinUser;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_login, container, false);

        loginUser = viewGroup.findViewById(R.id.loginUser);
        loginPassword = viewGroup.findViewById(R.id.loginPassword);
        loginBtn = viewGroup.findViewById(R.id.loginBtn);
        findLoginUser = viewGroup.findViewById(R.id.findLoginUser);
        findLoginPassword = viewGroup.findViewById(R.id.findLoginPassword);
        joinUser = viewGroup.findViewById(R.id.joinUser);

        mainActivity = (MainActivity)getActivity();

        /** 로그인 버튼 클릭 처리 S */
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

                    mainActivity.onFragmentChanged(AppMenus.MAIN);

                } catch (RuntimeException e) {
                    Toast.makeText(mainActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        /** 로그인 버튼 클릭 처리 E */

        /** 아이디 찾기 버튼 클릭 처리 S */
        findLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onFragmentChanged(AppMenus.FIND_LOGIN_USER);
            }
        });
        /** 아이디 찾기 버튼 클릭 처리 E */

        /** 비밀번호 찾기 버튼 클릭 처리 S */
        findLoginPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onFragmentChanged(AppMenus.FIND_LOGIN_PASSWORD);
            }
        });
        /** 비밀번호 찾기 버튼 클릭 처리 E */

        /** 회원 가입 버튼 클릭 처리 S */
        joinUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainActivity.onFragmentChanged(AppMenus.JOIN_USER);
            }
        });
        /** 회원 가입 버튼 클릭 처리 E*/

        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();

        loginUser.setText("");
        loginPassword.setText("");


    }

    /**
     * 로그인 처리
     * @param user 아이디
     * @param password 비밀번호
     */
    private void processLogin(String user, String password) {
        if (!user.equals("user01") || !password.equals("123456")) {
            throw new RuntimeException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        /** 로그인 처리 S */
        Member member = new Member();
        member.setMemNo(1L);
        member.setMemId(user);
        member.setMemNm("사용자01");
        member.setMobile("01000000000");

        LoginSession.setMember(member);

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("memNo", 1L);
        editor.commit();
        /** 로그인 처리 E */
    }
}
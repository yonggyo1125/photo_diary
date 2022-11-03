package org.koreait.diary.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.koreait.diary.MainActivity;
import org.koreait.diary.R;
import org.koreait.diary.commons.AppMenus;
import org.koreait.diary.commons.LoginSession;

public class MainFragment extends Fragment {

    private MainActivity mainActivity;
    private FrameLayout memberOnlyButtons; // 회원 전용 버튼 추가
    private MemberButtons memberButtons; // 회원 전용 버튼

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);

        mainActivity = (MainActivity) getActivity();

        // 접속 권한 체크
        checkAccess();
        addMemberOnlyButtons();

        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 접속 권한 체크
        checkAccess();
    }

    /**
     * 접속 권한 체크
     *
     *  로그인 하지 않은 경우 로그인 페이지로 이동
     */
    private void checkAccess() {
        if (!LoginSession.isLogin()) {
            mainActivity.onFragmentChanged(AppMenus.LOGIN);
        }
    }

    /** 회원 전용 버튼 추가 */
    private void addMemberOnlyButtons() {
        if (memberOnlyButtons == null) {
            memberOnlyButtons = mainActivity.findViewById(R.id.memberOnlyButtons);
        }

        if (memberButtons == null) {
            memberButtons = new MemberButtons(mainActivity);
        }

        memberOnlyButtons.addView(memberButtons);
    }
}
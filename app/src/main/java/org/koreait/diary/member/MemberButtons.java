package org.koreait.diary.member;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.koreait.diary.MainActivity;
import org.koreait.diary.R;
import org.koreait.diary.commons.AppMenus;
import org.koreait.diary.commons.LoginSession;

/**
 * 로그인을 한 경우 버튼 추가
 */
public class MemberButtons extends LinearLayout {

    private Button logoutBtn;
    private Button writeDiary;
    private Context context;

    public MemberButtons(Context context) {
        super(context);
        init(context);
    }

    public MemberButtons(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        MainActivity mainActivity = (MainActivity) context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.member_buttons, this, true);
        logoutBtn = viewGroup.findViewById(R.id.logoutBtn);
        writeDiary = viewGroup.findViewById(R.id.writeDiary);

        // 로그아웃 버튼 클릭 처리 S
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        // 로그아웃 버튼 클릭 처리 E

        writeDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onFragmentChanged(AppMenus.WRITE_DIARY);
            }
        });
    }

    private void logout() {
        LoginSession.logout();

        SharedPreferences pref = context.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        MainActivity mainActivity = (MainActivity) context;
        mainActivity.onFragmentChanged(AppMenus.LOGIN);
    }
}

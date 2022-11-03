package org.koreait.diary.commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.koreait.diary.member.Member;

/**
 * 로그인 회원 정보 보관
 * 
 */
public class LoginSession {
    private static Member member;


    public static void setMember(Context context, long memNo) {

    }

    public static void setMember(Member member) {
        LoginSession.member = member;
    }

    public static Member getMember() {

        return member;
    }

    public static boolean isLogin() {
        if (member != null) {
            return true;
        }

        return false;
    }

    public static void logout() {
        member = null;
    }
}

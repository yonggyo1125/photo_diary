package org.koreait.diary.member;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.koreait.diary.MainActivity;
import org.koreait.diary.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원 가입
 *
 */
public class JoinUserFragment extends Fragment {

    private EditText joinMemId;
    private EditText joinMemPw;
    private EditText joinMemPwRe;
    private EditText joinMemNm;
    private EditText joinEmail;
    private EditText joinMobile;
    private Button joinBtn;

    private  RequestQueue  requestQueue;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_join_user, container, false);

        mainActivity = (MainActivity) getActivity();

        joinMemId =viewGroup.findViewById(R.id.joinMemId);
        joinMemPw = viewGroup.findViewById(R.id.joinMemPw);
        joinMemPwRe = viewGroup.findViewById(R.id.joinMemPwRe);
        joinMemNm = viewGroup.findViewById(R.id.joinMemNm);
        joinEmail = viewGroup.findViewById(R.id.joinEmail);
        joinMobile = viewGroup.findViewById(R.id.joinMobile);
        joinBtn = viewGroup.findViewById(R.id.joinBtn);

        requestQueue = Volley.newRequestQueue(mainActivity);

        /** 회원가입 버튼 클릭  처리 */
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processJoin();
            }
        });

        return viewGroup;
    }

    /**
     * 회원 가입 처리
     *
     */
    private void processJoin() {
        String url = "http://192.168.1.200/member/join";
        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE_DATA", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPONSE_ERROR", error.toString());
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String memId = joinMemId.getText().toString();
                String memPw = joinMemPw.getText().toString();
                String memPwRe = joinMemPwRe.getText().toString();
                String memNm = joinMemNm.getText().toString();
                String email = joinEmail.getText().toString();
                String mobile = joinMobile.getText().toString();

                params.put("memId", memId);
                params.put("memPw", memPw);
                params.put("memPwRe", memPwRe);
                params.put("memNm", memNm);
                params.put("email", email);
                params.put("mobile", mobile);

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
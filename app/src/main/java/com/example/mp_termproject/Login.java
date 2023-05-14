package com.example.mp_termproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private String url = "https://cyber.gachon.ac.kr/login.php";

    TextView showResult;    //아이디, 비밀번호, 크롤링 결과값
    EditText name, password;
    Button loginBtn;
    String loginId, loginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Bundle bundle = new Bundle();
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        showResult = (TextView) findViewById(R.id.showText);
        loginBtn = (Button) findViewById(R.id.lgnBtn);

        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //입력한 아이디, 비번 가져오기
                loginId = name.getText().toString();
                loginPw = password.getText().toString();


                //로그인 시도
                new Thread() {
                    @Override
                    public void run() { //로그인 시도
                        try {
                            // 1. 전송할 폼 데이터
                            Map<String, String> data = new HashMap<>();
                            data.put("userid", loginId);
                            data.put("password", loginPw);
                            data.put("redirect", "/");

                            // 2. 로그인(POST)


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });


    }

}

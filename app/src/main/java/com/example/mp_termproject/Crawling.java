package com.example.mp_termproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Crawling extends Fragment {

    private String URL = "https://cyber.gachon.ac.kr/mod/ubboard/view.php?id=1";
    private TextView textView;  //크롤링 결과값 보여줌
    private String content;
    private Message msg;    //data 전달 class
    ValueHandler handler = new ValueHandler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.crawling, container, false);  //activity_main과 crawling 연결

        Bundle bundle = new Bundle();
        textView = (TextView) rootView.findViewById(R.id.showText);

        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = (Document) Jsoup.connect(URL).get();	//URL 웹사이트에 있는 html 코드를 다 끌어오기

                    //개요 부분 빼오기
                    Elements title = doc.select("tbody"); //끌어온 html에서 태그가 "tbody"인 값만 선택해서 빼오기
                    boolean isEmpty = title.isEmpty(); //빼온 값 null체크
                    Log.d("Tag", "isNull? : " + isEmpty); //로그캣 출력

                    if(isEmpty == false) { //null값이 아니면 크롤링 실행
                        content = title.get(0).text(); //크롤링 값 가져오기

                        bundle.putString("title", content); //bundle에 뽑아낸 결과값 담기
                        msg = handler.obtainMessage(); //handler에 결과값 추가
                        msg.setData(bundle);
                        handler.sendMessage(msg);  //그동안 넣어둔 msg를 message queue에 전달

                    }

                    /*
                    Elements부터 if문 까지가 태그를 이용해 원하는 정보를 빼오는 한 단락
                    이 단락을 여러개 추가하면 여러 정보들 가져오기 가능
                    */

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return rootView;
    }
    class ValueHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            //key값을 통해 원하는 정보를 가져와 textView에 출력하는 부분
            String value = msg.getData().getString("title");

            textView.append("[공지사항] \n" + value + "\n");    //화면에 출력
        }
    }
}

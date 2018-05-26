package com.example.trantien.appreview.mvp.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.trantien.appreview.R;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<NewsModel> list = new ArrayList<>();
        list.add(new NewsModel(null, "Đại biểu Quốc hội: Dẹp nạn bạo hành trẻ em là việc khẩn cấp"));
        list.add(new NewsModel(null, "Ấn Độ: Biểu tình biên thành bạo lực, 9 người chết"));

        this.listView = findViewById(R.id.listNews);

        this.listView.setAdapter(new NewsAdapter(list, this));
    }
}

package com.example.archer.selfdefiningwidget.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.archer.selfdefiningwidget.R;
import com.example.archer.selfdefiningwidget.ui.ToggleView;

public class MainActivity extends AppCompatActivity {

    private ToggleView toggleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //1.拿到自定义好的toggleView实例
        toggleView = (ToggleView) findViewById(R.id.ToggleView);
        //2.创建需要的方法
        toggleView.setSwitchBackgroundResource(R.drawable.switch_background);

        toggleView.setSlideButtonResource(R.drawable.slide_button);



        toggleView.setSwitchStatus(false);//设置开关的默认值。
    }
}

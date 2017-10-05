package com.dabing.learnandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import view.ChangeDatePopwindow;

public class MainActivity extends AppCompatActivity {

    private TextView endDate;
    private LinearLayout main;
    private ChangeDatePopwindow changeDatePopwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView startDate = (TextView) findViewById(R.id.start_date);
        main = (LinearLayout) findViewById(R.id.main);
        endDate = (TextView) findViewById(R.id.end_date);
        changeDatePopwindow = new ChangeDatePopwindow(this);
//        changeDatePopwindow.setDate("2019", "6", "20");
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDatePopwindow.showAtLocation(main, Gravity.BOTTOM, 0, 0);
            }
        });
        changeDatePopwindow.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day,String endYear,String endMonth,String endDay) {
                Toast.makeText(MainActivity.this,year+month+day,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//
    }
}

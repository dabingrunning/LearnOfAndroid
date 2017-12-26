package com.dabing.learnandroid;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dabing.learnandroid.bugtest.RentShopActivity;
import com.dabing.learnandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import view.ChangeDatePopwindow;
import view.DateChoosePagerFragment;

public class MainActivity extends AppCompatActivity {

    private TextView endDate;
    private LinearLayout main;
    private ChangeDatePopwindow changeDatePopwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView startDate = (TextView) findViewById(R.id.start_date);
        endDate = (TextView) findViewById(R.id.end_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DateChooseDialogFragment dateChooseDialogFragment = DateChooseDialogFragment.getInstance(DateChooseDialogFragment.POSITION_RIGHT);
//                FragmentManager supportFragmentManager = getSupportFragmentManager();
//                dateChooseDialogFragment.show(supportFragmentManager,"date");
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this,DatePagerActivity.class);
//                startActivity(intent);
//                ArrayList<String> startDateArray = new ArrayList<>();
//                startDateArray.add("2018");
//                startDateArray.add("2");
//                startDateArray.add("20");
//                DateChoosePagerFragment instance = DateChoosePagerFragment.getInstance(0,"2018-09-10","2019-08-11");
//                instance.setOnDateSelectListener(new DateChoosePagerFragment.OnDateSelectListener() {
//                    @Override
//                    public void onClick(String year, String month, String day, String endYear, String endMonth, String endDay) {
//                        startDate.setText(year+month+day);
//                        endDate.setText(endYear+endMonth+endDay);
//                        Toast.makeText(MainActivity.this,year+month+day+endYear+endMonth+endDay,Toast.LENGTH_LONG).show();
//                    }
//                });
//                FragmentManager fragmentManager = getFragmentManager();
//                instance.show(fragmentManager,"date");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RentShopActivity.class);
                startActivity(intent);
            }
        });
        main = (LinearLayout) findViewById(R.id.main);

        changeDatePopwindow = new ChangeDatePopwindow(this);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean weixinAvilible = Utils.isWeixinAvilible(MainActivity.this);
                if (weixinAvilible){
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                }
//                DateChoosePagerFragment instance = DateChoosePagerFragment.getInstance(1,"2020-07-11","2022-08-22");
//                instance.setOnDateSelectListener(new DateChoosePagerFragment.OnDateSelectListener() {
//                    @Override
//                    public void onClick(String year, String month, String day, String endYear, String endMonth, String endDay) {
//                        Toast.makeText(MainActivity.this,year+month+day+endYear+endMonth+endDay,Toast.LENGTH_LONG).show();
//                    }
//                });
//                FragmentManager fragmentManager = getFragmentManager();
//                instance.show(fragmentManager,"date");
//                changeDatePopwindow.showAtLocation(main, Gravity.BOTTOM, 0, 0);
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

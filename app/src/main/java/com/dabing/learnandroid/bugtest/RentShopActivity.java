package com.dabing.learnandroid.bugtest;

import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dabing.learnandroid.R;

import java.util.ArrayList;

/**
 * 出租商铺
 */
public class RentShopActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_shop);
        RecyclerView viewById = (RecyclerView) findViewById(R.id.filter_recycleView);
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            arrayList.add("测试"+i);
        }

        FullyLinearLayoutManager layout = new FullyLinearLayoutManager(this);
//        layout.setAutoMeasureEnabled(true);

        TestLieanerManager testLieanerManager = new TestLieanerManager(this);
        testLieanerManager.setAutoMeasureEnabled(true);

        viewById.setLayoutManager(layout);
        MyAdapter myAdapter = new MyAdapter(arrayList);
        viewById.setAdapter(myAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public ArrayList<String> datas = new ArrayList<>();
        public MyAdapter(ArrayList<String> datas) {
            this.datas = datas;
        }
        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }
        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas.get(position));
        }
        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }
        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text);
            }
        }
    }
}
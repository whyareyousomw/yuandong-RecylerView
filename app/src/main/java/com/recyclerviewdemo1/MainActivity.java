package com.recyclerviewdemo1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {


    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {


        mRecyclerView = (XRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header,
                (ViewGroup) findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(header);

        listData = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            listData.add("item" + i);
        }
        mAdapter = new MyAdapter(listData);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshing(true);

        mRecyclerView.setLoadingListener(this);
    }

    @Override
    public void onRefresh() {
        refreshTime++;
        times = 0;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                listData.clear();
                for (int i = 0; i < 15; i++) {
                    listData.add("item" + i + "after " + refreshTime + " times of refresh");
                }
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }

        }, 1000);
    }

    @Override
    public void onLoadMore() {

        if (times < 2) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for (int i = 0; i < 15; i++) {
                        listData.add("item" + (1 + listData.size()));
                    }
                    mRecyclerView.loadMoreComplete();
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for (int i = 0; i < 9; i++) {
                        listData.add("item" + (1 + listData.size()));
                    }
                    mRecyclerView.setNoMore(true);
                    mAdapter.notifyDataSetChanged();
                }
            }, 1000);
        }
        times++;
    }

}

package com.group.memoryhelper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.group.memoryhelper.R;
import com.group.memoryhelper.adapter.TaskAdapter;
import com.group.memoryhelper.db.DBDao;
import com.group.memoryhelper.db.Task;

import java.util.ArrayList;
import java.util.List;

public class RecordListActivity extends AppCompatActivity {


    ImageView ivFinish;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwRefresh;
    private TaskAdapter mAdapter;
    private List<Task> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        initView();
        init();

    }
    private void initView(){
        ivFinish = findViewById(R.id.iv_finish);
        mRecyclerView = findViewById(R.id.recyclerView);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskAdapter(RecordListActivity.this, list);
        mRecyclerView.setAdapter(mAdapter);
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK) {
            init();
        }
    }



    private void init() {
        List<Task> data = DBDao.getInstance(this).loadTask();
        list.clear();
        list.addAll(data);
        mAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }
}

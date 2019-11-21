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
import com.group.memoryhelper.adapter.ArticleAdapter;
import com.group.memoryhelper.db.Article;
import com.group.memoryhelper.db.DBDao;

import java.util.ArrayList;
import java.util.List;

public class ArticeListActivity extends AppCompatActivity {


    ImageView ivFinish;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwRefresh;
    private ArticleAdapter mAdapter;
    private List<Article> list = new ArrayList<>();
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);
        mSwRefresh = findViewById(R.id.sw_refresh);
        mRecyclerView = findViewById(R.id.recyclerView);
        ivFinish = findViewById(R.id.iv_finish);
        type =  getIntent().getStringExtra("type");
        initView();
        init();
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initView(){
        mSwRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ArticleAdapter(ArticeListActivity.this, list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ArticleAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(ArticeListActivity.this,ArticeDetailActivity.class);
                intent.putExtra("Note",list.get(position));
                startActivityForResult(intent,100);
            }

            @Override
            public void setOnItemLongClickListener(int position) {
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
        List<Article> data = DBDao.getInstance(this).loadArticleByType(type);
        list.clear();
        list.addAll(data);
        mAdapter.notifyDataSetChanged();
        mSwRefresh.setRefreshing(false);
    }
}

package com.group.memoryhelper.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.group.memoryhelper.R;
import com.group.memoryhelper.adapter.TaskAdapter;
import com.group.memoryhelper.adapter.TopAdapter;
import com.group.memoryhelper.db.Article;
import com.group.memoryhelper.db.DBDao;
import com.group.memoryhelper.db.Task;
import com.group.memoryhelper.ui.AddArticeActivity;
import com.group.memoryhelper.ui.ArticeDetailActivity;
import com.group.memoryhelper.ui.TaskDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    RecyclerView mRvTop;
    RecyclerView mRvFoot;
    SwipeRefreshLayout mRefreshLayout;
    private TopAdapter mTopAdapter;
    private List<Article> topList = new ArrayList<>();
    private TaskAdapter mTaskAdapter;
    private List<Task> list = new ArrayList<>();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        mRvTop = view.findViewById(R.id.rv_top);
        mRvFoot = view.findViewById(R.id.rv_foot);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        view.findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddArticeActivity.class));
            }
        });
        init();
        initData();
        return view;
    }

    private void initData() {
        List<Article> data = DBDao.getInstance(getActivity()).loadArticleByTopDay();
        topList.clear();
        topList.addAll(data);
        mTopAdapter.notifyDataSetChanged();

        List<Task> tasts = DBDao.getInstance(getActivity()).loadTaskByToday();
        list.clear();
        list.addAll(tasts);
        mTaskAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    private void init() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        LinearLayoutManager footManager = new LinearLayoutManager(getActivity());
        footManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvTop.setLayoutManager(footManager);
        mTopAdapter = new TopAdapter(getActivity(), topList);
        mRvTop.setAdapter(mTopAdapter);
        mTopAdapter.setOnItemClickListener(new TopAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ArticeDetailActivity.class);
                intent.putExtra("Note", topList.get(position));
                startActivityForResult(intent, 100);
            }

            @Override
            public void setOnItemLongClickListener(int position) {

            }
        });

        LinearLayoutManager bottomManager = new LinearLayoutManager(getActivity());
        footManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvFoot.setLayoutManager(bottomManager);
        mTaskAdapter = new TaskAdapter(getActivity(), list);
        mRvFoot.setAdapter(mTaskAdapter);
        mTaskAdapter.setOnItemClickListener(new TaskAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra("task", list.get(position));
                startActivityForResult(intent, 100);
            }

            @Override
            public void setOnItemLongClickListener(int position) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            initData();
        }
    }





}

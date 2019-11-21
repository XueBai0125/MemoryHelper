package com.group.memoryhelper.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.group.memoryhelper.R;
import com.group.memoryhelper.db.DBDao;
import com.group.memoryhelper.db.User;
import com.group.memoryhelper.ui.AboutUsActivity;
import com.group.memoryhelper.ui.RecordListActivity;


public class MoreFragment extends Fragment {


    TextView userName;

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container,false);
        view.findViewById(R.id.ll_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecordListActivity.class));
            }
        });
        view.findViewById(R.id.ll_des).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
            }
        });
        userName = view.findViewById(R.id.user_name);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt( "id", 0);
        User user = DBDao.getInstance(getActivity()).getUserInfoById(id);
        userName.setText(user.getName());


    }




}

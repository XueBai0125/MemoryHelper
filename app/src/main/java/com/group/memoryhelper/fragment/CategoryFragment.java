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

import com.group.memoryhelper.R;
import com.group.memoryhelper.adapter.TypeAdapter;
import com.group.memoryhelper.db.DBDao;
import com.group.memoryhelper.db.Type;
import com.group.memoryhelper.ui.AddTypeActivity;
import com.group.memoryhelper.ui.ArticeListActivity;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {


    RecyclerView recyclerView;
    private TypeAdapter mTypeAdapter;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }
    private ArrayList<Type> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container,false);
        view.findViewById(R.id.iv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AddTypeActivity.class),100);
            }
        });
        recyclerView = view.findViewById(R.id.recycleview);
        init();
        getData();
        return view;
    }
    private void getData() {
        list.clear();
        List<Type> notes = DBDao.getInstance(getContext()).loadTypes();
        list.addAll(notes);
        mTypeAdapter.notifyDataSetChanged();
    }
    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTypeAdapter = new TypeAdapter(getActivity(),list);
        recyclerView.setAdapter(mTypeAdapter);
        mTypeAdapter.setOnItemClickListener(new TypeAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent(getActivity(), ArticeListActivity.class);
                intent.putExtra("type",list.get(position).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            getData();
        }
    }
}

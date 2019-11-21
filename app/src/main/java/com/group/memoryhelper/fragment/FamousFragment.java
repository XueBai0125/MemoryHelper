package com.group.memoryhelper.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.group.memoryhelper.R;
import com.group.memoryhelper.adapter.FamousAdapter;
import com.group.memoryhelper.db.Famous;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;


public class FamousFragment extends Fragment {


    RecyclerView recyclerview;
    SwipeRefreshLayout mRefreshLayout;
    private List<Famous> mallList = new ArrayList<>();
    private FamousAdapter famousAdapter;
    public static FamousFragment newInstance() {
        FamousFragment fragment = new FamousFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_api, container,false);
        recyclerview = view.findViewById(R.id.recyclerview);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        famousAdapter = new FamousAdapter(getActivity(), mallList);
        recyclerview.setAdapter(famousAdapter);
        famousAdapter.setOnItemClickListener(new FamousAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {

            }


        });
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadTask().execute();
            }
        });
        new LoadTask().execute();
    }


    private class LoadTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            String result="";
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://andruxnet-random-famous-quotes.p.rapidapi.com/?cat=famous&count=10")
                    .get()
                    .addHeader("x-rapidapi-host", "andruxnet-random-famous-quotes.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "ff25bc3486msh3a5f4c19d7ef6e1p137cd4jsn2db3b8c8eb2e")
                    .build();

            okhttp3.Response response;
            try {
                response = client.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }




        @Override
        protected void onPostExecute(String result) {
            mRefreshLayout.setRefreshing(false);
            Gson gson = new Gson();
            Famous[] objectsArray = gson.fromJson(result, Famous[].class);
            List<Famous> list = Arrays.asList(objectsArray);
            mallList.clear();
            mallList.addAll(list);
            famousAdapter.notifyDataSetChanged();
        }


    }



}

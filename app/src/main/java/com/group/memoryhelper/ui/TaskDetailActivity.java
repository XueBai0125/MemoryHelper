package com.group.memoryhelper.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.group.memoryhelper.R;
import com.group.memoryhelper.db.Article;
import com.group.memoryhelper.db.DBDao;
import com.group.memoryhelper.db.Task;


public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener {

    int sizeSelect ;
    ImageView mIvFinish;
    TextView mTvName;
    TextView mTvContent;
    TextView mTvSize;
    TextView mTvBh;
    TextView mTvNo;
    TextView mTvAll;
    private Task task;
    private String[] size = {"9","10","11","12","13","14","15","16","17","18"};
    private Article mArticle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mIvFinish = findViewById(R.id.iv_finish);
        mTvName = findViewById(R.id.tv_name);
        mTvContent = findViewById(R.id.tv_content);
        mTvSize = findViewById(R.id.tv_size);
        mTvBh = findViewById(R.id.tv_bh);
        mTvNo = findViewById(R.id.tv_no);
        mTvAll = findViewById(R.id.tv_all);

        task = (Task) getIntent().getSerializableExtra("task");
        mArticle =  DBDao.getInstance(this).loadArticeByID(task.getArticeId()).get(0);
        mTvName.setText(mArticle.getTitle());

        if (task.getStatus().equals("Not memorized")) {
            mTvNo.setVisibility(View.VISIBLE);
            mTvBh.setVisibility(View.VISIBLE);
        } else {
            mTvNo.setVisibility(View.GONE);
            mTvBh.setVisibility(View.GONE);
        }
        mIvFinish.setOnClickListener(this);
        mTvSize.setOnClickListener(this);
        mTvBh.setOnClickListener(this);
        mTvAll.setOnClickListener(this);
        mTvNo.setOnClickListener(this);
        String content =  mArticle.getContent();
        String[] arr = content.split(" ");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 1 ){
                stringBuffer.append("  *****  ");
            }else{
                stringBuffer.append(arr[i]);
            }

        }
        mTvContent.setText(stringBuffer.toString());
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_all:
                mTvContent.setText(mArticle.getContent());
                break;

            case R.id.tv_size:
                new AlertDialog.Builder(TaskDetailActivity.this)
                        .setTitle("set font")
                        .setSingleChoiceItems(size, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sizeSelect = i;
                            }
                        })
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                if (sizeSelect>=0){
                                    int sizeL = Integer.parseInt(size[sizeSelect]);
                                    mTvContent.setTextSize(sizeL);
                                }

                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();

                break;
            case R.id.tv_bh:
                task.setStatus("Remembered");
                DBDao.getInstance(this).updateTask(task);
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.tv_no:
            case R.id.iv_finish:
                finish();
                break;
        }
    }
}

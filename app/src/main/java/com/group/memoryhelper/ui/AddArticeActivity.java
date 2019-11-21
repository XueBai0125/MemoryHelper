package com.group.memoryhelper.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.group.memoryhelper.R;
import com.group.memoryhelper.db.Article;
import com.group.memoryhelper.db.DBDao;
import com.group.memoryhelper.db.Type;

import java.util.List;


public class AddArticeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEtTitle;
    EditText mEtContent;
    TextView mBtAdd;
    private TextView tv_type;
    private View iv_finish;
    List<Type> notes;
    private String[] array;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addarticle);
        initView();
        notes = DBDao.getInstance(this).loadTypes();
       array =new String[notes.size()];
        for (int i = 0; i < notes.size(); i++) {
            array[i] = notes.get(i).getName();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            String name = data.getStringExtra("name");
            mEtTitle.setText(name);
        }
    }


    public void onToast(String msg) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    private void initView() {
        iv_finish = findViewById(R.id.iv_finish);
        mEtTitle = findViewById(R.id.et_title);
        mEtContent = findViewById(R.id.et_content);
        tv_type = findViewById(R.id.tv_type);

        iv_finish.setOnClickListener(this);
        mEtTitle.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        mBtAdd = findViewById(R.id.bt_add);
        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mEtTitle.getText().toString();
                String content = mEtContent.getText().toString();
                String type = tv_type.getText().toString();
                if (TextUtils.isEmpty(title)){
                    onToast("title");
                    return;
                }
                if (TextUtils.isEmpty(content)){
                    onToast("content");
                    return;
                }
                if (TextUtils.isEmpty(type)){
                    onToast(getString(R.string.input_category));
                    return;
                }
                Article article =  new Article();
                article.setType(type);
                article.setTitle(title);
                article.setContent(content);
                article.setStatus("Not memorized");
                article.setCreateTime("");
                long id = DBDao.getInstance(AddArticeActivity.this).insertArticle(article);
                List<Article> list = DBDao.getInstance(AddArticeActivity.this).loadArticeByID(id + "");
                onToast("success");

               finish();
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_type:
                if (notes.size()>0) {

                   new AlertDialog.Builder(this)
                           .setTitle("Choose category")
                           .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                    tv_type.setText(array[which]);
                                    dialog.dismiss();
                               }
                           }).create().show();
                }else{
                    onToast("Please go to add a category first.");
                }
                break;
            case R.id.iv_finish:
               finish();
                break;
        }
    }

}

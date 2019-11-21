package com.group.memoryhelper.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.group.memoryhelper.R;
import com.group.memoryhelper.db.DBDao;


public class LoginActivity extends AppCompatActivity {


    EditText mMobileEt;
    EditText mPwdEt;
    Button btLogin;
    TextView mRegisterTv;
    CheckBox ckPwd;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
         sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mMobileEt = findViewById(R.id.mMobileEt);
        mPwdEt = findViewById(R.id.mPwdEt);
        btLogin = findViewById(R.id.btLogin);
        mRegisterTv = findViewById(R.id.tv_register);
        mRegisterTv = findViewById(R.id.tv_register);
        ckPwd = findViewById(R.id.ck_pwd);
        init();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mMobileEt.getText().toString();
                String pwd = mPwdEt.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.input_name), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.input_pwd), Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean("ck",ckPwd.isChecked());
                edit.putString("name",name);
                edit.putString("pwd",pwd);
                edit.commit();
                int id = DBDao.getInstance(LoginActivity.this).signIn(name, pwd);
                if (id != -1) {
                    edit.putInt( "id", id);
                    edit.commit();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void init() {


        boolean ck = sharedPreferences.getBoolean("ck",false);
       ckPwd.setChecked(ck);
       if (ck){
           String name = sharedPreferences.getString("name","");
           String pwd = sharedPreferences.getString("pwd","");
           mMobileEt.setText(name);
           mPwdEt.setText(pwd);
       }
    }




}

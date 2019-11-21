package com.group.memoryhelper.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.group.memoryhelper.R;
import com.group.memoryhelper.db.DBDao;
import com.group.memoryhelper.db.User;


public class RegisterActivity extends AppCompatActivity {

    ImageView ivFinish;
    EditText etWriteName;
    EditText etWritePwd;
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ivFinish = findViewById(R.id.iv_finish);
        etWriteName = findViewById(R.id.et_write_name);
        etWritePwd = findViewById(R.id.et_write_pwd);
        btnLogin = findViewById(R.id.btn_login);
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etWriteName.getText().toString();
                String pwd = etWritePwd.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this,getString(R.string.input_name), Toast.LENGTH_SHORT).show();

                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(RegisterActivity.this,getString(R.string.input_pwd), Toast.LENGTH_SHORT).show();

                    return;
                }

                User user = new  User();
                user.setName(name);
                user.setPassword(pwd);
                if (DBDao.getInstance(RegisterActivity.this).signUp(user)){
                    Toast.makeText(RegisterActivity.this,"success", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}

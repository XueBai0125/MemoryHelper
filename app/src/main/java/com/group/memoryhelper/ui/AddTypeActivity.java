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
import com.group.memoryhelper.db.Type;

public class AddTypeActivity extends AppCompatActivity {
    ImageView ivFinish;
    EditText etName;
    Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ivFinish = findViewById(R.id.iv_finish);
        etName = findViewById(R.id.et_name);
        btnAdd = findViewById(R.id.btn_add);
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddTypeActivity.this, getString(R.string.input_category), Toast.LENGTH_SHORT).show();

                    return;
                }
                boolean flag = DBDao.getInstance(AddTypeActivity.this).insertType(new Type(name));
                if (flag) {
                    Toast.makeText(AddTypeActivity.this, "success", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddTypeActivity.this, "Please do not repeat", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}

package com.example.yongquan.autocall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PassScreen extends Activity {

    EditText editPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_screen);

        editPass = (EditText) findViewById(R.id.editPass);
    }
    public void onClick(View view){
        if(editPass.getText().toString().trim().equals("123")){
            startActivity(new Intent(this,MainActivity.class));
            Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Sai mật khẩu",Toast.LENGTH_SHORT).show();
        }
    }
}

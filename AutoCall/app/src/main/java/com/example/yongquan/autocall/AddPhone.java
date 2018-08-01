package com.example.yongquan.autocall;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.yongquan.autocall.Adapter.AdapterContact;
import com.example.yongquan.autocall.Model.Contact;

import java.util.ArrayList;

public class AddPhone extends AppCompatActivity {

    ListView lsContact;
    Button themContact;
    EditText edtNameContact,edtNumContact;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);

        sharedPreferences = getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
        themContact = (Button)findViewById(R.id.btnThemContact);
        edtNameContact = (EditText)findViewById(R.id.edt_name);
        edtNumContact = (EditText)findViewById(R.id.edt_phone);

        lsContact = (ListView)findViewById(R.id.listView);

        setListView();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        themContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNameContact.getText().toString().length()>0 && edtNumContact.getText().toString().length()>0) {
                    MyService.listContact.add(new Contact(edtNameContact.getText().toString(), edtNumContact.getText().toString()));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("contact");
                    editor.putString("contact",converStringFromArray(MyService.listContact));
                    editor.apply();
                    setListView();
                    edtNameContact.setText("");
                    edtNumContact.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void setListView(){

        if(MyService.listContact.size()>0) {
            AdapterContact adapterContact = new AdapterContact(this.getApplicationContext(), MyService.listContact);
            lsContact.setAdapter(adapterContact);
        }
    }
    public static String converStringFromArray(ArrayList<Contact> list){
        String str = "";
        for(int i=0;i<list.size();i++){
            str += list.get(i).getName() +"_"+ list.get(i).getPhone()+"__";
        }
        return  str;
    }
}

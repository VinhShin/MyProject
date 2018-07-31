package com.example.yongquan.autocall.Global;

import com.example.yongquan.autocall.Model.Contact;

import java.util.ArrayList;

public class Global_Function {
    public static ArrayList<Contact> convertStringToArray(String str){
        if(str!="") {
            ArrayList<Contact> contact = new ArrayList<Contact>();
            String[] tempArray;
            tempArray = str.split("__");
            for (int i = 0; i < tempArray.length; i++) {
                String[] temp = tempArray[i].split("_");
                if(temp.length>1) {
                    contact.add(new Contact(temp[0], temp[1]));
                }
            }

            return contact;
        }
        return null;
    }
}

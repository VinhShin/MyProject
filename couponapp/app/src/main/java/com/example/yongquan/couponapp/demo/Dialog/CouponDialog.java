package com.example.yongquan.couponapp.demo.Dialog;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yongquan.couponapp.Location.LocationAdapter;
import com.example.yongquan.couponapp.R;

import static android.R.attr.left;

public class CouponDialog extends DialogFragment{

    private View view;
    private ImageView mImageView;
    public CouponDialog() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light_Dialog);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = inflater.inflate(R.layout.fragment_user_name, container);
        mImageView = (ImageView) view.findViewById(R.id.username);
        this.view = view;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();

        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                300,
                r.getDisplayMetrics()
        );

        window.setLayout(px, px);



        window.setGravity(Gravity.CENTER);






    }

}
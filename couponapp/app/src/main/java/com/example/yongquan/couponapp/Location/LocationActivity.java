package com.example.yongquan.couponapp.Location;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.yongquan.couponapp.ListCoupon.ListCouponActivity;
import com.example.yongquan.couponapp.R;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity implements LocationView, LocationAdapter.onItemLocationClick{

    RecyclerView rvLocation;
    EditText edtSearch;
    LocationPresenter locationPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        rvLocation = (RecyclerView) findViewById(R.id.rv_location);
        edtSearch = (EditText)findViewById(R.id.edt_search);
        locationPresenter = new LocationPresenter(this);
        locationPresenter.processData();

        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        toolbar2.inflateMenu(R.menu.menu_toolbar);
        toolbar2.setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public void setItem(ArrayList<LocationModel> listData) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLocation.setLayoutManager(layoutManager);

        LocationAdapter locationAdapter = new LocationAdapter(listData);
        locationAdapter.setOnItemLocationClick(this);
        rvLocation.setAdapter(locationAdapter);
    }

    @Override
    public void onClick(){
        startActivity(new Intent(this, ListCouponActivity.class));
    }
}

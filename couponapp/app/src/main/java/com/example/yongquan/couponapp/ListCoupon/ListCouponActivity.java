package com.example.yongquan.couponapp.ListCoupon;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.yongquan.couponapp.CouponDetail.CouponDetailActivity;
import com.example.yongquan.couponapp.R;

import java.util.ArrayList;

public class ListCouponActivity extends AppCompatActivity implements ListCouponView,CouponAdapter.ItemClickListener {

    RecyclerView recyclerView;
    private ListCouponPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coupon);

        recyclerView = (RecyclerView) findViewById(R.id.rv_coupon);
        presenter = new ListCouponPresenter(this);
        presenter.processData();

        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        toolbar2.inflateMenu(R.menu.menu_toolbar);
        toolbar2.setTitle(getResources().getString(R.string.app_name));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_coupon);
        toolbar.setTitle(getResources().getString(R.string.title_toolbar_coupon));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("My title");
    }

    @Override
    public void setItem(ArrayList<CouponModel> couponModels, ArrayList<Integer> listTitle){
        CouponAdapter adapter = new CouponAdapter(this,couponModels,listTitle);
        adapter.setItemClickListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_coupon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick() {
        startActivity(new Intent(this, CouponDetailActivity.class));
    }
}

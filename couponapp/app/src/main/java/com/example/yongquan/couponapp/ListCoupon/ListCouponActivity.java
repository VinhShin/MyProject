package com.example.yongquan.couponapp.ListCoupon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yongquan.couponapp.CouponDetail.CouponDetailActivity;
import com.example.yongquan.couponapp.MainActivity;
import com.example.yongquan.couponapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListCouponActivity extends AppCompatActivity implements ListCouponView,CouponAdapter.ItemClickListener {

    RecyclerView recyclerView;
    private ListCouponPresenter presenter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<Integer> listTittle = new ArrayList<>();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_coupon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.tb_food:
                staggeredGridLayoutManager.scrollToPositionWithOffset(listTittle.get(0),20);
//                recyclerView.scrollToPosition(listTittle.get(0));
                break;
            case R.id.tb_beaty:
                staggeredGridLayoutManager.scrollToPositionWithOffset(listTittle.get(1),20);
//                recyclerView.scrollToPosition(listTittle.get(1));
                break;
            case R.id.tb_fashion:
                staggeredGridLayoutManager.scrollToPositionWithOffset(listTittle.get(2),20);
//                recyclerView.scrollToPosition(listTittle.get(2));
                break;
            case R.id.tb_life:
                staggeredGridLayoutManager.scrollToPositionWithOffset(listTittle.get(3),20);
//                recyclerView.scrollToPosition(listTittle.get(3));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick() {
        startActivity(new Intent(this, CouponDetailActivity.class));
    }

    @Override
    public void setItem(ArrayList<CouponModel> couponModels, ArrayList<Integer> listTitle){
        this.listTittle = listTitle;
        CouponAdapter adapter = new CouponAdapter(this,couponModels,listTitle);
        adapter.setItemClickListener(this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        recyclerView.setAdapter(adapter);
    }

}

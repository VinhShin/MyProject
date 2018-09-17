package com.example.yongquan.couponapp.Location;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;

import com.example.yongquan.couponapp.ListCoupon.ListCouponActivity;
import com.example.yongquan.couponapp.R;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements LocationView, LocationAdapter.onItemLocationClick{

    public final int MULTIPLE_PERMISSIONS = 10;

    public static String[] permissions = new String[]{
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    RecyclerView rvLocation;
    EditText edtSearch;
    LocationPresenter locationPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        checkPermissions();
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
    private boolean checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                result = ContextCompat.checkSelfPermission(this, p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                return false;
            }
            return true;

        } else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("YongQuan","permission be grant");
                } else {
                    String permissions1 = "";
                    for (String per : LocationActivity.permissions) {
                        permissions1 += "\n" + per;
                    }
                    // permissions list of don't granted permission
                }
                return;
            }
        }
    }
}

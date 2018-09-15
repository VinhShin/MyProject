package com.example.yongquan.couponapp.Location;

import java.util.ArrayList;

/**
 * Created by DELL on 9/11/2018.
 */

public class LocationPresenter {

    LocationView locationView;
    public LocationPresenter(LocationView locationView) {
        this.locationView = locationView;
        processData();
    }
    public void processData(){

        ArrayList<LocationModel> locationModel = new ArrayList<>();
        locationModel.add(new LocationModel("ba","bon","5"));
        locationModel.add(new LocationModel("ba","bron","4"));
        locationModel.add(new LocationModel("bsa","bo5n","5"));
        locationModel.add(new LocationModel("baa","bo7n","3"));
        locationModel.add(new LocationModel("bfa","boon","5"));
        locationModel.add(new LocationModel("bga","boin","6"));
        locationModel.add(new LocationModel("bha","boun","8"));
        locationView.setItem(locationModel);
    }
}

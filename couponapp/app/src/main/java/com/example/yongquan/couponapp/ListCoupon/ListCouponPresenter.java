package com.example.yongquan.couponapp.ListCoupon;

import com.example.yongquan.couponapp.Location.LocationModel;

import java.util.ArrayList;

/**
 * Created by DELL on 9/12/2018.
 */

public class ListCouponPresenter {

    private ListCouponView listCouponView;
    public ListCouponPresenter(ListCouponView listCouponView){
        this.listCouponView = listCouponView;
    }
    public void processData(){
        ArrayList<CouponModel> couponModels = new ArrayList<>();
        couponModels.add(new CouponModel("FOOD & DRINK","bon","5",true));
        couponModels.add(new CouponModel("ba","bron","4",false));
        couponModels.add(new CouponModel("bsa","bo5n","5",false));
        couponModels.add(new CouponModel("baa","bo7n","3",false));
        couponModels.add(new CouponModel("bfa","boon","5",false));
        couponModels.add(new CouponModel("bga","boin","6",false));
        couponModels.add(new CouponModel("bha","boun","8",false));
        couponModels.add(new CouponModel("BEAUTY","bon","5",true));
        couponModels.add(new CouponModel("ba","bron","4",false));
        couponModels.add(new CouponModel("bsa","bo5n","5",false));
        couponModels.add(new CouponModel("baa","bo7n","3",false));
        couponModels.add(new CouponModel("bfa","boon","5",false));
        couponModels.add(new CouponModel("bga","boin","6",false));
        couponModels.add(new CouponModel("bha","boun","8",false));
        couponModels.add(new CouponModel("ba","bron","4",false));
        couponModels.add(new CouponModel("FASHION & GOOD","bo5n","5",true));
        couponModels.add(new CouponModel("baa","bo7n","3",false));
        couponModels.add(new CouponModel("bfa","boon","5",false));
        couponModels.add(new CouponModel("bga","boin","6",false));
        couponModels.add(new CouponModel("bha","boun","8",false));
        couponModels.add(new CouponModel("LIFE & GOOD","bo5n","5",true));
        couponModels.add(new CouponModel("baa","bo7n","3",false));
        couponModels.add(new CouponModel("bfa","boon","5",false));
        couponModels.add(new CouponModel("bga","boin","6",false));
        couponModels.add(new CouponModel("bha","boun","8",false));
        ArrayList<Integer> listTitle = new ArrayList<>();
        for(int i = 0 ;i< couponModels.size();i++){
            if(couponModels.get(i).isTitle()){
                listTitle.add(i);
            }
        }

        listCouponView.setItem(couponModels,listTitle);
    }
}

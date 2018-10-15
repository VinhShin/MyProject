package com.yongquan.listenyourvocabularies.MainActivity;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import com.yongquan.listenyourvocabularies.Database.WorkWithDB;
import com.yongquan.listenyourvocabularies.MainActivity.Adapter.DoneAdapter;
import com.yongquan.listenyourvocabularies.MainActivity.Adapter.ListeningAdapter;
import com.yongquan.listenyourvocabularies.R;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {

    MainView mainView;

    ListeningAdapter listeningAdapter;
    DoneAdapter doneAdapter;
    public MainPresenter(MainView mainView){
        this.mainView = mainView;
    }

    public void init(Context context){
        List<VobModel> listDone = WorkWithDB.getAllVob(context);
//        List<VobModel> listDone = new ArrayList<>();
//        listDone.add(new VobModel("process","n","//","tiến trình"));
//        listDone.add(new VobModel("process","n","//","tiến trình"));
//        listDone.add(new VobModel("process","n","//","tiến trình"));
//        listDone.add(new VobModel("process","n","//","tiến trình"));

        List<VobModel> listListen = new ArrayList<>();
        listListen.add(new VobModel("process","n","//","tiến trình"));
        listListen.add(new VobModel("process","n","//","tiến trình"));
        listListen.add(new VobModel("process","n","//","tiến trình"));
        listListen.add(new VobModel("process","n","//","tiến trình"));

        doneAdapter = new DoneAdapter(context,listDone);
        listeningAdapter = new ListeningAdapter(context,listListen);
        mainView.setListenAdapter(listeningAdapter);
        mainView.setDoneAdapter(doneAdapter);
    }

    public void onNavigationClick(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
    }
    public boolean onOptionItemSelect(MenuItem item){
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return false;
    }
}

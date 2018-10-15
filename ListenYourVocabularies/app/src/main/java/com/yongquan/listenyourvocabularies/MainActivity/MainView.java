package com.yongquan.listenyourvocabularies.MainActivity;

import com.yongquan.listenyourvocabularies.MainActivity.Adapter.DoneAdapter;
import com.yongquan.listenyourvocabularies.MainActivity.Adapter.ListeningAdapter;

public interface MainView {
    public void setListenAdapter(ListeningAdapter listenAdapter);
    public void setDoneAdapter(DoneAdapter doneAdapter);
}

package com.yongquan.listenyourvocabularies.MainActivity;

public class VobModel {
    String name;
    String type;
    String pron;
    String mean;

    public VobModel(String name, String type, String pron, String mean) {
        this.name = name;
        this.type = type;
        this.pron = pron;
        this.mean = mean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}

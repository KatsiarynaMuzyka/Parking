package com.epam.bean;


import java.util.HashMap;
import java.util.Map;

public class SlotStatResponse extends Response {
//todo
    Map<String,Integer> statistic = new HashMap<>();

    public Map<String, Integer> getStatistic() {
        return statistic;
    }

    public void setStatistic(Map<String, Integer> statistic) {
        this.statistic = statistic;
    }
}

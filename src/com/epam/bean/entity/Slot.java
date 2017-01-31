package com.epam.bean.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Slot {
    private int number;
    private boolean isCovered;
    private boolean isRegular;
    private boolean isFree;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public boolean isRegular() {
        return isRegular;
    }

    public void setRegular(boolean regular) {
        isRegular = regular;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

}
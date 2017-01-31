package com.epam.bean.entity;

import java.util.*;

public class SlotReservation {
    private Date startTime;
    private Date endTime;
    private boolean isRegular;
    private boolean isCovered;
    private String regNumber;
    private Integer slotNumb;
    private Vehicle vehicle;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isRegular() {
        return isRegular;
    }

    public void setRegular(boolean regular) {
        isRegular = regular;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Integer getSlotNumb() {
        return slotNumb;
    }

    public void setSlotNumb(Integer slotNumb) {
        this.slotNumb = slotNumb;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
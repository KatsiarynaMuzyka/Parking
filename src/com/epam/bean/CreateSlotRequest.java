package com.epam.bean;


import com.epam.bean.entity.Vehicle;

public class CreateSlotRequest extends Request {
    Vehicle vehicle;
    boolean isCovered;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }
}

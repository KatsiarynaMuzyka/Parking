package com.epam.bean;

import com.epam.bean.entity.Vehicle;


public class FreeSlotsRequest extends Request {
    Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

package com.epam.bean;

import com.epam.bean.entity.Vehicle;


public class GetCurrentSlotReservationRequest extends Request {
    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

package com.epam.bean;

import com.epam.bean.entity.Slot;
import com.epam.bean.entity.Vehicle;

public class OccupySlotRequest extends Request {
    Slot slot;
    Vehicle vehicle;

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

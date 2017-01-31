package com.epam.bean;


import com.epam.bean.entity.Slot;

public class OccupySlotResponse extends Response {
    private Slot slot;

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}

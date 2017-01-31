package com.epam.bean;

import com.epam.bean.entity.Slot;

public class CreateNewSlotResponse extends Response {
    Slot newSlot;

    public Slot getNewSlot() {
        return newSlot;
    }

    public void setNewSlot(Slot newSlot) {
        this.newSlot = newSlot;
    }
}

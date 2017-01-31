package com.epam.bean;


import java.util.ArrayList;
import java.util.List;

import com.epam.bean.entity.Slot;

public class FreeSlotsResponse extends Response {
    public List<Slot> getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(List<Slot> freeSlot) {
        this.freeSlots = freeSlot;
    }

    private List<Slot> freeSlots = new ArrayList<>();
}

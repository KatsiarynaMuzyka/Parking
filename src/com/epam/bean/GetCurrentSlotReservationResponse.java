package com.epam.bean;

import com.epam.bean.entity.SlotReservation;

public class GetCurrentSlotReservationResponse extends  Response {
    private SlotReservation slotReservation = new SlotReservation();

    public SlotReservation getSlotReservation() {
        return slotReservation;
    }

    public void setSlotReservation(SlotReservation slotReservation) {
        this.slotReservation = slotReservation;
    }
}

package com.epam.service;

import java.util.List;

import com.epam.bean.entity.Slot;
import com.epam.bean.entity.SlotReservation;
import com.epam.bean.entity.Vehicle;


public interface SlotReservationService {
    List<SlotReservation> getSlotReservations();

    Slot occupy(Vehicle vehicle, Slot slot);

    Slot release(Slot slot);

    SlotReservation getSlotReservation(Slot slot);

    SlotReservation getCurrentSlotReservation(Vehicle vehicle);
}

package com.epam.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.epam.bean.entity.Slot;
import com.epam.bean.entity.SlotReservation;
import com.epam.bean.entity.Vehicle;
import com.epam.service.SlotReservationService;


public class SlotReservationServiceImplementation implements SlotReservationService {

    public List<SlotReservation> getSlotReservations() {
        return slotReservations;
    }

    private List<SlotReservation> slotReservations = new ArrayList<>();

    @Override
    public Slot occupy(Vehicle vehicle, Slot slot) {
        SlotReservation sr = new SlotReservation();
        sr.setStartTime(new Date());
        sr.setCovered(slot.isCovered());
        sr.setRegular(slot.isRegular());
        sr.setRegNumber(vehicle.getRegNumb());
        sr.setSlotNumb(slot.getNumber());
        slotReservations.add(sr);
        return slot;
    }

    @Override
    public Slot release(Slot slot) {
        for (int i = 0; i < slotReservations.size(); i++) {
            if (slotReservations.get(i).getSlotNumb().equals(slot.getNumber())) {
                slotReservations.get(i).setEndTime(new Date());
                return slot;
            }
        }
        return null;
    }

    @Override
    public SlotReservation getSlotReservation(Slot slot) {
        for (int i = 0; i < slotReservations.size(); i++) {
            if (slotReservations.get(i).getSlotNumb().equals(slot.getNumber())) {
                SlotReservation sr;
                sr = slotReservations.get(i);
                slotReservations.remove(i);
                return sr;
            }
        }
        return null;
    }

    @Override
    public SlotReservation getCurrentSlotReservation(Vehicle vehicle) {
        for (int i = 0; i < slotReservations.size(); i++) {
            if (slotReservations.get(i).getRegNumber().equals(vehicle.getRegNumb())) {
                SlotReservation sr;
                sr = slotReservations.get(i);
                return sr;
            }
        }
        return null;
    }

}
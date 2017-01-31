package com.epam.service.impl;

import java.util.*;

import com.epam.bean.entity.Slot;
import com.epam.service.SlotService;

public class SlotServiceImplementation implements SlotService {
    public List<Slot> slots = new ArrayList<>();

    @Override
    public List<Slot> getFreeSlotsForMotoCycle() {
        List<Slot> freeSlots = new ArrayList<>();
        for (Slot s : slots) {
            if ((s.isFree()) && (!s.isRegular())) {
                freeSlots.add(s);
            } else if ((s.isFree()) && (!s.isRegular())) {
                freeSlots.add(s);
            }
        }
        return freeSlots;
    }

    @Override
    public List<Slot> getFreeSlotsForCar() {
        List<Slot> freeSlots = new ArrayList<>();
        for (Slot s : slots) {
            if ((s.isFree()) && (s.isRegular())) {
                freeSlots.add(s);
            } else if ((s.isFree()) && (s.isRegular())) {
                freeSlots.add(s);
            }
        }
        return freeSlots;
    }

    @Override
    public Slot createCarSlot(boolean isCovered) {
        Slot slot = new Slot();
        slot.setCovered(isCovered);
        slot.setRegular(true);
        slot.setFree(true);
        slot.setNumber(slots.size() + 1);
        slots.add(slot);
        return slot;
    }

    @Override
    public Slot createMotoSlot(boolean isCovered) {
        Slot slot = new Slot();
        slot.setCovered(isCovered);
        slot.setRegular(false);
        slot.setFree(true);
        slot.setNumber(slots.size() + 1);
        slots.add(slot);
        return slot;
    }

    @Override
    public void setSlots() {
        for (int i = 0; i < SLOTSFORCARS; i++) {
            createCarSlot(false);
        }
        for (int i = 0; i < SLOTSFORMOTO; i++) {
            createMotoSlot(false);
        }
    }

    @Override
    public Map<String, Integer> getSlotsStatistic() {
        Map<String, Integer> slotStat = new LinkedHashMap<>();
        int slotsForCars = 0;
        int freeSlotsForCars = 0;
        int slotsForMoto = 0;
        int freeSlotsForMoto = 0;
        for (Slot slot : slots) {
            if (slot.isRegular()) {
                slotsForCars++;
            }
            if (!slot.isRegular()) {
                slotsForMoto++;
            }
            if (slot.isRegular() && slot.isFree()) {
                freeSlotsForCars++;
            }
            if (!slot.isRegular() && slot.isFree()) {
                freeSlotsForMoto++;
            }

            slotStat.put("All slots:", slots.size());
            slotStat.put("Slots for cars:", slotsForCars);
            slotStat.put("Slots for moto:", slotsForMoto);
            slotStat.put("Reserved slots for moto:", slotsForMoto - freeSlotsForMoto);
            slotStat.put("Reserved slots for cars:", slotsForCars - freeSlotsForCars);

        }
        return slotStat;
    }

    @Override
    public void setSlotFree(Slot slot) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getNumber() == slot.getNumber()) {
                slots.get(i).setFree(true);
            }
        }
    }

    @Override
    public void setSlotReserved(Slot slot) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getNumber() == slot.getNumber()) {
                slots.get(i).setFree(false);
            }
        }
    }

}

package com.epam.service;

import java.util.List;
import java.util.Map;

import com.epam.bean.entity.Slot;

public interface SlotService {
    int SLOTSFORCARS = 40;
    int SLOTSFORMOTO = 20;

    List<Slot> getFreeSlotsForMotoCycle();

    List<Slot> getFreeSlotsForCar();

    Slot createCarSlot(boolean isCovered);

    Slot createMotoSlot(boolean isCovered);

    void setSlotFree(Slot slot);

    void setSlotReserved(Slot slot);

    void setSlots();

    Map<String, Integer> getSlotsStatistic();
}

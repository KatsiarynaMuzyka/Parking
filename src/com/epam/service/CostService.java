package com.epam.service;

import com.epam.bean.entity.SlotReservation;

public interface CostService {
    Double getOccupationCost(SlotReservation slotReservation, double basePrice, double discount);
}

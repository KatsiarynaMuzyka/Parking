package com.epam.service.impl;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import com.epam.bean.entity.SlotReservation;
import com.epam.service.CostService;

public class CostServiceImplementation implements CostService {

    @Override
    public Double getOccupationCost(SlotReservation slotReservation, double basePrice, double discount) {
        double wholeTime;
        Hours hours = Hours.hoursBetween(new DateTime(slotReservation.getStartTime()),
                new DateTime(slotReservation.getEndTime()));
        wholeTime = hours.getHours();
        if (wholeTime < 1) {
            wholeTime = 1;
        }
        if ((slotReservation.isRegular()) && (slotReservation.isCovered())) {
            if (wholeTime <= 4) {
                return 1.5 * 1.2 * 1 * wholeTime * basePrice;
            }
            if (wholeTime > 4 && wholeTime <= 24) {
                return 1.2 * 1.2 * 1 * wholeTime * basePrice;
            }
            if (wholeTime > 24) {
                return 1 * 1.2 * 1 * wholeTime * basePrice;
            }
        }

        if ((slotReservation.isRegular()) && (!slotReservation.isCovered())) {
            if (wholeTime <= 4) {
                return 1.5 * 1 * 1 * wholeTime * basePrice;
            }
            if (wholeTime > 4 && wholeTime <= 24) {
                return 1.2 * 1 * 1 * wholeTime * basePrice;
            }
            if (wholeTime > 24) {
                return 1 * 1 * 1 * wholeTime * basePrice;
            }
        }
        if ((!slotReservation.isRegular()) && (slotReservation.isCovered())) {
            if (wholeTime <= 4) {
                return 1.5 * 1.2 * 0.7 * wholeTime * basePrice;
            }
            if (wholeTime > 4 && wholeTime <= 24) {
                return 1.2 * 1.2 * 0.7 * wholeTime * basePrice;
            }
            if (wholeTime > 24) {
                return 1 * 1.2 * 0.7 * wholeTime * basePrice;
            }
        }
        if ((!slotReservation.isRegular()) && (!slotReservation.isCovered())) {
            if (wholeTime <= 4) {
                return 1.5 * 1 * 0.7 * wholeTime * basePrice;
            }
            if (wholeTime > 4 && wholeTime <= 24) {
                return 1.2 * 1 * 0.7 * wholeTime * basePrice;
            }
            if (wholeTime > 24) {
                return 1 * 1 * 0.7 * wholeTime * basePrice;
            }
        }
        return null;
    }
}

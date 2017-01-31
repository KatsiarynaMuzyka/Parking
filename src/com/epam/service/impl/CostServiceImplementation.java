package com.epam.service.impl;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import com.epam.bean.entity.SlotReservation;
import com.epam.service.CostService;

public class CostServiceImplementation implements CostService {

	private static final double CAR_COEFFICIENT = 1;
	private static final double MOTO_COEFFICIENT = 0.7;
	private static final double COVERED_PARKING_SLOT_COEFFICIENT = 1.2;
	private static final double NOT_COVERED_PARKING_SLOT_COEFFICIENT = 1;
	private static final double FIRST_DURATION_COEFFICIENT = 1.5;
	private static final double SECOND_DURATION_COEFFICIENT = 1.2;
	private static final double THIRD_DURATION_COEFFICIENT = 1;

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
				return FIRST_DURATION_COEFFICIENT * COVERED_PARKING_SLOT_COEFFICIENT * CAR_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}

			if (wholeTime > 4 && wholeTime <= 24) {
				return SECOND_DURATION_COEFFICIENT * COVERED_PARKING_SLOT_COEFFICIENT * CAR_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
			if (wholeTime > 24) {
				return THIRD_DURATION_COEFFICIENT * COVERED_PARKING_SLOT_COEFFICIENT * CAR_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
		}

		if ((slotReservation.isRegular()) && (!slotReservation.isCovered())) {
			if (wholeTime <= 4) {
				return FIRST_DURATION_COEFFICIENT * NOT_COVERED_PARKING_SLOT_COEFFICIENT * CAR_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
			if (wholeTime > 4 && wholeTime <= 24) {
				return SECOND_DURATION_COEFFICIENT * NOT_COVERED_PARKING_SLOT_COEFFICIENT * CAR_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
			if (wholeTime > 24) {
				return THIRD_DURATION_COEFFICIENT * NOT_COVERED_PARKING_SLOT_COEFFICIENT * CAR_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
		}
		if ((!slotReservation.isRegular()) && (slotReservation.isCovered())) {
			if (wholeTime <= 4) {
				return FIRST_DURATION_COEFFICIENT * COVERED_PARKING_SLOT_COEFFICIENT * MOTO_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
			if (wholeTime > 4 && wholeTime <= 24) {
				return SECOND_DURATION_COEFFICIENT * COVERED_PARKING_SLOT_COEFFICIENT * MOTO_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
			if (wholeTime > 24) {
				return THIRD_DURATION_COEFFICIENT * COVERED_PARKING_SLOT_COEFFICIENT * MOTO_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
		}
		if ((!slotReservation.isRegular()) && (!slotReservation.isCovered())) {
			if (wholeTime <= 4) {
				return FIRST_DURATION_COEFFICIENT * NOT_COVERED_PARKING_SLOT_COEFFICIENT * MOTO_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
			if (wholeTime > 4 && wholeTime <= 24) {
				return SECOND_DURATION_COEFFICIENT * NOT_COVERED_PARKING_SLOT_COEFFICIENT * MOTO_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
			if (wholeTime > 24) {
				return THIRD_DURATION_COEFFICIENT * NOT_COVERED_PARKING_SLOT_COEFFICIENT * MOTO_COEFFICIENT * wholeTime
						* basePrice * calculateDiscount(discount);
			}
		}
		return null;
	}

	private double calculateDiscount(double discount) {
		return (1 - discount / 100);
	}
}

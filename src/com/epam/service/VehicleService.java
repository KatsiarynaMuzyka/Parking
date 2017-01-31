package com.epam.service;

import com.epam.bean.entity.Vehicle;

public interface VehicleService {
	
	void addVehicle(Vehicle vehicle);

	Vehicle getVehicle(String regNumb);
}

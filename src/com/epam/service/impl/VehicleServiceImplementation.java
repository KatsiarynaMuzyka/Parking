package com.epam.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.bean.entity.Vehicle;
import com.epam.service.VehicleService;

public class VehicleServiceImplementation implements VehicleService {
    List<Vehicle> vehiclesArchieve = new ArrayList<>();

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehiclesArchieve.add(vehicle);
    }

    @Override
    public Vehicle getVehicle(String regNumb) {
        for (Vehicle v : vehiclesArchieve) {
            if (v.getRegNumb().equals(regNumb)) {
                return v;
            }
        }
        return null;
    }

}

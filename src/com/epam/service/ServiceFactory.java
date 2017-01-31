package com.epam.service;


import com.epam.service.impl.CostServiceImplementation;
import com.epam.service.impl.SlotReservationServiceImplementation;
import com.epam.service.impl.SlotServiceImplementation;
import com.epam.service.impl.VehicleServiceImplementation;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private VehicleService vehicleService = new VehicleServiceImplementation();
    private SlotService slotService = new SlotServiceImplementation();
    private SlotReservationService slotReservationService = new SlotReservationServiceImplementation();
    private CostService costService = new CostServiceImplementation();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public SlotService getSlotService() {
        return slotService;
    }

    public SlotReservationService getSlotReservationService() {
        return slotReservationService;
    }

    public CostService getCostService() {
        return costService;
    }
}

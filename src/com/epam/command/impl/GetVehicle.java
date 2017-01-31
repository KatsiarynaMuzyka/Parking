package com.epam.command.impl;

import com.epam.bean.*;
import com.epam.bean.entity.SlotReservation;
import com.epam.bean.entity.Vehicle;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotReservationService;
import com.epam.service.VehicleService;


public class GetVehicle implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        GetVehicleRequest req = null;
        if (request instanceof GetVehicleRequest) {
            req = (GetVehicleRequest) request;
        } else {
            throw new CommandException("Wrong request");
        }
        ServiceFactory service = ServiceFactory.getInstance();
        VehicleService vehicleService = service.getVehicleService();

        GetVehicleResponse getVehicleResponse = new GetVehicleResponse();
        SlotReservationService slotReservationService = service.getSlotReservationService();
        try {
            for (SlotReservation sr : slotReservationService.getSlotReservations()) {
                if (sr.getRegNumber().equals(req.getRegNumber())) {
                    getVehicleResponse.setErrorStatus(true);
                    getVehicleResponse.setErrorMessage("Vehicle is already exists!");
                    return getVehicleResponse;
                }
            }

            getVehicleResponse.setVehicle(vehicleService.getVehicle(req.getRegNumber()));
        } catch (Exception e) {
            throw new CommandException();
        }
        getVehicleResponse.setErrorStatus(false);
        return getVehicleResponse;
    }
}

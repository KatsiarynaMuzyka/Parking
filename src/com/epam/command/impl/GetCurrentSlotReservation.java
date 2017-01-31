package com.epam.command.impl;

import com.epam.bean.*;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotReservationService;

public class GetCurrentSlotReservation implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        GetCurrentSlotReservationRequest req = null;

        if (request instanceof GetCurrentSlotReservationRequest) {
            req = (GetCurrentSlotReservationRequest) request;
        } else {
            throw new CommandException("Wrong request");
        }

        ServiceFactory service = ServiceFactory.getInstance();
        SlotReservationService slotReservationService = service.getSlotReservationService();
        GetCurrentSlotReservationResponse getCurrentSlotReservationResponse = new GetCurrentSlotReservationResponse();
        try {
           getCurrentSlotReservationResponse.setSlotReservation(slotReservationService.getCurrentSlotReservation(req.getVehicle()));
        } catch (Exception e) {
            throw new CommandException();
        }
        getCurrentSlotReservationResponse.setErrorStatus(false);
        return getCurrentSlotReservationResponse;
    }
}

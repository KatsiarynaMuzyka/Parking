package com.epam.command.impl;

import com.epam.bean.*;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotService;

public class ShowFreeSlots implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        FreeSlotsRequest req = null;
        if (request instanceof FreeSlotsRequest) {
            req = (FreeSlotsRequest) request;
        } else {
            throw new CommandException("Wrong request");
        }
        ServiceFactory service = ServiceFactory.getInstance();
        SlotService slotService = service.getSlotService();


        FreeSlotsResponse response = new FreeSlotsResponse();
        try {
            if (req.getVehicle().isCar()) {
                response.setFreeSlots(slotService.getFreeSlotsForCar());
            } else {
                response.setFreeSlots(slotService.getFreeSlotsForMotoCycle());
            }

        } catch (Exception e) {
            throw new CommandException();
        }
        response.setErrorStatus(false);
        return response;
    }
}
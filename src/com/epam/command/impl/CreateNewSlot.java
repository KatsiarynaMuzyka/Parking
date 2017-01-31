package com.epam.command.impl;

import com.epam.bean.*;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotService;

public class CreateNewSlot implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        CreateSlotRequest req = null;

        if (request instanceof CreateSlotRequest) {
            req = (CreateSlotRequest) request;
        } else {
            throw new CommandException("Wrong request");
        }

        ServiceFactory service = ServiceFactory.getInstance();
        SlotService slotService = service.getSlotService();
        CreateNewSlotResponse createNewSlotResponse = new CreateNewSlotResponse();
        try {

            if (req.getVehicle().isCar() && req.isCovered()) {
                createNewSlotResponse.setNewSlot(slotService.createCarSlot(true));
            }
            if (req.getVehicle().isCar() && !req.isCovered()) {
                createNewSlotResponse.setNewSlot(slotService.createCarSlot(false));
            }
            if (!req.getVehicle().isCar() && req.isCovered()) {
                createNewSlotResponse.setNewSlot(slotService.createMotoSlot(true));
            }
            if (!req.getVehicle().isCar() && !req.isCovered()) {
                createNewSlotResponse.setNewSlot(slotService.createMotoSlot(false));
            }
        } catch (Exception e) {
            throw new CommandException();
        }

        createNewSlotResponse.setErrorStatus(false);
        createNewSlotResponse.setResultMessage("Vehicle is successfully created!");
        return createNewSlotResponse;
    }
}

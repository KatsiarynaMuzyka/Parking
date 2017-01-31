package com.epam.command.impl;

import com.epam.bean.*;
import com.epam.bean.entity.Slot;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotReservationService;
import com.epam.service.SlotService;

public class OccupySlot implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        OccupySlotRequest req = null;

        if (request instanceof OccupySlotRequest) {
            req = (OccupySlotRequest) request;
        } else {
            throw new CommandException("Wrong request");
        }

        ServiceFactory service = ServiceFactory.getInstance();
        SlotReservationService slotReservationService = service.getSlotReservationService();
        SlotService slotService = service.getSlotService();
        OccupySlotResponse response = new OccupySlotResponse();
        Slot slot = new Slot();
        try {
            slot = slotReservationService.occupy(req.getVehicle(), req.getSlot());
            response.setSlot(slot);
            slotService.setSlotReserved(slot);

        } catch (Exception e) {
            throw new CommandException();
        }

        response.setErrorStatus(false);
        response.setResultMessage("Slot is occupied");
        return response;
    }
}

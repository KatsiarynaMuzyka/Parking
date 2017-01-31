package com.epam.command.impl;

import com.epam.bean.*;
import com.epam.bean.entity.Slot;
import com.epam.bean.entity.SlotReservation;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.CostService;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotReservationService;
import com.epam.service.SlotService;

public class PickUpVehicle implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        PickUpVehicleRequest req = null;
        if (request instanceof PickUpVehicleRequest) {
            req = (PickUpVehicleRequest) request;
        } else {
            throw new CommandException("Wrong request");
        }
        ServiceFactory service = ServiceFactory.getInstance();
        SlotReservationService slotReservationService = service.getSlotReservationService();
        SlotService slotService = service.getSlotService();
        CostService costService = service.getCostService();
        PickUpVehicleResponse pickUpVehicleResponse = new PickUpVehicleResponse();
        Slot slot;

        if (slotReservationService.getSlotReservations().size() == 0) {
            pickUpVehicleResponse.setErrorStatus(true);
            pickUpVehicleResponse.setErrorMessage("There are no occuped slots!");
            return pickUpVehicleResponse;
        }

        for (SlotReservation sr : slotReservationService.getSlotReservations()) {

            if (!sr.getSlotNumb().equals(req.getSlot().getNumber())) {
                pickUpVehicleResponse.setErrorStatus(true);
                pickUpVehicleResponse.setErrorMessage("This slot is not occupied!");
                return pickUpVehicleResponse;
            }
        }


        try {
            slot = slotReservationService.release(req.getSlot());
            pickUpVehicleResponse.setCost(costService.getOccupationCost(slotReservationService.getSlotReservation(slot), req.getBaseCost(), req.getDiscount()));
            slotService.setSlotFree(slot);
        } catch (Exception e) {
            throw new CommandException();
        }
        pickUpVehicleResponse.setErrorStatus(false);
        pickUpVehicleResponse.setResultMessage("Slot is released");
        return pickUpVehicleResponse;
    }
}

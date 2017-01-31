package com.epam.command.impl;

import com.epam.bean.*;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotService;

public class GetSlotStat implements Command {
    @Override
    public Response execute(Request request) throws CommandException  {
        Request req = null;

        if (request instanceof Request) {
            req =  request;
        } else {
            throw new CommandException("Wrong request");
        }

        ServiceFactory service = ServiceFactory.getInstance();
        SlotService slotService = service.getSlotService();
        SlotStatResponse response = new SlotStatResponse();
        try {
            response.setStatistic(slotService.getSlotsStatistic());

        } catch (Exception e) {
            throw new CommandException();
        }

        response.setErrorStatus(false);
        response.setResultMessage("Slot is occupied");
        return response;
    }
}

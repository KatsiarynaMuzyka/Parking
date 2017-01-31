package com.epam.command.impl;

import com.epam.bean.Request;
import com.epam.bean.Response;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.SlotService;


public class SetSlots implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        Request req = null;

        if (request instanceof Request) {
            req = request;
        } else {
            throw new CommandException("Wrong request");
        }

        ServiceFactory service = ServiceFactory.getInstance();
        SlotService slotService = service.getSlotService();
        try {
            slotService.setSlots();
        } catch (Exception e) {
            throw new CommandException();
        }

        Response response = new Response();
        response.setErrorStatus(false);
        response.setResultMessage("Vehicle is successfully registered!");
        return response;
    }
}

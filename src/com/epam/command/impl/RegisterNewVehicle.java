package com.epam.command.impl;

import com.epam.bean.RegisterNewVehicleRequest;
import com.epam.bean.Request;
import com.epam.bean.Response;
import com.epam.bean.entity.Vehicle;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.ServiceFactory;
import com.epam.service.VehicleService;

public class RegisterNewVehicle implements Command {
    @Override
    public Response execute(Request request) throws CommandException {
        RegisterNewVehicleRequest req = null;

        if (request instanceof RegisterNewVehicleRequest) {
            req = (RegisterNewVehicleRequest) request;
        } else {
            throw new CommandException("Wrong request");
        }

        ServiceFactory service = ServiceFactory.getInstance();
        VehicleService vehicleService = service.getVehicleService();
        Vehicle vehicle = new Vehicle();
        vehicle.setRegNumb(req.getRegNumb());
        vehicle.setCar(req.isCar());

        Response response = new Response();
        try {
            vehicleService.addVehicle(vehicle);
        } catch (Exception e) {
            throw new CommandException();
        }

        response.setErrorStatus(false);
        response.setResultMessage("Vehicle is successfully registered!");
        return response;
    }
}





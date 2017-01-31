package com.epam.controller;

import java.util.HashMap;
import java.util.Map;

import com.epam.command.Command;
import com.epam.command.impl.*;

public class CommandHelper {

    private final String ADD_VEHICLE = "ADD_VEHICLE";
    private final String GET_VEHICLE = "GET_VEHICLE";
    private final String CREATE_NEW_SLOT = "CREATE_NEW_SLOT";
    private final String PICK_UP = "PICK_UP_VEHICLE";
    private final String FIND_FREE_SLOT = "FIND_FREE_SLOT";
    private final String OCCUPY_SLOT = "OCCUPY_SLOT";
    private final String GET_CURRENT_SLOT_RESERVATION = "GET_CURRENT_SLOT_RESERVATION";
    private final String SHOW_STATISTIC = "SHOW_STATISTIC";
    private final String SET_SLOTS = "SET_SLOTS";

    private Map<String, Command> commands = new HashMap<String, Command>();

    public CommandHelper() {
        commands.put(ADD_VEHICLE, new RegisterNewVehicle());
        commands.put(CREATE_NEW_SLOT, new CreateNewSlot());
        commands.put(PICK_UP, new PickUpVehicle());
        commands.put(FIND_FREE_SLOT, new ShowFreeSlots());
        commands.put(GET_VEHICLE, new GetVehicle());
        commands.put(SHOW_STATISTIC, new GetSlotStat());
        commands.put(OCCUPY_SLOT, new OccupySlot());
        commands.put(GET_CURRENT_SLOT_RESERVATION, new GetCurrentSlotReservation());
        commands.put(SET_SLOTS, new SetSlots());
    }

    public Command getCommand(String commandName) {
        Command command;
        command = commands.get(commandName);
        return command;

    }

}

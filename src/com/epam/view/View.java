package com.epam.view;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.epam.bean.*;
import com.epam.bean.entity.Slot;
import com.epam.bean.entity.SlotReservation;
import com.epam.bean.entity.Vehicle;
import com.epam.controller.AddVehicleController;
import com.epam.controller.Controller;
import com.epam.service.exception.ServiceException;

public class View {
    static Scanner in = new Scanner(System.in);
    static char reaction;
    static Controller controller = new Controller();
    static boolean isCar;
    static String regNumber;
    static Vehicle vehicle = new Vehicle();
	static AddVehicleController addVehicleController = new AddVehicleController();

    public static void main(String[] args) throws IOException, ParseException, ServiceException, ClassNotFoundException {

        final String menu = "--------------------------Menu------------------------\n" +
                "|           Press 1 - Show Slot Statistic            |\n" +
                "|           Press 2 - Add vehicle                    |\n" +
                "|           Press 3 - Get current slot reservation   |\n" +
                "|           Press 4 - Pick up   		     |\n" +
                "|           Press 0 - Exit  			     |\n";

        Request setSlotsRequest = new Request();
        setSlotsRequest.setCommandName("SET_SLOTS");
        controller.doRequest(setSlotsRequest);

        while (true) {
            System.out.println(menu);
            reaction = in.nextLine().charAt(0);
            switch (reaction) {
                case '0': {
                    System.out.println("Programm completed successfully");
                    System.exit(0);
                }
                break;
                case '1': {
                    showSlotStat(controller);
                }
                break;

                case '2': {
                	
                	addVehicleController.addVehicle(controller);
                }
                break;

                case '3':
                    System.out.println("Write vehicle reg number");
                    String regNumb = in.nextLine();
                    Vehicle v = new Vehicle();
                    v.setRegNumb(regNumb);
                    getCurrentSlotReservation(v);
                    break;

                case '4':
                    pickUpVehicle();
                    break;
            }
        }
    }
    
    public static void showSlotStat(Controller controller) throws ServiceException {
        Request request = new Request();
        request.setCommandName("SHOW_STATISTIC");
        SlotStatResponse response = (SlotStatResponse) controller.doRequest(request);
        if (response.isErrorStatus() == true) {
            System.out.println(response.getErrorMessage());
        } else {
            if (response.getStatistic().size() == 0) {
                System.out.println("There are no slots");
            }
        }
        for (Map.Entry<String, Integer> entry : response.getStatistic().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static SlotReservation getCurrentSlotReservation(Vehicle v) throws ServiceException {
        GetCurrentSlotReservationRequest getCurrentSlotReservationRequest = new GetCurrentSlotReservationRequest();
        getCurrentSlotReservationRequest.setCommandName("GET_CURRENT_SLOT_RESERVATION");
        getCurrentSlotReservationRequest.setVehicle(v);

        GetCurrentSlotReservationResponse getCurrentSlotReservationResponse = (GetCurrentSlotReservationResponse) controller.doRequest(getCurrentSlotReservationRequest);

        if (getCurrentSlotReservationResponse.getSlotReservation() != null) {
            System.out.println("Slot: " + getCurrentSlotReservationResponse.getSlotReservation().getSlotNumb());
        } else {
            System.out.println("Current slot reservation was not found");
        }
        return getCurrentSlotReservationResponse.getSlotReservation();
    }

    public static void pickUpVehicle() throws ServiceException, ParseException, IOException {
        PickUpVehicleRequest pickUpVehicleRequest = new PickUpVehicleRequest();
        Slot slot = new Slot();
        System.out.println("Write slot number");
        String slotNumb = in.nextLine();
        slot.setNumber(Integer.valueOf(slotNumb));
        pickUpVehicleRequest.setCommandName("PICK_UP_VEHICLE");
        pickUpVehicleRequest.setSlot(slot);
        System.out.println("Write Base cost");
        Integer baseCost = Integer.parseInt(in.nextLine());
        System.out.println("Write discount in % ");
        Integer discount = Integer.parseInt(in.nextLine());
        pickUpVehicleRequest.setBaseCost(baseCost);
        pickUpVehicleRequest.setDiscount(discount);
        PickUpVehicleResponse pickUpVehicleResponse = (PickUpVehicleResponse) controller.doRequest(pickUpVehicleRequest);
        if (pickUpVehicleResponse.isErrorStatus()) {
            System.out.println(pickUpVehicleResponse.getErrorMessage());
            return;
        }
        System.out.println("Cost: " + pickUpVehicleResponse.getCost());
    } 
}
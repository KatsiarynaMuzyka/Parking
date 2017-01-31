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
import com.epam.controller.Controller;
import com.epam.service.exception.ServiceException;

public class View {
    static Scanner in = new Scanner(System.in);
    static char reaction;
    static Controller controller = new Controller();
    static boolean isCar;
    static String regNumber;
    static Vehicle vehicle = new Vehicle();

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
                    ShowSlotStat(controller);
                }
                break;

                case '2': {
                    GetVehicleResponse getVehicleResponse = (GetVehicleResponse) getVehicle(controller);
                    if (getVehicleResponse.isErrorStatus()) {
                        System.out.println(getVehicleResponse.getErrorMessage());
                        break;
                    }
                    if (getVehicleResponse.getVehicle() == null) {
                        while (true) {
                            System.out.println("Vehicle type: \n 1.Car \n 2.Moto");
                            char type = in.nextLine().charAt(0);
                            if (type == '1') {
                                isCar = true;
                                break;
                            }
                            if (type == '2') {
                                isCar = false;
                                break;
                            } else {
                                System.out.println("Please try again: ");
                                continue;
                            }
                        }
                        registerNewVehicle(controller, getVehicleResponse);
                    } else {
                        vehicle = getVehicleResponse.getVehicle();
                    }

                    while (true) {
                        List<Slot> freeSlots = showFreeSlots();
                        System.out.println(" 1. Occupy.\n 2. Create new slot  \n 0: Exit:");
                        reaction = in.nextLine().charAt(0);
                        if (reaction == '0')
                            break;
                        if (reaction == '1') {
                            occupySlot(freeSlots);
                            break;
                        }
                        if (reaction == '2') {
                            CreateNewSlotResponse createNewSlotResponse = createNewSlot();
                            System.out.println("Slot created. Do you want to locate your vehicle there? \n1.Yes \nAny input for exit");
                            reaction = in.nextLine().charAt(0);
                            if (reaction == '1') {
                                OccupySlotRequest occupySlotRequest = new OccupySlotRequest();
                                occupySlotRequest.setCommandName("OCCUPY_SLOT");
                                occupySlotRequest.setVehicle(vehicle);
                                occupySlotRequest.setSlot(createNewSlotResponse.getNewSlot());
                                OccupySlotResponse occupySlotResponse = (OccupySlotResponse) controller.doRequest(occupySlotRequest);
                                System.out.println("Slot: " + occupySlotResponse.getSlot().getNumber() + "was occupied");
                                break;
                            }
                        }
                    }
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
    
    public static void ShowSlotStat(Controller controller) throws ServiceException {
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

    public static GetVehicleResponse getVehicle(Controller controller) throws ServiceException, ParseException, IOException {
        GetVehicleRequest getVehicleRequest = new GetVehicleRequest();
        getVehicleRequest.setCommandName("GET_VEHICLE");
        System.out.println("Write registration number:");
        regNumber = in.nextLine();
        getVehicleRequest.setRegNumber(regNumber);
        return (GetVehicleResponse) controller.doRequest(getVehicleRequest);
    }


    public static void registerNewVehicle(Controller controller, GetVehicleResponse getVehicleResponse) throws ServiceException {
        RegisterNewVehicleRequest registerNewVehicleRequest = new RegisterNewVehicleRequest();
        registerNewVehicleRequest.setCommandName("ADD_VEHICLE");
        registerNewVehicleRequest.setCar(isCar);
        registerNewVehicleRequest.setRegNumb(regNumber);
        Response registerNewVehicleResponse = controller.doRequest(registerNewVehicleRequest);

        if (registerNewVehicleResponse.isErrorStatus()) {
            System.out.println(getVehicleResponse.getErrorMessage());
        } else {
            System.out.println("Vehicle: " + regNumber + " was added");
            vehicle.setCar(isCar);
            vehicle.setRegNumb(regNumber);
        }
    }


    public static List<Slot> showFreeSlots() throws ServiceException, ClassNotFoundException, ParseException, IOException {
        FreeSlotsRequest freeSlotsRequest = new FreeSlotsRequest();
        freeSlotsRequest.setCommandName("FIND_FREE_SLOT");
        freeSlotsRequest.setVehicle(vehicle);
        FreeSlotsResponse freeSlotsResponse = (FreeSlotsResponse) controller.doRequest(freeSlotsRequest);
        List<Slot> freeSlots = freeSlotsResponse.getFreeSlots();
        System.out.println("Free slots: ");
        StringBuilder covered = new StringBuilder();
        StringBuilder isNotCovered = new StringBuilder();
        for (Slot slot : freeSlots) {
            if (slot.isCovered()) {
                covered.append(slot.getNumber() + " ");
            } else {
                isNotCovered.append(slot.getNumber() + " ");
            }
        }
        System.out.println("Covered:" + covered.toString());
        System.out.println("Is not Covered:" + isNotCovered.toString());
        return freeSlots;
    }

    public static void occupySlot(List<Slot> freeSlots) throws ServiceException, ParseException, IOException {
        Slot sl = new Slot();
        while (true) {
            System.out.println("Slot number: ");
            Integer slot = Integer.valueOf(in.nextLine());
            for (int i = 0; i < freeSlots.size(); i++) {
                if (freeSlots.get(i).getNumber() == slot) {
                    OccupySlotRequest occupySlotRequest = new OccupySlotRequest();
                    occupySlotRequest.setCommandName("OCCUPY_SLOT");
                    occupySlotRequest.setVehicle(vehicle);
                    occupySlotRequest.setSlot(freeSlots.get(i));
                    OccupySlotResponse occupySlotResponse = (OccupySlotResponse) controller.doRequest(occupySlotRequest);
                    System.out.println("Slot " + occupySlotResponse.getSlot().getNumber() + " was occupied");
                    return;
                }
            }
            System.out.println("Incorrect input, please try again");
        }
    }

    public static CreateNewSlotResponse createNewSlot() throws ServiceException, ParseException, IOException {
        CreateSlotRequest createSlotRequest = new CreateSlotRequest();
        createSlotRequest.setCommandName("CREATE_NEW_SLOT");
        createSlotRequest.setVehicle(vehicle);
        while (true) {
            System.out.println("Choose slot type: 1.Covered 2. NoCovered");
            reaction = in.nextLine().charAt(0);
            if (reaction == '1') {
                createSlotRequest.setCovered(true);
                break;
            }
            if ((reaction == '2')) {
                createSlotRequest.setCovered(false);
                break;
            } else {
                System.out.println("Incorrect input, please try again");
            }
        }
        return (CreateNewSlotResponse) controller.doRequest(createSlotRequest);

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
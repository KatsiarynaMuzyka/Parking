package com.epam.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import com.epam.bean.CreateNewSlotResponse;
import com.epam.bean.CreateSlotRequest;
import com.epam.bean.FreeSlotsRequest;
import com.epam.bean.FreeSlotsResponse;
import com.epam.bean.GetVehicleRequest;
import com.epam.bean.GetVehicleResponse;
import com.epam.bean.OccupySlotRequest;
import com.epam.bean.OccupySlotResponse;
import com.epam.bean.RegisterNewVehicleRequest;
import com.epam.bean.Response;
import com.epam.bean.entity.Slot;
import com.epam.bean.entity.Vehicle;
import com.epam.service.exception.ServiceException;

public class AddVehicleController {

	static Scanner in = new Scanner(System.in);
	static char reaction;
	static Controller controller = new Controller();
	static boolean isCar;
	static String regNumber;
	static Vehicle vehicle = new Vehicle();


	
	public void addVehicle(Controller controller)
			throws IOException, ParseException, ServiceException, ClassNotFoundException {
		GetVehicleResponse getVehicleResponse = (GetVehicleResponse) getVehicle(controller);
		if (!getVehicleResponse.isErrorStatus()) {
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
					System.out.println(
							"Slot created. Do you want to locate your vehicle there? \n1.Yes \nAny input for exit");
					reaction = in.nextLine().charAt(0);
					if (reaction == '1') {
						OccupySlotRequest occupySlotRequest = new OccupySlotRequest();
						occupySlotRequest.setCommandName("OCCUPY_SLOT");
						occupySlotRequest.setVehicle(vehicle);
						occupySlotRequest.setSlot(createNewSlotResponse.getNewSlot());
						OccupySlotResponse occupySlotResponse = (OccupySlotResponse) controller
								.doRequest(occupySlotRequest);
						System.out.println("Slot: " + occupySlotResponse.getSlot().getNumber() + "was occupied");
						break;
					}
				}
			}
		} else {
			System.out.println(getVehicleResponse.getErrorMessage());
		}
	}

	public GetVehicleResponse getVehicle(Controller controller)
			throws ServiceException, ParseException, IOException {
		GetVehicleRequest getVehicleRequest = new GetVehicleRequest();
		getVehicleRequest.setCommandName("GET_VEHICLE");
		System.out.println("Write registration number:");
		regNumber = in.nextLine();
		getVehicleRequest.setRegNumber(regNumber);
		return (GetVehicleResponse) controller.doRequest(getVehicleRequest);
	}

	public void registerNewVehicle(Controller controller, GetVehicleResponse getVehicleResponse)
			throws ServiceException {
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

	public List<Slot> showFreeSlots()
			throws ServiceException, ClassNotFoundException, ParseException, IOException {
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

	public void occupySlot(List<Slot> freeSlots) throws ServiceException, ParseException, IOException {
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
					OccupySlotResponse occupySlotResponse = (OccupySlotResponse) controller
							.doRequest(occupySlotRequest);
					System.out.println("Slot " + occupySlotResponse.getSlot().getNumber() + " was occupied");
					return;
				}
			}
			System.out.println("Incorrect input, please try again");
		}
	}

	public CreateNewSlotResponse createNewSlot() throws ServiceException, ParseException, IOException {
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

}

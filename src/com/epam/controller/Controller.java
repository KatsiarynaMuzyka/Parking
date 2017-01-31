package com.epam.controller;

import com.epam.bean.Request;
import com.epam.bean.Response;
import com.epam.command.Command;
import com.epam.command.exception.CommandException;
import com.epam.service.exception.ServiceException;

public class Controller {
	private CommandHelper helper = new CommandHelper();
	
	public Controller(){}
	
	public Response doRequest(Request request) throws ServiceException {
		String commandName = request.getCommandName();
		Command command = helper.getCommand(commandName);
		Response response;
		try {
			response = command.execute(request);
		} catch (CommandException e) {
			response = new Response();
			response.setErrorStatus(true);
			response.setErrorMessage("ERROR!");
		}
		return response;
		
	}

}

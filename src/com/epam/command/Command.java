package com.epam.command;

import com.epam.bean.Request;
import com.epam.bean.Response;
import com.epam.command.exception.CommandException;

public interface Command {
	Response execute(Request request) throws CommandException;
}

package com.ComputerSquad.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class SayHello extends Command {

	public SayHello() {
		this.name = "SayHello";
		this.help = "It says hello to you :)";
	}

	@Override
	protected void execute(CommandEvent commandEvent) {
		commandEvent.reply("Hello There!");
	}
}

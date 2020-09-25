package com.computer_squad.command.fun.hello;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

/**
 * Command to test if the bot is working
 */
public class SayHello extends Command {

	public SayHello() {
		this.name = "hello";
		this.help = "It says hello to you :)";
	}

	@Override
	protected void execute(CommandEvent commandEvent) {
		commandEvent.reply("Hello There!");
	}
}

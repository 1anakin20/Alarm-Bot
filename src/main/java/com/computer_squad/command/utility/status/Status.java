package com.computer_squad.command.utility.status;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

/**
 * Command to test if the bot is working
 */
public class Status extends Command {

	public Status() {
		this.name = "status";
		this.help = "Replies if bot is online";
	}

	@Override
	protected void execute(CommandEvent commandEvent) {
		commandEvent.reply("Online");
	}
}

package com.ComputerSquad.commands.echo;

import com.ComputerSquad.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

/**
 * Echo command will make the bot talk. Your command is deleted.<br>
 * Syntax: ++echo [string to repeat]
 */
public class Echo extends Command {

	public Echo() {
		this.name = "echo";
		this.help = "Echos a phrase to be said by the bot";
		this.ownerCommand = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		Message message = event.getMessage();
		try {
			message.delete().queue();
		} catch (InsufficientPermissionException e) {
			e.printStackTrace();
			event.reply("Insufficient privileges. Debugging info:\n" + e);
		}

		String commandFirstPart = Main.getPrefix() + getName();
		String reply = event.getMessage().getContentRaw().replace(commandFirstPart, "");
		event.reply(reply);
	}
}

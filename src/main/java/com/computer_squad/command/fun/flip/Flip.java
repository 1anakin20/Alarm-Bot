package com.computer_squad.command.fun.flip;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Random;

public class Flip extends Command {

	public Flip() {
		this.name = "flip";
		this.help = "Chooses between to options";
		this.arguments = "option1:option2";
	}

	@Override
	protected void execute(CommandEvent event) {
		String args = event.getArgs();
		String[] options = args.split(":");
		if (options.length != 2) {
			event.reply("There aren't 2 options. option1:option2");
		}

		Random random = new Random();
		int rand = random.nextInt(2);
		event.reply("I prefer, " + options[rand]);
	}
}

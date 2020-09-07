package com.ComputerSquad.commands.ClassAlarm;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Map;

public class ClassAlarm extends Command {
	private final Clock clock;
	public ClassAlarm() {
		this.name = "alarm";
		this.arguments = "on, off";
		this.help = "Will ping everyone when it's class time";

		clock = Clock.getInstance();
	}

	@Override
	protected void execute(CommandEvent commandEvent) {
		if(commandEvent.getArgs().isEmpty()) {
			commandEvent.replyWarning("There is no arguments");
		} else {
			String[] args = commandEvent.getArgs().split("\\s+");
			if (args.length > 1) {
				// Show help commands
				commandEvent.reply("Too many arguments");
				return;
			}
			switch (args[0]) {
				case "on":
					setOn(true);
					commandEvent.reply("Alarm has been turned on");
					break;
				case "off":
					setOn(false);
					commandEvent.reply("Alarm has been turned off");
					break;
				case "times":
					commandEvent.reply(alarmTimes());
					break;
				default:
					// Argument not found
					commandEvent.reply("Argument doesn't exist");
			}
		}
	}

	private String alarmTimes() {
		JSONAlarms jsonAlarms = new JSONAlarms();
		Map<String, String> map = jsonAlarms.readJSON();
//		StringBuilder stringBuilder = new StringBuilder();
		String formatted = "";
		for(String key : map.keySet()) {
			formatted = key + " -> " + map.get(key) + "\n";
		}


		return "Week day:Hour:Minutes -> 'Class name'\n" + formatted;
	}

	private void setOn(boolean isOn) {
		clock.setOn(isOn);
	}
}

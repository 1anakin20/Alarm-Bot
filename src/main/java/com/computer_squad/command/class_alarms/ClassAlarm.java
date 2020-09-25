package com.computer_squad.command.class_alarms;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			switch (args[0]) {
				case "on":
//					setOn(true);
//					commandEvent.reply("Alarm has been turned on");
					commandEvent.reply("Not available at the moment");
					break;
				case "off":
//					setOn(false);
//					commandEvent.reply("Alarm has been turned off");
					commandEvent.reply("Not available at the moment");
					break;
				case "times":
					commandEvent.reply(alarmTimes());
					break;
				case "new":
					commandEvent.reply(setAlarm(Arrays.copyOfRange(args, 1, args.length)));
					break;
				case "remove":
					commandEvent.reply(removeAlarm(Arrays.copyOfRange(args, 1, args.length)));
					break;
				case "status":
					// Check if alarm is on or off
					commandEvent.reply(alarmStatus());
					break;
				default:
					// Argument not found
					commandEvent.reply("Argument doesn't exist");
			}
		}
	}

	private String alarmTimes() {
		Map<String, String> alarms = Clock.getInstance().getAlarms();
		if (alarms.isEmpty()) {
			return "No alarms";
		}
		StringBuilder formatted = new StringBuilder();
		for(Map.Entry<String, String> entry : alarms.entrySet()) {
			formatted.append(entry.getValue()).append(" -> ").append(entry.getKey()).append("\n");
		}
		return formatted.toString();
	}

	private String setAlarm(String[] args) {
		if(args.length != 2) {
			return "There should be 2 arguments";
		}

		// Check if the format is correct "week day:HH:MM"
		Pattern pattern = Pattern.compile("[a-zA-Z]+:\\d\\d:\\d\\d");
		Matcher matcher = pattern.matcher(args[0]);
		if (!matcher.matches()) {
			return "The first argument is incorrect. It should be: week day:hour:Minutes\n" +
					"Note: Week day is a string; hour is double digits in 24 hours format; Minutes is double digits\n" +
					"Example: friday:17:00";
		}
		// Each component is between ':'
		String[] components = args[0].split(":");

//		JSONAlarms jsonAlarms = new JSONAlarms();
//		jsonAlarms.saveAlarm(args[0].toLowerCase(), args[1]);
		Clock clock = Clock.getInstance();
		try {
			clock.newAlarm(components[0], Integer.parseInt(components[1]), Integer.parseInt(components[2]), args[1]);
		} catch (IllegalArgumentException e) {
			return "There is something wrong with your date.\n" +
					"Check for spelling mistakes,\n" +
					"full day name,\n" +
					"another alarm doesn't have the same name";
		}

		return "Alarm saved";
	}

	private String removeAlarm(String[] args) {
		if (args.length != 1) {
			return "There should be only 1 argument";
		}

		try {
			Clock.getInstance().removeAlarm(args[0]);
		} catch (IllegalArgumentException e) {
			return "Alarm name doesn't exist";
		}

		return "Alarm removed";
	}

	private String alarmStatus() {
		return (Clock.getInstance().isOn()) ? "Alarm is on" : "Alarm is off";
	}

	private void setOn(boolean isOn) {
		clock.setOn(isOn);
	}
}

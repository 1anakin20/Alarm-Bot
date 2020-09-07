package com.ComputerSquad.commands.ClassAlarm;

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
				case "new":
					commandEvent.reply(setAlarm(Arrays.copyOfRange(args, 1, args.length)));
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
		String formatted = "";
		for(String key : map.keySet()) {
			formatted = key + " -> " + map.get(key) + "\n";
		}

		return "Week day:Hour:Minutes -> 'Class name'\n" + formatted;
	}

	private String setAlarm(String[] args) {
		if(args.length != 2) {
			return "There should be 2 arguments";
		}

		// Check if the format is correct "week day:HH:MM"
		Pattern pattern = Pattern.compile("\\d:\\d\\d:\\d\\d");
		Matcher matcher = pattern.matcher(args[0]);
		if (!matcher.matches()) {
			return "The first argument is incorrect. It should be: 'week day':hour:Minutes\n" +
					"Note: Week day is a number; hour is double digits in 24 hours format; Minutes is double digits\n" +
					"Example: 3:17:00";
		}

		JSONAlarms jsonAlarms = new JSONAlarms();
		jsonAlarms.saveAlarm(args[0], args[1]);
		return "Alarm saved";
	}

	private void setOn(boolean isOn) {
		clock.setOn(isOn);
	}
}

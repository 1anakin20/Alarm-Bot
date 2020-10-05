package com.computer_squad.command.utilities.class_alarm;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * You can set alarms to ping everyone at a certain time of a day every week.
 * Command: ++alarm
 * Requires at least 1 argument.
 * <pre>
 * Arguments:
 * 		on, off: Not available at the moment
 * 		times: Shows list of the alarms set
 * 		new: Adds new alarm.
 * 			required arguments: DayOfWeek:hour:minutes AlarmName. Note that hour and minutes needs to be always 2 digits
 * 		remove: Removes an alarm
 * 			required arguments: alarmName
 * 		status: Shows if the alarm is on or off
 * 	</pre>
 */
public class ClassAlarm extends Command {
	private final Clock clock;
	public ClassAlarm() {
		this.name = "alarm";
		this.arguments = "on, off, times, new, remove, status";
		this.help = "Will ping everyone when it's class time";
		clock = Clock.getInstance();
	}

	@Override
	protected void execute(CommandEvent commandEvent) {
		if(commandEvent.getArgs().isEmpty()) {
			commandEvent.replyWarning("There are no arguments");
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

	/** Shows a list of the alarms
	 * @return Reply
	 */
	private String alarmTimes() {
		Map<String, Calendar> alarms = Clock.getInstance().getAlarms();
		if (alarms.isEmpty()) {
			return "No alarms";
		}
		StringBuilder formatted = new StringBuilder();
		for(Map.Entry<String, Calendar> entry : alarms.entrySet()) {
			String stringEntry = convertStringEntry(entry.getValue());
			formatted.append(stringEntry).append(" -> ").append(entry.getKey()).append("\n");
		}
		return formatted.toString();
	}

	/** Converts the calendar into a human readable date
	 * @return Readable string entry
	 */
	private String convertStringEntry(Calendar entry){
		// Get the week day, hour in 24 hour format and minutes in the hour
		String formattedWeekDay = Clock.WeekDays.values()[entry.get(Calendar.DAY_OF_WEEK)-1].toString();
		int hour = entry.get(Calendar.HOUR_OF_DAY);
		int minutes = entry.get(Calendar.MINUTE);

		// Integer with a leading 0 is removed
		String minutesString;
		if (minutes <= 9) {
			minutesString = "0" + minutes;
		} else {
			minutesString = String.valueOf(minutes);
		}

		return formattedWeekDay + " at " + hour + ":" + minutesString;
	}

	/** Set ups a new alarm
	 * @param args Command arguments
	 * @return Reply
	 */
	private String setAlarm(String[] args) {
		if(args.length != 2) {
			return "There should be 2 arguments";
		}

		// Check if the format is correct "week day:HH:MM"
		Pattern pattern = Pattern.compile("[a-zA-Z]+:\\d?\\d:\\d\\d");
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

	/** Removes the desired alarm
	 * @param args Command arguments
	 * @return Reply
	 */
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

	/** Shows if the alarm is on or off
	 * @return Reply
	 */
	private String alarmStatus() {
		return (Clock.getInstance().isOn()) ? "Alarm is on" : "Alarm is off";
	}

	// Getters and setters
	private void setOn(boolean isOn) {
		clock.setOn(isOn);
	}
}

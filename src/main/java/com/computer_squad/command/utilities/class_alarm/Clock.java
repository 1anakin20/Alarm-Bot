package com.computer_squad.command.utilities.class_alarm;

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.cron.CronSchedule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;

/**
 * Singleton holding the alarms
 */
public class Clock {
	public enum WeekDays{Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday}
	private static Clock instance = null;
	private JDA jda;
	private String channelName = "";
	private Scheduler scheduler = new Scheduler();
	private Map<String, Calendar> alarms = new HashMap<>();

	private boolean isOn = true;

	private Clock() {
	}

	/** Adds a new alarm
	 * @param weekDay Day of the week name: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
	 * @param hour Hour of the day in 24h format
	 * @param minutes Minutes of the hour
	 * @param className Name of the alarm
	 */
	public void newAlarm(String weekDay, int hour, int minutes, String className) {
		Runnable ping = () -> {
			System.out.println();
			TextChannel textChannel = jda.getTextChannelsByName(channelName, true).get(0);
			textChannel.sendMessage("@everyone " + className).queue();
		};

		String cronExpression = CronHelper.CronQuartzExpressionCreator(weekDay, hour, minutes);
		scheduler.schedule(className, ping, CronSchedule.parseQuartzCron(cronExpression));

		// format the first Weekday letter into upper case
		String formattedWeekDay;
		formattedWeekDay = weekDay.substring(0,1).toUpperCase() + weekDay.substring(1).toLowerCase();

		// Create a new Calendar that tracks the date
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK,WeekDays.valueOf(formattedWeekDay).ordinal()+1); // Weekday enum is 1 unit behind the Calendar weekday constants
		date.set(Calendar.HOUR_OF_DAY,hour);
		date.set(Calendar.MINUTE,minutes);

		// Add the alarm and then sort all of them
		alarms.put(className, date);
		sortAlarms();
	}

	/** Sorts the alarms HashMap in chronological order
	 */
	private void sortAlarms() {
		List<Map.Entry<String, Calendar>> list = new LinkedList<>(alarms.entrySet());

		// Sort the list based on the Calendar value
		list.sort( (c1,c2)-> c1.getValue().compareTo(c2.getValue()) );

		// put data from sorted list to hashmap
		HashMap<String, Calendar> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<String, Calendar> aa : list) {
			sortedMap.put(aa.getKey(), aa.getValue());
		}
		alarms = sortedMap;
	}

	/** Removes an existing alarm
	 * @param className Name of the alarm to remove
	 * @throws IllegalArgumentException If the alarm name doesn't exist
	 */
	public void removeAlarm(String className) throws IllegalArgumentException {
		scheduler.cancel(className);
		alarms.remove(className);
		sortAlarms();
	}

	/** Get's the the Clock instance
	 * @return The Clock instance
	 */
	public static Clock getInstance() {
		if(instance == null) {
			instance = new Clock();
		}
		return instance;
	}

	// Getter and setters
	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean on) {
		isOn = on;
		// TODO shutdown the scheduler if set to off, if turned back on again reload everything from the JSON file
	}

	public void setJda(JDA jda) {
		this.jda = jda;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Map<String, Calendar> getAlarms() {
		return alarms;
	}
}

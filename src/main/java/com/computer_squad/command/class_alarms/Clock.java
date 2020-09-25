package com.computer_squad.command.class_alarms;

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.cron.CronSchedule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton holding the alarms
 */
public class Clock {
	private static Clock instance = null;
	private JDA jda;
	private String channelName = "";
	private Scheduler scheduler = new Scheduler();
	private Map<String, String> alarms = new HashMap<>();

	private boolean isOn = true;

	/**
	 * Singleton class for the Class Alarm
	 */
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

		// Human readable date to keep track of the current alarms
		// Integer with a leading 0 will be remove
		String minutesString;
		if (minutes <= 9) {
			minutesString = "0" + minutes;
		} else {
			minutesString = String.valueOf(minutes);
		}
		String formattedWeekDay;
		formattedWeekDay = weekDay.substring(0,1).toUpperCase() + weekDay.substring(1).toLowerCase();
		String date = formattedWeekDay + " at " + hour + ":" + minutesString;
		alarms.put(className, date);
	}

	/** Removes an existing alarm
	 * @param className Name of the alarm to remove
	 * @throws IllegalArgumentException If the alarm name doesn't exist
	 */
	public void removeAlarm(String className) throws IllegalArgumentException {
		scheduler.cancel(className);
		alarms.remove(className);
	}

	/** Get's the the Clock instance
	 * @return A Clock instance
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

	public Map<String, String> getAlarms() {
		return alarms;
	}
}

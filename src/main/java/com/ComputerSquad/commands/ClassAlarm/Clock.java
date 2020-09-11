package com.ComputerSquad.commands.ClassAlarm;

import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.cron.CronSchedule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

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

	public void removeAlarm(String className) throws IllegalArgumentException {
		scheduler.cancel(className);
		alarms.remove(className);
	}

	/** Get's the the ClassAlarm instance
	 * @return A classAlarm instance
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

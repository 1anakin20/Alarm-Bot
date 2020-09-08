package com.ComputerSquad.commands.ClassAlarm;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock {
	private static Clock instance = null;
	private JDA jda;
	private String channelName = "";

	private boolean isOn = true;

	/**
	 * Singleton class for the Class Alarm
	 */
	private Clock() {
		JSONAlarms jsonAlarms = new JSONAlarms();
		// Check each minute if it's time for class or not
		Runnable runnable = () -> {
			// Check if it's class time if the alarm is on
			if(isOn) {
				if (jda != null) {
					// Get current time
					LocalDate currentDate = LocalDate.now();
					LocalTime currentTime = LocalTime.now();

					String dayOfWeek = String.valueOf(currentDate.getDayOfWeek()).toLowerCase();
					int hour = currentTime.getHour();
					int minutes = currentTime.getMinute();

					String matchingKey = dayOfWeek + ":" + hour + ":" + minutes;

					Map<String, String> alarms = jsonAlarms.readJSON();

					// Check if one matches the current time
					if (alarms.containsKey(matchingKey)) {
						TextChannel textChannel = jda.getTextChannelsByName(channelName,true).get(0);
						textChannel.sendMessage("@everyone " + alarms.get(matchingKey)).queue();
						System.out.println("Should ping");
					}
				}
			}
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);
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
	}

	public void setJda(JDA jda) {
		this.jda = jda;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}

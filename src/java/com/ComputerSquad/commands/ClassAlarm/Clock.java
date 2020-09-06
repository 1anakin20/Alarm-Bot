package com.ComputerSquad.commands.ClassAlarm;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Clock {
	private static Clock instance = null;
	private JDA jda;

	private boolean isOn = true;

	/**
	 * Singleton class for the Class Alarm
	 */
	private Clock() {
		// Check each minute if it's time for class or not
		Runnable runnable = () -> {
			// Check if it's class time if the alarm is on
			if(isOn) {
				// TODO Check if it's class time or not
				Calendar calendar = Calendar.getInstance();
//				calendar.get(Calendar.DAY_OF_WEEK)
				if (jda != null) {
					TextChannel textChannel = jda.getTextChannelsByName("bot-test",true).get(0);
					textChannel.sendMessage("@everyone").queue();
					System.out.println("Should ping");
				}
			}
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.MINUTES);
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
}

package com.ComputerSquad.ClassAlarm;

import com.ComputerSquad.helpers.JSONAlarms;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClassAlarm {
	private static ClassAlarm instance = null;

	private boolean isOn = true;

	/**
	 * Singleton class for the Class Alarm
	 */
	private ClassAlarm() {
		// Check each minute if it's time for class or not
		Runnable runnable = () -> {
			// Check if it's class time if the alarm is on
			if(isOn) {
				// TODO Check if it's class time or not
				Calendar calendar = Calendar.getInstance();
//				calendar.get(Calendar.DAY_OF_WEEK)
			}
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);
	}

	/** Get's the the ClassAlarm instance
	 * @return A classAlarm instance
	 */
	public static ClassAlarm ClassAlarm() {
		if(instance == null) {
			instance = new ClassAlarm();
		}
		return instance;
	}

	public void arguments(MessageChannel messageChannel, String[] args) {
		if (args.length > 1) {
			// Too many arguments
			messageChannel.sendMessage("Too many arguments").queue();
			return;
		} else if (args.length == 0) {
			// Show help commands
			messageChannel.sendMessage("Available arguments: on, off").queue();
			return;
		}

		switch (args[0]) {
			case "on":
				setOn(true);
				messageChannel.sendMessage("Alarm has been turned On").queue();
				break;
			case "off":
				setOn(false);
				messageChannel.sendMessage("Alarm has been turned off").queue();
				break;
			case "times":
				new JSONAlarms().readJSON();
				break;
			default:
				// Argument not found
				messageChannel.sendMessage("Argument doesn't exist").queue();
		}
	}

	// Getter and setters
	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean on) {
		isOn = on;
	}
}

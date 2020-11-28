package com.computer_squad.command.utility.alarm;

import com.coreoz.wisp.Job;
import com.coreoz.wisp.JobStatus;
import com.coreoz.wisp.Scheduler;
import com.coreoz.wisp.schedule.cron.CronSchedule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.DayOfWeek;
import java.util.*;

/**
 * Singleton holding the alarms
 */
public class Clock {
	private static Clock instance = null;
	private JDA jda;
	private String channelID = "";
	private final Scheduler scheduler = new Scheduler();
	private Map<String, Calendar> alarms = new HashMap<>();

	private boolean isOn = true;

	private Clock() {
		// Reload alarms
		JSONAlarms jsonAlarms = new JSONAlarms();
		Map<String, Calendar> saved = jsonAlarms.readAlarms();

		saved.forEach((key, value) -> {
			String dayOfWeek = value.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CANADA);
			int hour = value.get(Calendar.HOUR_OF_DAY);
			int minutes = value.get(Calendar.MINUTE);
			newAlarm(dayOfWeek, hour, minutes, key);
		});
	}

	/** Adds a new alarm
	 * @param weekDay Day of the week name: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
	 * @param hour Hour of the day in 24h format
	 * @param minutes Minutes of the hour
	 * @param className Name of the alarm
	 */
	public String newAlarm(String weekDay, int hour, int minutes, String className) {
		// Add a #alarmNumber if it's already present
		if (alarms.containsKey(className)) {
			String alternativeAlarmName;
			int i = 1;
			do {
				alternativeAlarmName = className + "#" + i;
				i++;
			} while (alarms.containsKey(alternativeAlarmName));
			className = alternativeAlarmName;
		}

		// format the first Weekday letter into upper case
		String formattedWeekDay = weekDay.substring(0,1).toUpperCase() + weekDay.substring(1).toLowerCase();

		// Create a new Calendar that tracks the date
		Calendar date = Calendar.getInstance();
		int dayOfWeek = DayOfWeek.valueOf(formattedWeekDay.toUpperCase()).getValue();
		date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		date.set(Calendar.HOUR_OF_DAY,hour);
		date.set(Calendar.MINUTE,minutes);

		String cronExpression = CronHelper.CronUnixExpressionCreator(weekDay, hour, minutes);

		// Add the alarm and then sort all of them
		alarms.put(className, date);
		scheduleAlarm(className, cronExpression);
		sortAlarms();

		// Save the alarm
		JSONAlarms jsonAlarms = new JSONAlarms();
		jsonAlarms.saveAlarm(className, date);

		return className;
	}

	/** Removes an existing alarm
	 * @param className Name of the alarm to remove
	 * @throws IllegalArgumentException If the alarm name doesn't exist
	 */
	public void removeAlarm(String className) throws IllegalArgumentException {
		Optional<Job> jobOptional = scheduler.findJob(className);

		// Make sure the job exists
		if (!jobOptional.isPresent()) {
			throw new IllegalArgumentException("Job doesn't exist");
		}

		Job job = jobOptional.get();

		// A job that is already done will be considered as not existing
		if(job.status() == JobStatus.DONE) {
			throw new IllegalArgumentException("Job doesn't exist");
		}

		String alarmName = job.name();
		scheduler.cancel(alarmName);
		alarms.remove(className);
		JSONAlarms jsonAlarms = new JSONAlarms();
		jsonAlarms.removeAlarm(alarmName);
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

	private void scheduleAlarm(String name, String unixCronExpression) {
		Runnable ping = () -> {
			System.out.println();
			TextChannel textChannel = jda.getTextChannelById(channelID);
			assert textChannel != null;
			textChannel.sendMessage("@everyone " + name).queue();
		};

		scheduler.schedule(name, ping, CronSchedule.parseUnixCron(unixCronExpression));
	}

	/** Sorts the alarms HashMap in chronological order
	 */
	private void sortAlarms() {
		List<Map.Entry<String, Calendar>> list = new LinkedList<>(alarms.entrySet());

		// Sort the list based on the Calendar value
		list.sort(Map.Entry.comparingByValue());

		// put data from sorted list to hashmap
		HashMap<String, Calendar> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<String, Calendar> aa : list) {
			sortedMap.put(aa.getKey(), aa.getValue());
		}
		alarms = sortedMap;
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

	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public Map<String, Calendar> getAlarms() {
		return alarms;
	}
}

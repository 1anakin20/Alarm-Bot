package com.computer_squad.command.utilities.class_alarm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSONAlarms {
	private final String alarmPath = "user/alarms.json";
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public Map<String, Calendar> readAlarms() {
	    Reader reader = null;
		File file = new File(alarmPath);
		try {
			if (file.createNewFile()) {
				FileWriter fileWriter = new FileWriter(file);
				// Add empty braces to avoid nullPointerException from gson.fromJson(Line 30)
				fileWriter.write("{}");
				fileWriter.close();
			}
			reader = new FileReader(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gson.fromJson(reader, new TypeToken<HashMap<String, Calendar>>() {}.getType());
	}

	public void saveAlarm(String name, Calendar date) {
		Map<String, Calendar> json = readAlarms();
		json.put(name, date);
		writeToFile(json);
	}

	public void removeAlarm(String name) {
		Map<String, Calendar> alarms = readAlarms();
		alarms.remove(name);
		writeToFile(alarms);
	}

	public void writeToFile(Map<String, Calendar> dict) {
		try (FileWriter writer = new FileWriter(alarmPath)) {
			gson.toJson(dict, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

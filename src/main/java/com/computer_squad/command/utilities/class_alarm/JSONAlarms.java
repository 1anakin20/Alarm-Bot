package com.computer_squad.command.utilities.class_alarm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSONAlarms {
	private final String alarmPath = "user/alarms.json";
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public Map<String, Calendar> readAlarms() {
		try (Reader reader = new FileReader(alarmPath)) {
			return gson.fromJson(reader, new TypeToken<HashMap<String, Calendar>>() {}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

package com.computer_squad.command.utilities.class_alarm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class JSONAlarms {
	private final String alarmPath = "user/alarms.json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public Map<String, String> readJSON() {
		try (Reader reader = new FileReader(alarmPath)) {
			return gson.fromJson(reader, new TypeToken<HashMap<String, String>>() {}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveAlarm(String date, String className) {
		Map<String, String> json = readJSON();
		json.put(date, className);
		writeToFile(json);
	}

	public void writeToFile(Map<String, String> dict) {
		try (FileWriter writer = new FileWriter(alarmPath)) {
			gson.toJson(dict, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

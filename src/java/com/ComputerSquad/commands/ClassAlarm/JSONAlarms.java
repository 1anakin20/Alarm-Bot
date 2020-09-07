package com.ComputerSquad.commands.ClassAlarm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class JSONAlarms {
	private final String alarmPath = "user/alarms.json";
	private Gson gson = new Gson();

	public void saveDate(int weekDay, int hour, int minutes, String className) {
		String date = weekDay + ":" + hour + ":" + minutes;
		jsonObject.put(date, className);
	}

	public Map<String, String> readJSON() {
		try (Reader reader = new FileReader(alarmPath)) {
			return gson.fromJson(reader, new TypeToken<HashMap<String, String>>() {}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeToFile() {
		// TODO updates the hours if changed
	}
}

package com.ComputerSquad.helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class JSONAlarms {
	private final String alarmPath = "user/alarms.json";
	private JSONObject jsonObject = new JSONObject();

	public void saveDate(int weekDay, int hour, int minutes, String className) {
		String date = weekDay + ":" + hour + ":" + minutes;
		jsonObject.put(date, className);

	}

	public void readJSON() {
		JSONParser parser = new JSONParser();
		String alarmJson = null;
		try {
			alarmJson = FileOperations.readFile(alarmPath);
		} catch (IOException e) {
			System.out.println("The alarm settings file couldn't be found");
			return;
		}

		Object obj = null;
		try {
			obj = parser.parse(alarmJson);
		} catch (ParseException e) {
			System.out.println("The file is not a valid format");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(obj);

		// TODO Parse the hours
		System.out.println(jsonArray.get(0));
	}

	public void writeToFile() {
		// TODO updates the hours if changed
	}
}

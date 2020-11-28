package com.computer_squad.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {
	private static final String CONFIGURATION_PATH = "user/config.properties";

	public static Configuration loadConfiguration() {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(CONFIGURATION_PATH));
		} catch (IOException e) {
			System.out.println("No configuration file found in: " + CONFIGURATION_PATH);
			System.exit(1);
		}
		return new Configuration(properties);
	}
}

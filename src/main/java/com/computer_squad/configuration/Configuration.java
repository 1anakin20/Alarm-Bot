package com.computer_squad.configuration;

import java.io.Console;
import java.util.Properties;

public class Configuration {
	private final String botToken;
	private final String roleId;
	private final String channelName;
	private final String ownerId;

	public Configuration(Properties properties) {
		botToken = parseBotToken(properties.getProperty("botToken"));
		roleId = properties.getProperty("roleId");
		channelName = properties.getProperty("channelName");
		ownerId = properties.getProperty("ownerId");
	}

	public String getBotToken() {
		return botToken;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getChannelName() {
		return channelName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	private String parseBotToken(String botTokenValue) {
		if (botTokenValue.equals("runtime")) {
			return getBotTokenFromUser();
		} else {
			return botTokenValue;
		}
	}

	private String getBotTokenFromUser() {
		Console console = System.console();

		if(console == null) {
			System.out.println("No console available");
			System.exit(0);
		}

		System.out.print("Enter bot token: ");
		return String.valueOf(console.readPassword());
	}
}

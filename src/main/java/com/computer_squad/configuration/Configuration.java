package com.computer_squad.configuration;

import java.io.Console;
import java.util.Properties;

/**
 * The configuration file should be inside a folder called "user".
 * Write each value in a key=value pair.
 * <br>
 * Keys:
 * <dl>
 *     <dt>botToken</dt>
 *     <dd>The token of the bot. You can also write "runtime" to be asked for it at the bot startup</dd>
 *     <dt>channelID</dt>
 *     <dd>Channel ID to send the alarm ping in</dd>
 *     <dt>ownerID</dt>
 *     <dd>ID of the owner of the bot</dd>
 * </dl>
 */
public class Configuration {
	private final String botToken;
	private final String channelID;
	private final String ownerId;

	public Configuration(Properties properties) {
		botToken = parseBotToken(properties.getProperty("botToken"));
		channelID = properties.getProperty("channelID");
		ownerId = properties.getProperty("ownerID");
	}

	public String getBotToken() {
		return botToken;
	}

	public String getChannelID() {
		return channelID;
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

package com.computer_squad.configuration;

import java.io.Console;
import java.util.Properties;

public class Configuration {
    private static final String BOT_TOKEN = "botToken";

    private String botToken;
    private String roleId;
    private String channelName;
    private String ownerId;

    public Configuration(Properties properties) {
        this.botToken = parseBotToken(properties.getProperty(BOT_TOKEN));
        this.roleId = properties.getProperty("roleId");
        this.channelName = properties.getProperty("channelName");
        this.ownerId = properties.getProperty("ownerId");
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

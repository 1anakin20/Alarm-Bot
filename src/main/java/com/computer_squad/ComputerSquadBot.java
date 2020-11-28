package com.computer_squad;

import com.computer_squad.command.utility.alarm.ClassAlarm;
import com.computer_squad.command.utility.alarm.Clock;
import com.computer_squad.command.utility.status.Status;
import com.computer_squad.configuration.Configuration;
import com.computer_squad.configuration.ConfigurationLoader;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class ComputerSquadBot {
	public static final String PREFIX = "++";

	public static void main( String[] args ) {

		/*
		  The configuration file should be inside a folder called "user"
		  With a new line for each piece of information needed
		  Order:
		  Bot token; you can also write runtime to be asked for it when running the bot
		  ID of the owner of the bot
		 */
		ConfigurationLoader configurationLoader = new ConfigurationLoader();
		Configuration configuration = configurationLoader.loadConfiguration();

		EventWaiter eventWaiter = new EventWaiter();

		CommandClientBuilder client = new CommandClientBuilder()
				.setOwnerId(configuration.getOwnerId())
				.useDefaultGame()
				.setPrefix(PREFIX);

		client.addCommands(
				new Status(),
				new ClassAlarm()
		);

		JDA jda = null;
		try {
			jda = JDABuilder.createDefault(configuration.getBotToken())
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.build();
			jda.addEventListener(eventWaiter, client.build());
			jda.awaitReady();
		} catch (LoginException e) {
			System.out.println("Error, couldn't login. Please verify the token");
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Configure the alarm clock
		Clock clock = Clock.getInstance();
		clock.setJda(jda);
		clock.setChannelID(configuration.getChannelID());
	}
}

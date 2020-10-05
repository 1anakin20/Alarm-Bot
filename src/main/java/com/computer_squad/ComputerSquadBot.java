package com.computer_squad;

import com.computer_squad.command.fun.echo.Echo;
import com.computer_squad.command.fun.hello.SayHello;
import com.computer_squad.command.utilities.class_alarm.ClassAlarm;
import com.computer_squad.command.utilities.class_alarm.Clock;
import com.computer_squad.services.UserEvents;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
		List<String> config = null;
		try {
			config = Files.readAllLines(Paths.get("user/configuration"));
		} catch (IOException e) {
			System.out.println("No file named 'configuration' inside the user folder found");
			System.exit(1);
		}
		// Config file
		String botToken;
		String roleID;

		// Bot Token
		if (config.get(0).equals("runtime")) {
			Console console = System.console();

			if(console == null) {
				System.out.println("No console available");
				System.exit(0);
			}

			System.out.print("Enter bot token: ");
			botToken = String.valueOf(console.readPassword());
		} else {
			botToken = config.get(0);
		}

		try {
			roleID = config.get(3);
		} catch (IndexOutOfBoundsException e) {
			roleID = "";
		}


		EventWaiter eventWaiter = new EventWaiter();

		CommandClientBuilder client = new CommandClientBuilder()
				.setOwnerId(config.get(1))
				.useDefaultGame()
				.setPrefix(PREFIX);

		client.addCommands(
				new SayHello(),
				new ClassAlarm(),
				new Echo()
		);

		JDA jda = null;
		try {
			jda = JDABuilder.createDefault(botToken)
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.build();
			jda.addEventListener(eventWaiter, client.build(), new UserEvents(roleID));
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
		clock.setChannelName(config.get(2));
	}

	public static String getPrefix() {
		return PREFIX;
	}
}

package com.ComputerSquad;

import com.ComputerSquad.ClassAlarm.ClassAlarm;
import com.ComputerSquad.commands.SayHello;
import com.ComputerSquad.helpers.Token;
import com.ComputerSquad.commands.CommandListener;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageActivity;

import javax.security.auth.login.LoginException;

public class Main {

	public static void main( String[] args ) {
		// Create bot instance
		String token = Token.readToken();
//		CommandListener commandListener = new CommandListener();

		EventWaiter eventWaiter = new EventWaiter();

		CommandClientBuilder client = new CommandClientBuilder();

		client.useDefaultGame();
		client.setPrefix("++");

		client.addCommand(
				new SayHello()
		);

		client.setOwnerId("owner");

		try {
			JDA jda = JDABuilder.createDefault(token).build();
			jda.addEventListener(eventWaiter, client.build());
		} catch (LoginException e) {
			System.out.println("Error, couldn't login. Please verify the token");
			System.exit(1);
		}

		// Get the alarm clock running
		ClassAlarm.ClassAlarm();
	}
}

package com.ComputerSquad;

import com.ComputerSquad.ClassAlarm.ClassAlarm;
import com.ComputerSquad.Helpers.Token;
import com.ComputerSquad.commands.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

	public static void main( String[] args ) {
		// Create bot instance
		String token = Token.readToken();
		CommandListener commandListener = new CommandListener();

		try {
			JDA jda = JDABuilder.createDefault(token).build();
			jda.addEventListener(commandListener);
		} catch (LoginException e) {
			System.out.println("Error, couldn't login. Please verify the token");
			System.exit(1);
		}
	}
}

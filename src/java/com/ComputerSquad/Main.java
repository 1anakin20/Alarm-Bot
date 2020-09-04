package com.ComputerSquad;

import com.ComputerSquad.commands.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

	public static void main( String[] args ) {
		CommandListener commandListener = new CommandListener();

		try {
			JDA jda = JDABuilder.createDefault("").build();
			jda.addEventListener(commandListener);
		} catch (LoginException e) {
			System.out.println("The token entered is incorrect please retry");
		}
	}
}

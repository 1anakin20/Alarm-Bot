package com.ComputerSquad.commands;

import com.ComputerSquad.ClassAlarm.ClassAlarm;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CommandListener extends ListenerAdapter {
	private final String prefix = "++";

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		Message message = event.getMessage();
		MessageChannel messageChannel = event.getChannel();

		if (message.getContentRaw().startsWith(prefix)) {
			// Note: It will remove all '++' in the text, so no commands can have ++
			String command = message.getContentRaw().replace(prefix, "");
			// Separate the command from the arguments
			// Ex: ++command argument1 argument2
			String[] commandParts = command.split(" ");
			String[] args = {};
			if (commandParts.length > 1) {
				args = Arrays.copyOfRange(commandParts, 1, commandParts.length);
			}
			switch (commandParts[0]) {
				case "alarm":
					// Activate class alarm
					ClassAlarm.ClassAlarm().arguments(messageChannel, args);
					break;
				default:
					// No command found
					messageChannel.sendMessage("Command not found").queue();
			}
		}
	}
}

package com.ComputerSquad.jda;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class UserEvents extends ListenerAdapter {
	@Override
	public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
		// Automatically add a role when an user joins
		Member member = event.getMember();
		JDA jda = event.getJDA();
		// Roles
		Role role = event.getGuild().getRoleById("751121572105814139");
		assert role != null;
		event.getGuild().addRoleToMember(member, role).queue();
		// Greeting
		TextChannel textChannel = jda.getTextChannelsByName("bot-commands", true).get(0);
		textChannel.sendMessage("Welcome " + member.getEffectiveName()).queue();
	}
}

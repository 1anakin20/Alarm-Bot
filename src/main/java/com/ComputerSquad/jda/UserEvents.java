package com.ComputerSquad.jda;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class UserEvents extends ListenerAdapter {
	private String roleID;
	public UserEvents(String roleID) {
		this.roleID = roleID;
	}

	@Override
	public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
		// Automatically add a role when an user joins
		Member member = event.getMember();
		JDA jda = event.getJDA();
		if (!roleID.isEmpty()) {
			// Roles
			Role role = event.getGuild().getRoleById(roleID);
			assert role != null;
			event.getGuild().addRoleToMember(member, role).queue();
		}
	}
}

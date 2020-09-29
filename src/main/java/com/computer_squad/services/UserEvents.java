package com.computer_squad.services;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * User events Events triggered by {@link ListenerAdapter}
 */
public class UserEvents extends ListenerAdapter {
	private String roleID;
	public UserEvents(String roleID) {
		this.roleID = roleID;
	}

	@Override
	public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
		// Automatically adds a role when an user joins
		Member member = event.getMember();
		if (!roleID.isEmpty()) {
			// Roles
			Role role = event.getGuild().getRoleById(roleID);
			assert role != null;
			event.getGuild().addRoleToMember(member, role).queue();
		}
	}
}

package com.gulbi.Backend.domain.chat.message.dto;

import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageSaveCommand {
	private final MessageReceiveRequest request;
	private final User sender;
	private final User receiver;
	private final ChatRoom room;
}

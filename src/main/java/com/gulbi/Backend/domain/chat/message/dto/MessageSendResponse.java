package com.gulbi.Backend.domain.chat.message.dto;

import java.time.LocalDateTime;

import com.gulbi.Backend.domain.chat.message.entity.ChatMessage;

import lombok.Getter;

@Getter
public class MessageSendResponse {

	private Long messageId;
	private Long chatRoomId;
	private String content;
	private Long senderId;
	private Long receiverId;
	private LocalDateTime timestamp;
	private boolean isRead;

	public MessageSendResponse(Long messageId, Long chatRoomId, String content, Long senderId, Long receiverId,
		LocalDateTime timestamp, boolean isRead) {
		this.messageId = messageId;
		this.chatRoomId = chatRoomId;
		this.content = content;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.timestamp = timestamp;
		this.isRead = isRead;
	}

	// ChatMessage에서 DTO로 변환
	public static MessageSendResponse from(ChatMessage chatMessage) {
		return new MessageSendResponse(
			chatMessage.getId(),
			chatMessage.getChatRoom().getId(),
			chatMessage.getContent(),
			chatMessage.getSender().getId(),
			chatMessage.getReceiver().getId(),
			chatMessage.getTimestamp(),
			chatMessage.isRead()

		);
	}

	public void readMessage(){
		this.isRead = true;
	}
}

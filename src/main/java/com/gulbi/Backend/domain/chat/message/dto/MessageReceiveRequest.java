package com.gulbi.Backend.domain.chat.message.dto;

import com.gulbi.Backend.domain.chat.message.entity.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageReceiveRequest {
    private String content;
    private LocalDateTime timestamp;
}

package com.gulbi.Backend.domain.chat.websocket;

public class UserConnectedEvent {
    private final Long userId;

    public UserConnectedEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}

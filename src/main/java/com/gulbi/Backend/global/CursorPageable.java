package com.gulbi.Backend.global;

import org. springframework. data. domain. Pageable;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CursorPageable {
	private final Pageable pageable;
	private final Long lastId;
	private final LocalDateTime lastCreatedAt;

	public CursorPageable(Pageable pageable, Long lastId, LocalDateTime lastCreatedAt) {
		this.pageable = pageable;
		this.lastId = lastId;
		this.lastCreatedAt = lastCreatedAt;
	}
}

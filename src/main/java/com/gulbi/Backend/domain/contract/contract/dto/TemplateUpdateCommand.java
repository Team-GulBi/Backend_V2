package com.gulbi.Backend.domain.contract.contract.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TemplateUpdateCommand {
	private final TemplateUpdateRequest request;
	private final Long templateId;
}

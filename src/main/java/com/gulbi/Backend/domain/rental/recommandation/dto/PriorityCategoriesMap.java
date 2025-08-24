package com.gulbi.Backend.domain.rental.recommandation.dto;

import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PriorityCategoriesMap {
	private Map<String, CategoryPair> map;

	public PriorityCategoriesMap() {
	}

	public Map<String, CategoryPair> getMap() {
		return map;
	}

	public void put(String priority, CategoryPair pair){
		map.put(addUUID(priority), pair);
	}

	private String addUUID(String key){
		UUID uuid = UUID.randomUUID();
		String strUuid = uuid.toString();
		String parsedPriority = key + "-" + strUuid;
		return parsedPriority;
	}

	private String subUUID(String key){
		return key.contains("-") ? key.substring(0,key.indexOf("-")) : key;

	}

}

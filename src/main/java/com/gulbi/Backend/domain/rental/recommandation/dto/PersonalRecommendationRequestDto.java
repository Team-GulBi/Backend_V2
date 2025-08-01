package com.gulbi.Backend.domain.rental.recommandation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gulbi.Backend.domain.rental.recommandation.code.QueryFilter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class PersonalRecommendationRequestDto {
        @NotNull
        @Schema(description = "페이지 네이션 사이즈", example = "10")
        private int size;
        @NotNull
        @Schema(description = "GET 요청시 false, POST요청시 true", example = "false")
        private boolean pagination;
        @NotNull
        @Schema(description = "추천 전략을 위한 필드", example = "RECENT")
        private QueryFilter filter;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY,
                description = "페이지 네이션",
                example = "{\n" +
                        "  \"1\": {\n" +
                        "    \"bCategoryId\": { \"intValue\": 1 },\n" +
                        "    \"mCategoryId\": { \"intValue\": 2 },\n" +
                        "    \"lastCreatedAt\": { \"dateTimeValue\": \"2025-01-05T23:52:36.861726\" },\n" +
                        "    \"당부말씀\": { \"dateTimeValue\": \"GET,POST같은DTO를쓰기에,GET요청은categories를지우고쓰세요\" }\n" +
                        "  }\n" +
                        "}"
        )
        private Map<Integer, Map<String, Value>> categories;




        public PersonalRecommendationRequestDto() {
            this.size = 0;
            this.pagination = false;
            this.filter = null;
        }

        public PersonalRecommendationRequestDto(int size, boolean isPagination, QueryFilter filter) {
            this.size = size;
            this.pagination = isPagination;
            this.filter = filter;
        }

        @Getter
        public static class Value {
            private final Integer intValue;
            private final LocalDateTime dateTimeValue;

            public Value() {
                this.intValue = null;
                this.dateTimeValue = null;
            }

            @JsonCreator
            public Value(@JsonProperty("intValue") Integer intValue,
                         @JsonProperty("dateTimeValue") LocalDateTime dateTimeValue) {
                this.intValue = intValue;
                this.dateTimeValue = dateTimeValue;
            }
        }

//    public void print() {
//        if (categories != null && !categories.isEmpty()) {
//            categories.forEach((priority, valueMap) -> {
//                System.out.println("Priority: " + priority);
//                valueMap.forEach((keyString, value) -> {
//                    System.out.print("  " + keyString + ": ");
//                    if (value.getIntValue() != null) {
//                        System.out.println(value.getIntValue());
//                    } else if (value.getDateTimeValue() != null) {
//                        System.out.println(value.getDateTimeValue());
//                    } else {
//                        System.out.println("null");
//                    }
//                });
//            });
//        } else {
//            System.out.println("Categories is empty.");
//        }
//    }

}



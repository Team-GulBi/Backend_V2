package com.gulbi.Backend.domain.rental.review.dto;

// 상품을 상세조회를 할떄 리뷰도 가지고 와야하는데.
public interface ReviewWithAvgProjection {
    Long getId();
    Integer getRating();
    String getContent();
    Float getAverageRating();
    String getNickName();
    String getProfileImage();

}

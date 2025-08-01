package com.gulbi.Backend.domain.rental.product.dto.product.request.register;

import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegisterRequestDto {
    @NotNull(message = "1개 이상의 태그를 입력 해 주새요.")
    @Pattern(regexp = "^[\\w가-힣]{1,7}(,[\\w가-힣]{1,7})*$", message = "각 항목은 1자에서 7자 사이의 글자만 포함할 수 있습니다.")
    @Schema(description = "태그를 입력 해 주세요.", example = "태그1,태그2")
    private String tag;

    @NotNull(message = "제목을 입력 해 주세요.")
    @Size(min = 1, max = 30, message = "최소 1글자에서 최대 30글자 까지 적어주세요.")
    @Schema(description = "제목을 입력 해 주세요.", example = "MacBook Pro M3 최저가!!")
    private String title;

    @Size(min = 1, max = 20, message = "최소 1글자에서 최대 20글자 까지 적어주세요.")
    @Schema(description = "상품 이름을 입력 해 주세요.", example = "MacBook Pro M3")
    private String name;

    @NotNull(message = "가격을 입력 해 주세요.")
    @Min(value = 1, message = "올바른 가격을 적어주세요.")
    @Schema(description = "가격을 입력 해 주세요", example = "500000")
    private Integer price;
    @NotNull(message = "시도를 입력 해 주세요.")
    @Schema(description = "시도를 입력 해 주세요", example = "경기도")
    private String sido;
    @NotNull(message = "시군구를 입력 해 주세요.")
    @Schema(description = "시군구를 입력 해 주세요", example = "원미구")
    private String sigungu;
    @Schema(description = "시군구를 입력 해 주세요", example = "중동")
    private String bname;
    @NotNull(message = "상품 설명을 입력하세요.")
    @Size(min = 1, max = 1000, message = "최소 1글자에서 최대 1000글자 까지 적어주세요.")
    @Schema(description = "상품 설명을 작성하세요.", example = "사용감이 좀 있으나")
    private String description;

    @NotNull(message = "카테고리 아이디를 넣어주세요")
    @Min(0)
    @Schema(description = "카테고리의 PK를 입력해주세요", example = "1")
    private Long bcategoryId;

    @NotNull(message = "카테고리 아이디를 넣어주세요")
    @Min(0)
    @Schema(description = "카테고리의 PK를 입력해주세요", example = "2")
    private Long mcategoryId;

    @NotNull(message = "카테고리 아이디를 넣어주세요")
    @Min(0)
    @Schema(description = "카테고리의 PK를 입력해주세요", example = "3")
    private Long scategoryId;

    @Setter
    @Schema(hidden = true)
    private ProductImageCollection productImageCollection;
    @Setter
    @Schema(hidden = true)
    private ImageUrl mainImage;

    public ProductRegisterRequestDto(String tags, String title, String productName, Integer price, String sido, String sigungu, String bname, String description, ImageUrl mainImage, Long bcategoryId, Long mcategoryId, Long scategoryId) {
        System.out.println("RequestPart는 생성자를 이용하지 않습니다!");
        this.tag = tag;
        this.title = title;
        this.name = productName;
        this.price = price;
        this.sido = sido;
        this.sigungu = sigungu;
        this.bname = bname;
        this.description = description;
        this.bcategoryId = bcategoryId;
        this.mcategoryId = mcategoryId;
        this.scategoryId = scategoryId;
    }
}

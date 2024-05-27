package org.yeinp.animate.animate_backend.adoption.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionArticleDto {
    private Long arNo;
    private Timestamp arRegdate;
    private Long userNo;
    private String arTitle;
    private String arContent;
    private String userNickname;
    private List<AdoptionImgDto> adoptionReviewImgList;
}

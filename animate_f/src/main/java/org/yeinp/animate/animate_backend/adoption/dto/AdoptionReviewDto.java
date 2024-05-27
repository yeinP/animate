package org.yeinp.animate.animate_backend.adoption.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdoptionReviewDto {
    private  Long arNo;

    private Timestamp arRegdate;

    private Long userNo;

    private String arTitle;

    private String arContent;


}

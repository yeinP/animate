package org.yeinp.animate.animate_backend.home.dto;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeARDto {
    public Long arNo;
    public Timestamp arRegdate;

    public String arTitle;

    public String arImgUrl;

    public Long likeCount;


}

package org.yeinp.animate.animate_backend.notice.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeArticleDto {

    private Long noticeNo;
    private String noticeTitle;
    private String noticeContent;
    private int noticeCount;
    private Timestamp noticeRegdate;
    private int noticeStatus;

    private List<NoticeFilesDto> noticeFileDto;
}

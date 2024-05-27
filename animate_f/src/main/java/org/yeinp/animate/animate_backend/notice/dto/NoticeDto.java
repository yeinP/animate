package org.yeinp.animate.animate_backend.notice.dto;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NoticeDto {
    private Long noticeNo;
    private String noticeTitle;
    private String noticeContent;
    private Long noticeWriter;
    private int noticeCount;
    private Timestamp noticeRegdate;
    private int noticeStatus;
}

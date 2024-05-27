package org.yeinp.animate.animate_backend.notice.dto;

import jakarta.persistence.*;
import lombok.*;
import org.yeinp.animate.animate_backend.entity.Notice;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NoticeFilesDto {

    private  Long noticeFileNo;
    private  Long noticeNo;
    private String noticeFileUrl;
    private String noticeFileKind;
    private String noticeFileName;



}

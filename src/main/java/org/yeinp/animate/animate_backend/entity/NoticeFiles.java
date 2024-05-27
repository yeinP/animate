package org.yeinp.animate.animate_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="notice_files")
public class NoticeFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeFileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_no")
    private Notice notice;

    private String noticeUrl;
    private String noticeFileKind;
    private String noticeFileName;
}

package org.yeinp.animate.animate_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name="notice")
public class Notice{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeNo;

    private String noticeTitle;
    private String noticeContent;
    private Long noticeWriter;
    private int noticeCount;
    private Timestamp noticeRegdate;

    private int noticeStatus;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeFiles> noticeFilesList;
}

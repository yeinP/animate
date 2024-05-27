package org.yeinp.animate.animate_backend.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3NoticeFileDto {
    private String originFileName;
    private String renamedFileName;
    private String fileUrl;
}

package org.yeinp.animate.animate_backend.notice.service;

import org.springframework.web.multipart.MultipartFile;
import org.yeinp.animate.animate_backend.miss.dto.S3MissFileDto;
import org.yeinp.animate.animate_backend.notice.dto.S3NoticeFileDto;

import java.io.IOException;
import java.util.List;

public interface AwsNoticeService {
    List<S3NoticeFileDto> uploadFileNotice(List<MultipartFile> multipartFiles);
    Object deleteFiles(String fileName);

    String getRenamedFileName(String originalFileName, int count);

}

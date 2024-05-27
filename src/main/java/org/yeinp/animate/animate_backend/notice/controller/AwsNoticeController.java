package org.yeinp.animate.animate_backend.notice.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yeinp.animate.animate_backend.notice.dto.NoticeDto;
import org.yeinp.animate.animate_backend.notice.dto.S3NoticeFileDto;
import org.yeinp.animate.animate_backend.notice.service.AwsNoticeService;
import org.yeinp.animate.animate_backend.notice.service.NoticeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animate" )
@RequiredArgsConstructor
public class AwsNoticeController {
    @Autowired
    AwsNoticeService awsNoticeService;

    @Autowired
    NoticeService noticeService;

    @PostMapping(value = "/upload/notice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMissFile(@RequestParam(value = "files", required = false) List<MultipartFile> multipartFiles, NoticeDto noticeDto, HttpSession session, String noticeFileKind) {
        Long userNo = (Long) session.getAttribute("userNo");
        List<S3NoticeFileDto> s3NoticeFileDtoList = new ArrayList<>();

        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            s3NoticeFileDtoList = awsNoticeService.uploadFileNotice(multipartFiles);

            List<String> fileUrls = s3NoticeFileDtoList.stream().map(S3NoticeFileDto::getFileUrl).collect(Collectors.toList());
            List<String> fileName = s3NoticeFileDtoList.stream().map(S3NoticeFileDto::getOriginFileName).collect(Collectors.toList());
            noticeDto.setNoticeWriter(userNo);
            int result = noticeService.writeNoticeArticle(noticeDto, fileUrls, noticeFileKind, fileName);
            return ResponseEntity.ok(fileUrls);
        } else {
            noticeDto.setNoticeWriter(userNo);
            int result = noticeService.writeNoticeArticle(noticeDto, new ArrayList<>(), noticeFileKind, new ArrayList<>());
            return ResponseEntity.ok("글만 저장");
        }
    }


    @DeleteMapping("/delete/noticeUrl")
    public ResponseEntity<?> deleteFile(@RequestParam("fileName") String fileName) {
        return ResponseEntity.ok(awsNoticeService.deleteFiles(fileName));
    }


}

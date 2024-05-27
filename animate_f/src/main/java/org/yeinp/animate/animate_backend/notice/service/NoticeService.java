package org.yeinp.animate.animate_backend.notice.service;

import org.yeinp.animate.animate_backend.adoption.dto.AdoptionArticleDto;
import org.yeinp.animate.animate_backend.adoption.dto.AdoptionImgDto;
import org.yeinp.animate.animate_backend.adoption.dto.AdoptionReviewDto;
import org.yeinp.animate.animate_backend.entity.AdoptionImg;
import org.yeinp.animate.animate_backend.entity.AdoptionReview;
import org.yeinp.animate.animate_backend.entity.Notice;
import org.yeinp.animate.animate_backend.entity.NoticeFiles;
import org.yeinp.animate.animate_backend.notice.dto.NoticeArticleDto;
import org.yeinp.animate.animate_backend.notice.dto.NoticeDto;
import org.yeinp.animate.animate_backend.notice.dto.NoticeFilesDto;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface NoticeService {

    int writeNoticeArticle(NoticeDto noticeDto, List<String> imageUrls, String noticeFileKind, List<String> noticeFileName);

    List<NoticeDto> getNoticeList();

    Optional<NoticeArticleDto> getNoticeWithImages(Long noticeNo);
    void increaseNoticeCount(Long noticeNo);

    void modifyNoticeArticle(NoticeDto noticeDto);


    default String getFileKind(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String extension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1).toLowerCase();
            String[] imageExtensions = {"jpg", "jpeg", "png", "gif"};
            for (String ext : imageExtensions) {
                if (ext.equals(extension)) {
                    return "I";
                }
            }
        }
        return "F";
    }


    default Notice noticeDtoToEntity(NoticeDto noticeDto){
        Date date = new Date();
        Timestamp today = new Timestamp(date.getTime());
        return Notice.builder().
                noticeNo(noticeDto.getNoticeNo()).
                noticeTitle(noticeDto.getNoticeTitle()).
                noticeWriter(noticeDto.getNoticeWriter()).
                noticeContent(noticeDto.getNoticeContent()).
                noticeCount(noticeDto.getNoticeCount()).
                noticeStatus(noticeDto.getNoticeStatus()).
                noticeRegdate(today).
                build();

    }

    default NoticeDto entityToNoticeDto(Notice notice){
        return NoticeDto.builder().noticeNo(notice.getNoticeNo()).
                noticeTitle(notice.getNoticeTitle()).
                noticeContent(notice.getNoticeContent()).
                noticeWriter(notice.getNoticeWriter()).
                noticeCount(notice.getNoticeCount()).
                noticeStatus(notice.getNoticeStatus()).
                noticeRegdate(notice.getNoticeRegdate()).
                build();
    }

    default NoticeArticleDto entityToNoticeArticleDto(Notice notice) {
        return NoticeArticleDto.builder()
                .noticeNo(notice.getNoticeNo())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .noticeCount(notice.getNoticeCount())
                .noticeRegdate(notice.getNoticeRegdate())
                .noticeStatus(notice.getNoticeStatus())
                .noticeFileDto(entityListToDtoList(notice.getNoticeFilesList()))
                .build();
    }

    default List<NoticeFilesDto> entityListToDtoList(List<NoticeFiles> noticeFileList) {
        if (noticeFileList == null) {
            return Collections.emptyList();
        }

        return noticeFileList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    default NoticeFilesDto entityToDto(NoticeFiles noticeFiles) {
        return NoticeFilesDto.builder()
                .noticeFileNo(noticeFiles.getNoticeFileNo())
                .noticeFileUrl(noticeFiles.getNoticeUrl())
                .noticeFileKind(noticeFiles.getNoticeFileKind())
                .noticeFileName(noticeFiles.getNoticeFileName())
                .build();
    }

}

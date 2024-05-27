package org.yeinp.animate.animate_backend.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yeinp.animate.animate_backend.entity.AdoptionImg;
import org.yeinp.animate.animate_backend.entity.AdoptionReview;
import org.yeinp.animate.animate_backend.entity.Notice;
import org.yeinp.animate.animate_backend.entity.NoticeFiles;
import org.yeinp.animate.animate_backend.notice.dto.NoticeArticleDto;
import org.yeinp.animate.animate_backend.notice.dto.NoticeDto;
import org.yeinp.animate.animate_backend.notice.repository.NoticeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    NoticeRepository noticeRepository;
    @Override
    public int writeNoticeArticle(NoticeDto noticeDto, List<String> imageUrls, String noticeFileKind, List<String> noticeFileNames) {
        Notice entity = noticeDtoToEntity(noticeDto);
        List<NoticeFiles> noticeFiles = new ArrayList<>();

        for (int i = 0; i < imageUrls.size(); i++) {
            String imageUrl = imageUrls.get(i);
            String fileKind = getFileKind(imageUrl);
            String fileName = noticeFileNames.get(i);
            noticeFiles.add(NoticeFiles.builder().notice(entity).noticeUrl(imageUrl).noticeFileKind(fileKind).noticeFileName(fileName).build());
        }

        entity.setNoticeFilesList(noticeFiles);
        noticeRepository.save(entity);
        return 0;
    }

    @Override
    public List<NoticeDto> getNoticeList() {
        List<Notice> noticeList = noticeRepository.findAllByOrderByNoticeRegdateDesc
                ();
        return noticeList.stream()
                .map(this::entityToNoticeDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NoticeArticleDto> getNoticeWithImages(Long noticeNo) {
        Optional<Notice> noticeArticleOptional = noticeRepository.findById(noticeNo);
        return noticeArticleOptional.map(noticeArticle -> {
            NoticeArticleDto noticeArticleDto = entityToNoticeArticleDto(noticeArticle);
            return noticeArticleDto;
        });
    }

    @Override
    public void increaseNoticeCount(Long noticeNo) {
        noticeRepository.increaseNoticeCount(noticeNo);
    }

    @Override
    public void modifyNoticeArticle(NoticeDto noticeDto) {
        Optional<Notice> result = noticeRepository.findById(noticeDto.getNoticeNo());
        if(result.isPresent()){
            Notice entity = result.get();
            entity.setNoticeContent(noticeDto.getNoticeContent());
            entity.setNoticeTitle(noticeDto.getNoticeTitle());
        }
    }

}

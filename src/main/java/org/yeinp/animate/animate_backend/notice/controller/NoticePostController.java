package org.yeinp.animate.animate_backend.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yeinp.animate.animate_backend.entity.Notice;
import org.yeinp.animate.animate_backend.notice.dto.NoticeDto;
import org.yeinp.animate.animate_backend.notice.repository.NoticeRepository;
import org.yeinp.animate.animate_backend.notice.service.NoticeService;

@Controller
@RequestMapping("/animate")
public class NoticePostController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    @Transactional
    @PostMapping("/notice/{noticeNo}/increaseCount")
    @ResponseBody
    public void increaseNoticeCount(@PathVariable Long noticeNo) {
        noticeService.increaseNoticeCount(noticeNo);
    }
    @Transactional
    @PostMapping("/notice/delete/{noticeNo}")
    public void deleteNotice(@PathVariable Long noticeNo){
        noticeRepository.deleteNoticeByNoticeNo(noticeNo);
    }

    @Transactional
    @PostMapping("/notice/modify/{noticeNo}")
    @ResponseBody
    public void modifyNotice(@PathVariable Long noticeNo) {

        noticeService.increaseNoticeCount(noticeNo);
    }

    @Transactional
    @PostMapping("/notice/modify/{noticeNo}")
    @ResponseBody
    public void modifyNotice(@PathVariable("noticeNo") Long noticeNo, @RequestBody NoticeDto noticeDto) {
        noticeDto.setNoticeNo(noticeNo);
        noticeService.modifyNoticeArticle(noticeDto);
    }


}

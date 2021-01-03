package com.example.studyroom.service;

import com.example.studyroom.pojo.Notice;
import com.example.studyroom.util.Page;

import java.util.List;

public interface NoticeService {
    int getTotal();
    List<Notice> listByPage(Page page);
    void addNotice(Notice notice);
    Notice getNoticeById(int id);
    void updateNotice(Notice notice);
    void delete(int id);
    List<Notice> findByPage(Notice notice, Page page);
    int totalFind(Notice notice);
}

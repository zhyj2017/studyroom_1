package com.example.studyroom.dao;

import com.example.studyroom.pojo.Notice;

import java.util.List;

public interface NoticeDAO {
    int getTotal();
    List<Notice> list(int start, int count);
    void addNotice(Notice notice);
    Notice getNoticeById(int id);
    void updateNotice(Notice notice);
    void delete(int id);
    List<Notice> find(Notice bean,int start, int count);
    int getFindTotal(Notice bean);
}

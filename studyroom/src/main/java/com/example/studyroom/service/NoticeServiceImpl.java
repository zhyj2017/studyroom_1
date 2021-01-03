package com.example.studyroom.service;

import com.example.studyroom.dao.NoticeDAO;
import com.example.studyroom.pojo.Notice;
import com.example.studyroom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDAO noticeDAO;

    public int getTotal(){
        int total=0;
        total=(int) noticeDAO.getTotal();
        return total;
    }

    public List<Notice> listByPage(Page page){
        List<Notice> notices = noticeDAO.list(page.getStart(),page.getCount());
        return notices;
    }

    public void addNotice(Notice notice){
        noticeDAO.addNotice(notice);
    }

    public Notice getNoticeById(int id){
        Notice notice = noticeDAO.getNoticeById(id);
        return notice;
    }

    public void updateNotice(Notice notice){
        noticeDAO.updateNotice(notice);
    }

    public void delete(int id){
        noticeDAO.delete(id);
    }

    public List<Notice> findByPage(Notice notice, Page page){
        List<Notice> notices = noticeDAO.find(notice,page.getStart(),page.getCount());
        return notices;
    }

    public int totalFind(Notice notice){
        int total=0;
        total=(int) noticeDAO.getFindTotal(notice);
        return total;
    }
}

package com.example.studyroom.service;

import com.example.studyroom.pojo.Credit;
import com.example.studyroom.util.Page;

import java.util.List;

public interface CreditService {
    Credit getCreditByUId(String uid);
    void addCredit(Credit credit);
    void updateCredit(Credit credit);
    List<Credit> listByPage(Page page);
    int getTotal();
    List<Credit> findByPage(Credit credit1, Credit credit2, Page page);
    int getFindTotal(Credit credit1, Credit credit2);
}

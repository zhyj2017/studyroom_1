package com.example.studyroom.dao;

import com.example.studyroom.pojo.Credit;

import java.util.List;

public interface CreditDAO {
    Credit getCreditByUId(String uid);
    void addCredit(Credit credit);
    void delete(String uid);
    void update(Credit credit);
    List<Credit> list(int start, int count);
    int getTotal();
    List<Credit> find(Credit bean1, Credit bean2, int start, int count);
    int getFindTotal(Credit bean1, Credit bean2);
}

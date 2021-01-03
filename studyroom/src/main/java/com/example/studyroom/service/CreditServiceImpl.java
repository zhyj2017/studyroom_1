package com.example.studyroom.service;

import com.example.studyroom.dao.CreditDAO;
import com.example.studyroom.pojo.Credit;
import com.example.studyroom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditDAO creditDAO;
    public Credit getCreditByUId(String uid){
        Credit credit = creditDAO.getCreditByUId(uid);
        return credit;
    }

    public void addCredit(Credit credit){
        creditDAO.addCredit(credit);
    }

    public void updateCredit(Credit credit){
        creditDAO.update(credit);
    }

    public List<Credit> listByPage(Page page){
        List<Credit> credits = creditDAO.list(page.getStart(),page.getCount());
        return credits;
    }

    public int getTotal(){
        int total=0;
        total=(int) creditDAO.getTotal();
        return total;
    }

    public List<Credit> findByPage(Credit credit1, Credit credit2, Page page){
        List<Credit> credits = creditDAO.find(credit1,credit2,page.getStart(),page.getCount());
        return credits;
    }

    public int getFindTotal(Credit credit1, Credit credit2){
        int total=0;
        total=(int) creditDAO.getFindTotal(credit1,credit2);
        return total;
    }
}

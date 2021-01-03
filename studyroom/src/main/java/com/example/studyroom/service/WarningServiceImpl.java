package com.example.studyroom.service;

import com.example.studyroom.dao.WarningDAO;
import com.example.studyroom.pojo.Warning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningServiceImpl implements WarningService{
    @Autowired
    private WarningDAO warningDAO;

    public boolean isWarning(String uid){
        Warning bean = null;
        bean = warningDAO.getWarningByUId(uid);
        if (bean != null){
            return true;
        }
        return false;
    }

    public void add(Warning warning){
        warningDAO.addWarning(warning);
    }

    public void delete(Warning warning){
        warningDAO.deleteWarning(warning);
    }

}

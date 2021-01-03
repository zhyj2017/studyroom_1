package com.example.studyroom.dao;

import com.example.studyroom.pojo.Warning;

public interface WarningDAO {
    Warning getWarningByUId(String uid);
    void addWarning(Warning warning);
    void deleteWarning(Warning warning);
}

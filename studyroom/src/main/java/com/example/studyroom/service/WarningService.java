package com.example.studyroom.service;

import com.example.studyroom.pojo.Warning;

public interface WarningService {
    boolean isWarning(String uid);
    void add(Warning warning);
    void delete(Warning warning);
}

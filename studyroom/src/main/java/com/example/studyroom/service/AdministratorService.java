package com.example.studyroom.service;

import com.example.studyroom.pojo.Administrator;

public interface AdministratorService {
    Administrator getAdministratorById(String id);
    boolean loginCheck(String id , String password);
}

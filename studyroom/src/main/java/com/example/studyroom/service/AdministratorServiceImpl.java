package com.example.studyroom.service;

import com.example.studyroom.dao.AdministratorDAO;
import com.example.studyroom.pojo.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorDAO administratorDAO;

    public Administrator getAdministratorById(String id){
        Administrator administrator = administratorDAO.getAdministratorById(id);
        return administrator;
    }

    public boolean loginCheck(String id , String password){
        Administrator administrator = administratorDAO.getAdministratorById(id);
        if (administrator == null){
            return false;
        } else if (!id.equals(administrator.getId())||!password.equals(administrator.getPassword())) {
            return false;
        }
        return true;
    }

}

package com.UL2012.API.Kardex.Service.Implementacion;
import com.UL2012.API.Kardex.Models.Dao.AdminsDao;
import com.UL2012.API.Kardex.Models.Entity.Admins;

import com.UL2012.API.Kardex.Service.INT_Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ImplAdmins implements INT_Admins {

    @Autowired
    private AdminsDao AdminsDao;

    public ImplAdmins(AdminsDao adminsDao) {
        AdminsDao = adminsDao;
    }

    @Transactional
    @Override
    public List<Object[]> Init_Session(String username, String password) {
        List<Object[]> results = AdminsDao.login(username, password);
        return  results;

    }

    @Override
    public Admins allAdmins() {
        return null;
    }

    @Override
    public Admins AdminByCodEmp(String CodEmp) {
        return null;
    }

    @Override
    public void DeleteAdminByID(String CodEmp) {

    }
}

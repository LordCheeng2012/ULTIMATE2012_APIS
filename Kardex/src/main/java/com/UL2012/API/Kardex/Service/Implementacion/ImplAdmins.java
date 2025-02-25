package com.UL2012.API.Kardex.Service.Implementacion;
import com.UL2012.API.Kardex.Models.Conexion.KardexSystemDb;
import com.UL2012.API.Kardex.Models.Dao.AdminsDao;
import com.UL2012.API.Kardex.Models.Entity.Admins;
import com.UL2012.API.Kardex.Models.Entity.Message;
import com.UL2012.API.Kardex.Service.INT_Admins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
@Service
public class ImplAdmins implements INT_Admins {

    @Autowired
    private AdminsDao AdminsDao;


    @Transactional
    @Override
    public List<String> Init_Session(String User, String Password) {
        return AdminsDao.Login_Service(User,Password);
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

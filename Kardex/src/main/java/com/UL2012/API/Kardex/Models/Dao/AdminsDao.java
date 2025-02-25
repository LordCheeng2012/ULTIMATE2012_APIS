package com.UL2012.API.Kardex.Models.Dao;
import com.UL2012.API.Kardex.Models.Entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminsDao extends JpaRepository<Admins, String> {

  @Procedure(name = "Admins.Login_Service")
    List<String> Login_Service(
            @Param("USERNAME") String User,
            @Param("U_Password") String Pass);

}

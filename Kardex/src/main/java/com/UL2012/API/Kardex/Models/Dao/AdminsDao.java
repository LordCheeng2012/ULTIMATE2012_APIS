package com.UL2012.API.Kardex.Models.Dao;
import com.UL2012.API.Kardex.Models.Entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminsDao extends JpaRepository<Admins, String> {
  @Query(value = "CALL Login_Service(:username, :password)", nativeQuery = true)
  List<Object[]> login(@Param("username") String username,
                       @Param("password") String password);


}


package com.UL2012.API.KardexTest;
import com.UL2012.API.Kardex.Models.Dao.AdminsDao;
import com.UL2012.API.Kardex.Service.Implementacion.ImplAdmins;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class KardexApplicationTests  {

	@Test
	public void testInit_Session() {
		//given
		AdminsDao dat = mock(AdminsDao.class) ;
		ImplAdmins adm = new ImplAdmins(dat);
		//when
		List<Object[]> MockRes= new ArrayList<>();
		MockRes.add(new Object[]{"ERR02","Warnig","invalidos","datos invalidos","No data"});
		when(dat.login("admin", "admin")).
				thenReturn(MockRes);

		//then
		List<Object[]> data = adm.Init_Session("admin", "admin");
		assertNotNull(data);
		assertFalse(data.isEmpty());
		assertEquals("ERR02", data.get(0)[0]);



		System.out.println("Test Init_Session passed");
	}
}

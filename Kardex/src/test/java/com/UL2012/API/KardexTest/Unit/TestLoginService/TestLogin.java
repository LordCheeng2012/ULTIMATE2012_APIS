package com.UL2012.API.KardexTest.Unit.TestLoginService;
import com.UL2012.API.Kardex.Models.Dao.AdminsDao;
import com.UL2012.API.Kardex.Utils.Message;
import com.UL2012.API.Kardex.Service.Implementacion.ImplAdmins;
import com.UL2012.API.KardexTest.Unit.DataProviders;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class TestLogin {
    private AdminsDao dao = mock(AdminsDao.class);
    private ImplAdmins AD = new ImplAdmins(dao);
    @Test
    public void TestInitSession() {
        //given
        //llamar a las propiedades y Mockear las dependencias
        //when
        when(dao.login("admin", "123")).thenReturn(DataProviders.ResponseLogin("success"));
        List<Object[]> result = AD.Init_Session("admin", "123");
        //then
        List<Message> msg =Message.ResponseMessage(result);
        assertEquals(msg.get(0).getCod_Msg(),"SUC02");
        assertEquals(msg.get(0).getType(),"Succes");
        assertEquals(msg.get(0).getTitle(),"Componente Succes");
        assertEquals(msg.get(0).getMessage(),"La prueba unitaria paso");
        assertEquals(msg.get(0).getData(),"SUCC01");

    }

}

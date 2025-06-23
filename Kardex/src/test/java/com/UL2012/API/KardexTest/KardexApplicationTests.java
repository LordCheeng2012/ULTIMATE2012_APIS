package com.UL2012.API.KardexTest;
import com.intuit.karate.junit5.Karate;


class KardexApplicationTests  {
	//configurar el metodo de prueba para ejecutar test mediente su etiqueta
    @Karate.Test
    Karate testSample() {
        return Karate.run().relativeTo(getClass());
    }

    @Karate.Test
    Karate testTags() {
        return Karate.run().tags("~ignore").relativeTo(getClass());
    }




}

package com.UL2012.API.Kardex.Controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/Test")
public class Test {
    @GetMapping("/Hellow")
    public String Test(){
    return "Hola Moises";
    }
}

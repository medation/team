package com.sbc.projet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjetController{

    @GetMapping("/")
    public void test() {
        System.out.println("Appel Test !");
    }

}

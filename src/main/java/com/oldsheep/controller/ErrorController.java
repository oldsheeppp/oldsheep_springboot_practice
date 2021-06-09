package com.oldsheep.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @RequestMapping("/error/403")
    public String error403() {
        return "你妈死了";
    }
}

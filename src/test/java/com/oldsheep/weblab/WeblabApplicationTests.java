package com.oldsheep.weblab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class WeblabApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void test() {
        String encodePassword = passwordEncoder.encode("123");
        System.out.println(encodePassword);
    }

}

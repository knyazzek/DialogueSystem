package com.sstu;

import com.sstu.prolog.Eliza;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        Eliza eliza = new Eliza();
    }
}
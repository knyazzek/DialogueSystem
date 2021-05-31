package com.sstu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dialogue")
public class DialogueController {

    @GetMapping
    public String homePage() {
        return "dialogue";
    }
}

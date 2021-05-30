package com.sstu.controller;

import com.sstu.prolog.Eliza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

    @Autowired
    Eliza eliza;

    @PostMapping("start_dialogue")
    public String startDialogue() {
        eliza.startDialogue();
    }

    @GetMapping("get_answer")
    public String getAnswer() {
        eliza
    }
}
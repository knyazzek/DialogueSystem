package com.sstu.controller;

import com.sstu.prolog.Eliza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

    @Autowired
    Eliza eliza;

    @GetMapping("/start_dialogue")
    public String startDialogue() {
        return eliza.startDialogue();
    }

    @GetMapping("/get_answer")
    public String getAnswer(String sentence) {
        System.out.println(sentence);
        return eliza.getNextAnswer(sentence);
    }
}
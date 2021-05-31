package com.sstu.prolog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Eliza {
    @Autowired
    private JPL jpl;

    public String startDialogue() {
        return "Рада вас видеть здесь. Я как раз хотела поговорить с вами о еде. " +
                "Расскажите мне, пожалуйста, что вы ели в близжайшие пару дней?";
    }

    public String getNextAnswer(String sentence) {
        return jpl.getAnswerBySentence(sentence);
    }
}
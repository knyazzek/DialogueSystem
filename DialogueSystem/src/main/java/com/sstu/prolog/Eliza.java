package com.sstu.prolog;

import org.springframework.stereotype.Component;

@Component
public class Eliza {
    private final JPL jpl;

    public Eliza() {
        this.jpl = new JPL();
    }

    public String startDialogue() {
        jpl.setUp();
        return "Рада вас видеть здесь. Я как раз хотела поговорить с вами о еде. " +
                "Расскажите мне, пожалуйста, что вы ели в близжайшие пару дней?";
    }

    public String getNextAnswer(String sentence) {
        return jpl.getAnswerBySentence(sentence);
    }
}
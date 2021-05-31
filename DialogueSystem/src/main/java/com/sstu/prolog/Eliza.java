package com.sstu.prolog;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Eliza {
    private final Scanner scanner;
    private final JPL jpl;

    public Eliza() {
        this.scanner = new Scanner(System.in);
        this.jpl = new JPL();
    }

    public String startDialogue() {
        jpl.setUp();
        return "Что вы ели в близжайшие пару дней?";
    }

    public String getNextAnswer(String sentence) {
        return jpl.getAnswerBySentence(sentence);
    }

/*    public void startDialogue() {
        jpl.setUp();
        System.out.println("Eliza>Что вы ели в близжайшие пару дней?");
        System.out.print("You>");
        dialogue(scanner.nextLine());
    }

    private void dialogue(String sentence) {
        System.out.println("Eliza>" + jpl.getAnswerBySentence(sentence));
        System.out.print("You>");
        dialogue(scanner.nextLine());
    }*/
}
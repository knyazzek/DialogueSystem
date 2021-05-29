package com.sstu;

import java.util.Scanner;

public class Eliza {
    final private static Scanner scanner = new Scanner(System.in);

    public void startDialogue() {
        JPL.setUp();
        System.out.println("Eliza>Что вы ели в близжайшие пару дней?");
        System.out.print("You>");
        dialogue(scanner.nextLine());
    }

    private void dialogue(String sentence) {
        System.out.println("Eliza>" + JPL.getAnswerBySentence(sentence));
        System.out.print("You>");
        dialogue(scanner.nextLine());
    }
}
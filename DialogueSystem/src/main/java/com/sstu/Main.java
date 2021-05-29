package com.sstu;

import java.io.IOException;


public class Main {
    public static void main (String[] args) throws IOException {
        Eliza eliza = new Eliza();
        eliza.startDialogue();
    }
}
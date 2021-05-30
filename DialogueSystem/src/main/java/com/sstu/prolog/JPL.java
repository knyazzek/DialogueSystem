package com.sstu.prolog;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import java.util.*;

public class JPL {
    private final String FILE_PATH;
    private final Map<Integer, List<String>> ANSWERS;

    private Integer currentExchange = 0;

    public JPL() {
        FILE_PATH = "D:/University/6 semester" +
                "/Mathematical Foundations of Artificial Intelligence" +
                "/Coursework/v2/Prolog/core.pl";
        ANSWERS = new HashMap<>();
    }

    public void setUp() {
        Query query = new Query("consult", new Term[]{new Atom(FILE_PATH)});
        System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
    }

    public String getAnswerBySentence(String sentence) {
        Atom atom = new Atom(sentence);
        Variable Answer = new Variable("Answers");

        Query query = new Query("get_answers_by_sentence",
                new Term[] {atom, Answer});

        String res;

        try {
            Map<String, Term> solution = query.nextSolution();
            Term[] answersTerms = solution.get("Answers").listToTermArray();
            List<String> answersStrings = new LinkedList<>();

            for (Term answer : answersTerms) {
                answersStrings.add(answer.toString());
            }

            ANSWERS.put(currentExchange, answersStrings);
            res = ANSWERS.get(currentExchange).get(0);
            ANSWERS.get(currentExchange).remove(0);

        } catch (Exception ex) {
            res = "Couldn't find solution";
            ex.printStackTrace();
        }

        currentExchange++;
        return res;
    }
}

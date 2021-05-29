package com.sstu;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import java.util.Map;

public class JPL {
    final static String FILE_PATH = "D:/University/6 semester" +
            "/Mathematical Foundations of Artificial Intelligence" +
            "/Coursework/v2/Prolog/core.pl";

    public static void setUp() {
        Query query = new Query("consult", new Term[]{new Atom(FILE_PATH)});
        System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
    }

    public static String getAnswerBySentence(String sentence) {
        Atom atom = new Atom(sentence);
        Variable Answer = new Variable("Answer");

        Query query = new Query("get_answer_by_sentence",
                new Term[] {atom, Answer});

        String res;

        try {
            Map<String, Term> solution = query.nextSolution();
            String tmp = solution.get("Answer").toString();
            res = tmp.substring(1, tmp.length() - 1);
        } catch (Exception ex) {
            res = "Couldn't find solution";
            //ex.printStackTrace();
        }
        return res;
    }
}

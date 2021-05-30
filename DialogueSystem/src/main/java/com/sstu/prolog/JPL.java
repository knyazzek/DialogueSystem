package com.sstu.prolog;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import java.util.*;

public class JPL {
    private final String FILE_PATH;
    //private final Map<Integer, List<String>> ANSWERS;
    private List<String> userAnswers;

    private Integer currentExchange = 0;
    private final List<String> INTRODUCTORY_WORDS;

    public JPL() {
        FILE_PATH = "D:/University/6 semester" +
                "/Mathematical Foundations of Artificial Intelligence" +
                "/Coursework/v2/Prolog/core.pl";
        //ANSWERS = new HashMap<>();
        userAnswers = new ArrayList<>();
        INTRODUCTORY_WORDS = new ArrayList<>(Arrays.asList(
                "Кстати... ",
                "Забыла вас спросить... ",
                "Возвращаясь к предыдущей теме... ",
                "Мы уже говорили об этом выше, но... "
        ));
    }

    public void setUp() {
        Query query = new Query("consult", new Term[]{new Atom(FILE_PATH)});
        System.out.println( "consult " + (query.hasSolution() ? "succeeded" : "failed"));
    }

    public String getAnswerBySentence(String sentence) {
        Atom atom = new Atom(sentence);
        Variable Answer = new Variable("Answer");

        Query query = new Query("get_answers_by_sentence",
                new Term[] {atom, Answer});

        StringBuilder res = new StringBuilder();

        try {
            res.append(query.nextSolution().get("Answer").toString());

            if (currentExchange != userAnswers.size()) {
                String introductory_word = INTRODUCTORY_WORDS.get(
                        userAnswers.size() % INTRODUCTORY_WORDS.size());

                res.insert(0, introductory_word);
            }

            userAnswers.add(sentence);
            currentExchange = userAnswers.size();
/*            Map<String, Term> solution = query.nextSolution();
            Term[] answersTerms = solution.get("Answers").listToTermArray();
            List<String> answersStrings = new LinkedList<>();

            for (Term answer : answersTerms) {
                answersStrings.add(answer.toString());
            }

            ANSWERS.put(currentExchange, answersStrings);
            res = ANSWERS.get(currentExchange).get(0);
            ANSWERS.get(currentExchange).remove(0);*/

        } catch (Exception ex) {
/*            System.out.println("ERROR : " + sentence);
            ex.printStackTrace();*/
            currentExchange--;
            if (currentExchange >= 0) {
                res.append(getAnswerBySentence(userAnswers.get(currentExchange)));
            } else {
                res.append("Я даже не знаю что и сказать...");
            }
        }
        return res.toString().replace("'","");
    }
}

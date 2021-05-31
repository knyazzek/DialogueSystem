package com.sstu.prolog;

import com.sstu.excel.ExcelUtils;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JPL {
    private final String FILE_PATH;
    private List<String> userAnswers;

    private Integer currentExchange = 0;
    private final List<String> INTRODUCTORY_WORDS;

    public JPL() {
        FILE_PATH = "D:/University/6 semester" +
                "/Mathematical Foundations of Artificial Intelligence" +
                "/Coursework/v2/Prolog/core.pl";
        userAnswers = new ArrayList<>();
        INTRODUCTORY_WORDS = new ArrayList<>(Arrays.asList(
                "Кстати... ",
                "Забыла вас спросить... ",
                "Возвращаясь к предыдущей теме... ",
                "Мы уже говорили об этом выше, но... "
        ));

        setUp();
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
        } catch (Exception ex) {
            currentExchange--;
            if (currentExchange >= 0) {
                res.append(getAnswerBySentence(userAnswers.get(currentExchange)));
            } else {
                res.append("impasse");
            }
        }
        return res.toString().replace("'","");
    }

    public int getFirstFreeIndex() {
        Variable Index = new Variable("Index");
        Query query = new Query("get_first_free_index",
                new Term[] {Index});

        String indexString = query.nextSolution().get("Index").toString();
        return Integer.parseInt(indexString);
    }

    public void addKeyword(String keyword, int index) {
        try {
            keyword = ExcelUtils.getWordWithoutEnding(keyword);
            Atom Keyword = new Atom(keyword);
            org.jpl7.Integer Index = new org.jpl7.Integer(index);
            Query query = new Query("add_new_keyword",
                    new Term[] {Keyword, Index});

            query.nextSolution().get("Index");
        } catch (Exception ex) {
            System.out.println("Couldn't add predicate : " + keyword);
        }
    }

    public void addAssociation(String association, int index) {
        
    }

    public void addAnswer(String answer, int index) {

    }
}

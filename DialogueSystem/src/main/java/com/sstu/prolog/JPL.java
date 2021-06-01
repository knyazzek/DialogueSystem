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

            System.err.println(res);

            if (currentExchange != userAnswers.size()) {
                String introductory_word = INTRODUCTORY_WORDS.get(
                        userAnswers.size() % INTRODUCTORY_WORDS.size());

                res.insert(0, introductory_word);
            }
            if (!userAnswers.contains(sentence))
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

    public String addKeyword(String keyword, int index) {
        if (keyword.equals("")) return "error";
        try {
            keyword = ExcelUtils.getWordWithoutEnding(keyword);
            Atom Keyword = new Atom(keyword);
            org.jpl7.Integer Index = new org.jpl7.Integer(index);
            Query query = new Query("add_new_keyword",
                    new Term[] {Keyword, Index});

            query.nextSolution();
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Couldn't add keyword : " + keyword);
            return "error";
        }
    }

    public String addAssociation(String association, int index) {
        if (association.equals("")) return "error";
        try {
            association = ExcelUtils.getWordWithoutEnding(association);
            Atom Association = new Atom(association);
            org.jpl7.Integer Index = new org.jpl7.Integer(index);
            Query query = new Query("add_new_association",
                    new Term[] {Association, Index});

            query.nextSolution().get("Index");
            return "ok";
        } catch (Exception ex) {
            System.out.println("Couldn't add association : " + association);
            return "error";
        }
    }

    public String addAnswer(String answer, int index) {
        if (answer.equals("")) return "error";
        try {
            answer = ExcelUtils.getWordWithoutEnding(answer);
            Atom Answer = new Atom(answer);
            org.jpl7.Integer Index = new org.jpl7.Integer(index);
            Query query = new Query("add_new_answer",
                    new Term[] {Index, Answer});

            query.nextSolution();
            System.out.println("Adding predicates complete");
            return "ok";
        } catch (Exception ex) {
            System.out.println("Couldn't add answer : " + answer);
            return "error";
        }
    }

    public String delKeyword(String keyword) {
        try {
            Atom Keyword = new Atom(keyword);
            Query query = new Query("del_keyword",
                    new Term[] {Keyword});

            query.nextSolution();
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Couldn't delete keyword : " + keyword);
            return "error";
        }
    }

    public String delAssociation(String association) {
        try {
            Atom Keyword = new Atom(association);
            Query query = new Query("del_association",
                    new Term[] {Keyword});

            query.nextSolution();
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Couldn't delete keyword : " + association);
            return "error";
        }
    }

    public String delAnswer(String answer) {
        try {
            Atom Keyword = new Atom(answer);
            Query query = new Query("del_answer",
                    new Term[] {Keyword});

            query.nextSolution();
            return "ok";
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Couldn't delete keyword : " + answer);
            return "error";
        }
    }

}

package com.sstu.controller;

import com.sstu.domain.Predicate;
import com.sstu.excel.ExcelUtils;
import com.sstu.prolog.JPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditRestController {

    @Autowired
    JPL jpl;

    @PostMapping("add_predicate")
    public String addPredicate(@RequestBody Predicate predicate) {
        System.out.println(predicate);
        int index = jpl.getFirstFreeIndex();

        for (String keyword : predicate.getKeywords()) {
            if (jpl.addKeyword(keyword, index).equals("error"))
                return "error";
        }

        for (String association : predicate.getAssociations()) {
            if (jpl.addAssociation(association, index).equals("error"))
                return "error";
        }

        for (String answer : predicate.getAnswers()) {
            if (jpl.addAnswer(answer, index).equals("error"))
                return "error";
        }
        return "ok";
    }

    @PostMapping("del_keyword_predicate")
    public String delKeywordPredicate(String keyword) {
        if (!keyword.equals("")) {
            return jpl.delKeyword(keyword);
        } else {
            return "error";
        }
    }

    @PostMapping("del_association_predicate")
    public String delAssociationPredicate(String association) {
        if (!association.equals("")) {
            return jpl.delAssociation(association);
        } else {
            return "error";
        }
    }

    @PostMapping("del_answer_predicate")
    public String delAnswerPredicate(String answer) {
        if (!answer.equals("")) {
            return jpl.delAnswer(answer);
        } else {
            return "error";
        }
    }
}

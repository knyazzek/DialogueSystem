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
    public void addPredicate(@RequestBody Predicate predicate) {
        System.out.println(predicate);
        int index = jpl.getFirstFreeIndex();

        for (String keyword : predicate.getKeywords()) {
            jpl.addKeyword(keyword, index);
        }

        for (String association : predicate.getAssociations()) {
            jpl.addAssociation(association, index);
        }

        for (String answer : predicate.getAnswers()) {
            jpl.addAnswer(answer, index);
        }

        System.out.println("Adding predicates complete");
    }
}

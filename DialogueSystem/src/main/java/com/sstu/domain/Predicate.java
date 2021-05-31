package com.sstu.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Predicate {
    private String[] keywords;
    private String[] associations;
    private String[] answers;
}
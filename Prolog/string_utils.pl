chop_list([_], []):-!.
chop_list([X|Xs], [X|WithoutLast]) :-
    chop_list(Xs, WithoutLast).


chop_string(String, ChoppedString):-
    string_chars(String, Chars),
    chop_list(Chars, ChoppedChars),
    string_chars(ChoppedString, ChoppedChars).

del_substring(Substring,String,Res):-re_match(Substring,String),
    re_replace(Substring,"",String,R),
    del_substring(Substring,R, Res).

del_substring(Substring,String,String):-re_match(Substring,String)\=true.



/*
****************************************************************
                 Delete element from string
****************************************************************
*/


del_elem_from_list(Elem, List, Res):-
    atomic(List) \= true,
    del_complex_elem_from_string(Elem, List, ResTmp),
    del_simple_element_from_list(Elem, ResTmp, Res),!.

del_elem_from_list(Elem, List, Res):-
    del_simple_element_from_list(Elem, List, Res),!.

del_simple_element_from_list(_, [], []):-!.

del_simple_element_from_list(Elem, [Elem|T], Res):-
    del_simple_element_from_list(Elem, T, Res).

del_simple_element_from_list(Elem, [H|T], [H|Res]):-
    Elem \= H,
    del_simple_element_from_list(Elem, T, Res).

del_complex_elem_from_string([W1,W2|_], List , Res):-
    del_simple_element_from_list(W1, List, ResTmp),
    del_simple_element_from_list(W2, ResTmp, Res).



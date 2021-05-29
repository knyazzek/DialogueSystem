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


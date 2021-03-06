get_all_substrings(String, Res):-
    normalize_space(string(TrimmedString), String),
    split_string(TrimmedString, SimpleSubstrings),
    get_all_complex_substrings(SimpleSubstrings, ComplexSubstrings),
    append(ComplexSubstrings, SimpleSubstrings, Res),!.

get_all_complex_substrings([Word1,Word2|OtherWords], [[Word1,Word2]|T]):-
    %string_concat(Word1, " ", Htmp),
    %string_concat(Htmp, Word2, H),
    get_all_complex_substrings([Word2|OtherWords],T).

get_all_complex_substrings(_,[]):-!.

split_string(String, Res):-string_lower(String, LCString),
    split_string(LCString, "\s", ",!.?", Res).

del_substring(Substring,String,Res):-re_match(Substring,String),
    re_replace(Substring,"",String,R),
    del_substring(Substring,R, Res).

del_substring(Substring,String,String):-re_match(Substring,String)\=true.

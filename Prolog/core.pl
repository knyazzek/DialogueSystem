:-[string_utils].
:-[read_line].
:-[keywords].
:-[associations].
:-[answers].


terminal_keyword("íå çíàþ", 300).
terminal_keyword(["íå", "çíàþ"], 300).
%answers(300, "Ïî÷åìó?").

/*
****************************************************************
                       GetAnswerByKeyword
****************************************************************
*/


%Get answer by keyword for one word

get_answer_by_keyword(String, Answer):-
    keyword(String, AnswerIndex),
    get_answer(AnswerIndex, Answer),!.

get_answer_by_keyword(String, Answer):-
    atomic(String),
    chop_string(String, ChoppedString),
    get_answer_by_keyword(ChoppedString, Answer).



%Get answer by keyword for two words


get_answer_by_keyword([W1,W2|_], Answer):-
    get_answer_by_chopping_second_word_by_keyword([W1,W2], Answer),!.

get_answer_by_keyword([W1,W2|_], Answer):-
    chop_string(W1, ChoppedW1),
    get_answer_by_keyword([ChoppedW1,W2], Answer),!.

get_answer_by_chopping_second_word_by_keyword(Strings, Answer):-
    keyword(Strings, AnswerIndex),
    get_answer(AnswerIndex, Answer),!.

get_answer_by_chopping_second_word_by_keyword([W1,W2|_], Answer):-
    chop_string(W2, ChoppedW2),
    get_answer_by_chopping_second_word_by_keyword([W1,ChoppedW2], Answer).


/*
****************************************************************
                       GetAnswerByAssociation
****************************************************************
*/


%Get answer by association for one word


get_answer_by_association(String, Answer):-
    association(String, AnswerIndex),
    get_answer(AnswerIndex,Answer),!.

get_answer_by_association(String, Answer):-
    atomic(String),
    chop_string(String, ChoppedString),
    get_answer_by_association(ChoppedString, Answer).



%Get answer by association for two words


get_answer_by_association([W1,W2|_], Answer):-
    get_answer_by_chopping_second_word_by_association([W1,W2], Answer),!.

get_answer_by_association([W1,W2|_], Answer):-
    chop_string(W1, ChoppedW1),
    get_answer_by_association([ChoppedW1,W2], Answer),!.

get_answer_by_chopping_second_word_by_association(Strings, Answer):-
    association(Strings, AnswerIndex),
    get_answer(AnswerIndex, Answer),!.

get_answer_by_chopping_second_word_by_association([W1,W2|_], Answer):-
    chop_string(W2, ChoppedW2),
    get_answer_by_chopping_second_word_by_association([W1,ChoppedW2], Answer).


/*
****************************************************************
                     GetAnswerByTerminalKeyword
****************************************************************
*/


%Get answer by terminal keyword for one word


get_answer_by_terminal_keyword(String, Answer):-
    terminal_keyword(String, AnswerIndex),
    get_answer(AnswerIndex, Answer),!.

get_answer_by_terminal_keyword(String, Answer):-
    atomic(String),
    chop_string(String, ChoppedString),
    get_answer_by_terminal_keyword(ChoppedString, Answer).



%Get answer by terminal keyword for two words


get_answer_by_terminal_keyword([W1,W2|_], Answer):-
    get_answer_by_chopping_second_word_by_terminal_keyword([W1,W2], Answer),!.

get_answer_by_terminal_keyword([W1,W2|_], Answer):-
    chop_string(W1, ChoppedW1),
    get_answer_by_terminal_keyword([ChoppedW1,W2], Answer),!.

get_answer_by_chopping_second_word_by_terminal_keyword(Strings, Answer):-
    terminal_keyword(Strings, AnswerIndex),
    get_answer(AnswerIndex, Answer),!.

get_answer_by_chopping_second_word_by_terminal_keyword([W1,W2|_], Answer):-
    chop_string(W2, ChoppedW2),
    get_answer_by_chopping_second_word_by_terminal_keyword([W1,ChoppedW2], Answer).


/*
****************************************************************
                     Get answer by sentence
****************************************************************
*/

get_answers_by_sentence(Atom, Answers):-
    atom_string(Atom, Sentence),
    get_all_substrings(Sentence, Substrings),
    get_answer_by_string_list(Substrings, Answers, _).

%Get answer list

/*get_answers_list_by_string_list([], []):-!.

get_answers_list_by_string_list(Strings, [Answer|OtherAnswers]):-
    get_answer_by_string_list(Strings, Answer, FoundWord),
    del_elem_from_list(FoundWord, Strings, StringWithoutFoundWord),
    get_answers_list_by_string_list(StringWithoutFoundWord, OtherAnswers),!.

get_answers_list_by_string_list(Strings,[]):-
    get_answer_by_string_list(Strings, _, _) \= true, !.*/

%Get answer

get_answer_by_string_list(Strings, Answer, FoundWord):-
    member(FoundWord, Strings),
    get_answer_by_keyword(FoundWord, Answer),!.

get_answer_by_string_list(Strings, Answer, FoundWord):-
    member(FoundWord, Strings),
    get_answer_by_association(FoundWord, Answer),!.

get_answer_by_string_list(Strings, Answer, FoundWord):-
    member(FoundWord, Strings),
    get_answer_by_terminal_keyword(FoundWord, Answer),!.

get_answer_by_string_list([], "Âû íè÷åãî íå ââåëè...", ""):-!.


/*
****************************************************************
                         Get answer
****************************************************************
*/


get_answer(AnswerIndex, Answer):-
    answers(AnswerIndex, [Answer|_]),
    refresh_answers(AnswerIndex),!.

refresh_answers(Index):-
    retract(answers(Index, [_|T])),
    assert(answers(Index, T)).

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



/*
****************************************************************
                       GetAll
****************************************************************
*/

 
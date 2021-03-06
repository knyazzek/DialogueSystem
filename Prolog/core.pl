:-set_prolog_flag(encoding, utf8).

:-[string_utils].
:-[read_line].
:-[keywords].
:-[associations].
:-[answers].
:-[stop_words].
:-[save].

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
                Get answer by stop word
****************************************************************
*/

get_answer_by_stop_word(String, "stop"):-
    stop_word(String),!.

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
    get_answer_by_stop_word(FoundWord, Answer),!.

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
                        Get all
****************************************************************
*/

%% get_all(Keyword, Associations, Answers):-
%%     keyword(Keyword, AnswersIndex),
%%     get_all_association(AnswersIndex, )


%% get_all_association(AnswersIndex, Res):-


/*
****************************************************************
                    List utils
****************************************************************
*/

join_lists(A, B, R):-
    append([A], [B], Tmp),
    flatten(Tmp, R).


/*
****************************************************************
                    Get first free index
****************************************************************
*/

get_first_free_index(R):-
    get_first_free_index(1, R),!.    
    

get_first_free_index(CurrentIndex, R):-
    keyword(_, CurrentIndex),
    NextIndex is CurrentIndex + 1,
    get_first_free_index(NextIndex, R).

get_first_free_index(CurrentIndex, CurrentIndex):-
    keyword(CurrentIndex, _) \= true,!.

:-[string_utils].
:-[read_line].
:-[keywords].
:-[associations].
:-[answers].


%terminal_keyword("�� ����", 300).
%terminal_keyword(["��", "����"], 300).
%answers(300, "������?").

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

get_answer_by_sentence(Atom, Answer):-
    atom_string(Atom, Sentence),
    get_all_substrings(Sentence, Substrings),
    get_answer_by_string_list(Substrings, Answer).

get_answer_by_string_list(Strings, Answer):-
    member(String, Strings),
    get_answer_by_keyword(String, Answer),!.

get_answer_by_string_list(Strings, Answer):-
    member(Substrings, Strings),
    get_answer_by_association(Substrings, Answer),!.

get_answer_by_string_list(Strings, Answer):-
    member(Substrings, Strings),
    get_answer_by_terminal_keyword(Substrings, Answer),!.

get_answer_by_string_list("", "�� ������ �� �����..."):-!.


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
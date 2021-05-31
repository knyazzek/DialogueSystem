:-set_prolog_flag(encoding, utf8).
/*
****************************************************************
                            Save
****************************************************************
*/

project_folder('D:/University/6 semester/Mathematical Foundations of Artificial Intelligence/Coursework/v2/Prolog/').

save_all(Term, FileName):-
    project_folder(FolderPath),
    atom_concat(FolderPath, FileName, FilePath),
    tell(FilePath),
    listing(Term),
    told.

save_term(Term, FileName):-
    project_folder(FolderPath),
    atom_concat(FolderPath, FileName, FilePath),
    open(FilePath, append, Stream),
    write(Stream, Term),
    put_char(Stream,.),
    nl(Stream),
    close(Stream).


/*
****************************************************************
                    Adding new predicates
****************************************************************
*/

add_new_keyword(Keyword, AnswersIndex):-
    assert(keyword(Keyword, AnswersIndex)),
    save_all(keyword, 'keywords.pl').

add_new_association(Association, AnswersIndex):-
    assert(Association, AnswersIndex),
    save_term(association(Association, AnswersIndex), 'associations.pl').

add_new_answers(AnswersIndex, NewAnswers):-
    association(AnswersIndex, OldAnswers),
    retract(association(AnswersIndex, _)),
    join_lists(NewAnswers, OldAnswers, Answers),
    assert(association(AnswersIndex, Answers)),
    save_term(answers(AnswersIndex, Answers), 'answers.pl').

% If specified index doesnt exist

add_new_answers(AnswersIndex, Answers):-
    flatten([Answers], Res),
    assert(association(AnswersIndex, Res)),
    save_term(answers(AnswersIndex, Answers), 'answers.pl').


/*
****************************************************************
                    Deleting predicates
****************************************************************
*/

del_keyword(Keyword):-
    keyword(Keyword, AnswersIndex),
    retract(keyword(Keyword, AnswersIndex)),
    save_all(keyword, 'keywords.pl').

del_assotiation(Association):-
    association(Association, AnswersIndex),
    retract(association(Association,AnswersIndex)),
    save_all(association, 'associations.pl').

del_answer(Answer):-
    answers(AnswersIndex, Answers),
    member(Answer, Answers),
    delete(Answers, Answer, Res),
    retract(answers(AnswersIndex, Answers)),
    assert(answers(AnswersIndex, Res)),
    save_all(answers, 'answers.pl').

del_answers_by_index(AnswersIndex):-
    answers(AnswersIndex, Answers),
    retract(answers(AnswersIndex, Answers)),
    save_all(answers, 'answers.pl').

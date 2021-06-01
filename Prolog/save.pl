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
    write(':- encoding(utf8).\n\n'),
    told,
    append(FilePath),
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

add_new_keyword(KeywordAtom, AnswersIndex):-
    atom_string(KeywordAtom, Keyword),
    assert(keyword(Keyword, AnswersIndex)),
    save_all(keyword, 'keywords.pl'),!.

add_new_association(AssociationAtom, AnswersIndex):-
    atom_string(AssociationAtom, Association),
    assert(association(Association, AnswersIndex)),
    save_all(association, 'associations.pl'),!.

add_new_answer(AnswersIndex, NewAnswerAtom):-
    atom_string(NewAnswerAtom, NewAnswer),
    answers(AnswersIndex, OldAnswers),
    retract(answers(AnswersIndex, OldAnswers)),
    join_lists(NewAnswer, OldAnswers, Answers),
    assert(answers(AnswersIndex, Answers)),
    save_all(answers, 'answers.pl'),!.

% If specified index doesnt exist

add_new_answer(AnswersIndex, AnswerAtom):-
    atom_string(AnswerAtom, Answer),
    answers(AnswersIndex, _) \= true,
    flatten([Answer], Res),
    assert(answers(AnswersIndex, Res)),
    save_all(answers, 'answers.pl').


/*
****************************************************************
                    Deleting predicates
****************************************************************
*/

del_keyword(KeywordAtom):-
    atom_string(KeywordAtom, Keyword),
    keyword(Keyword, AnswersIndex),
    retract(keyword(Keyword, AnswersIndex)),
    save_all(keyword, 'keywords.pl'),!.

del_association(AssociationAtom):-
    atom_string(AssociationAtom, Association),
    association(Association, AnswersIndex),
    retract(association(Association,AnswersIndex)),
    save_all(association, 'associations.pl'),!.

del_answer(AnswerAtom):-
    atom_string(AnswerAtom, Answer),
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

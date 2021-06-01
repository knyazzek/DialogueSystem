function delKeyword() {
    let keyword = document.getElementById("delKeyword").value;
    console.log(keyword);

    $.post('/del_keyword_predicate',
        {keyword: keyword},
        function (data) {
            if (data.toString() == "error") {
                alert("Couldn't delete specified message. There is no such keyword in the knowledge base")
            }
            if ((data.toString() == "ok")) {
                alert("Keyword was successfully deleted.")
            }
        }
    )
}

function delAssociation() {
    let association = document.getElementById("delAssociation").value;
    console.log(association);

    $.post('/del_association_predicate',
        {association: association},
        function (data) {
            if (data.toString() == "error") {
                alert("Couldn't delete specified message. There is no such association in the knowledge base")
            }
            if ((data.toString() == "ok")) {
                alert("Keyword was successfully deleted.")
            }
        }
    )
}

function delAnswer() {
    let answer = document.getElementById("delAnswer").value;
    console.log(answer);

    $.post('/del_answer_predicate',
        {answer: answer},
        function (data) {
            if (data.toString() == "error") {
                alert("Couldn't delete specified message. There is no such answer in the knowledge base")
            }
            if ((data.toString() == "ok")) {
                alert("Keyword was successfully deleted.")
            }
        }
    )
}
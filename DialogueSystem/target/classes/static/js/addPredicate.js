function addKeywordInput() {
    let elem = document.getElementById('keywords');
    let newInputElem = document.createElement('input');
    newInputElem.className = "keyword";
    elem.appendChild(newInputElem);
    newInputElem.placeholder = "keyword";
}

function addAssociationInput() {
    let elem = document.getElementById('associations');
    let newInputElem = document.createElement('input');
    newInputElem.className = "association";
    elem.appendChild(newInputElem);
    newInputElem.placeholder = "association";
}

function addAnswerInput() {
    let elem = document.getElementById('answers');
    let newInputElem = document.createElement('input');
    newInputElem.className = "answer";
    elem.appendChild(newInputElem);
    newInputElem.placeholder = "answer";
}

const getKeywords = () => {
    let keywords = [];
    let keywordElements = document.getElementById('keywords');
    keywordElements.childNodes.forEach(
        function (element) {
            if (element.value !== undefined) {
                keywords.push(element.value);
            }
        }
    );
    console.log(keywords);
    return keywords;
}

const getAssociations = () => {
    let associations = [];
    let associationElements = document.getElementById('associations');
    associationElements.childNodes.forEach(
        function (element) {
            if (element.value !== undefined) {
                associations.push(element.value);
            }
        }
    );
    console.log(associations);
    return associations;
}

const getAnswers = () => {
    let answers = [];
    let answerElements = document.getElementById('answers');
    answerElements.childNodes.forEach(
        function (element) {
            if (element.value !== undefined) {
                answers.push(element.value);
            }
        }
    );
    console.log(answers);
    return answers;
}

function addPredicates() {
    let answers =  getAnswers();
    let associations = getAssociations();
    let keywords = getKeywords();

    let request = $.ajax({
        url:'/add_predicate',
        dataType: 'json',
        type: 'POST',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify({
            keywords: keywords,
            associations: associations,
            answers: answers
        })
    })

    request.done(function(data) {
        if (data == "ok") {
            alert("OK")
        } else {
            alert("fail")
        }
    });


    resetInputs()
}

function resetInputs() {
    let keywords = document.getElementById("keywords");
    keywords.innerHTML = '<input type="text" class="keyword" placeholder="keyword" autocomplete="off"/>';

    let associations = document.getElementById("associations");
    associations.innerHTML = '<input type="text" class="association" placeholder="association" autocomplete="off"/>';

    let answers = document.getElementById("answers");
    answers.innerHTML = '<input type="text" class="answer" placeholder="answer" autocomplete="off"/>';
}
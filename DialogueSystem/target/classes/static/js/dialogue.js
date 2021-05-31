$(document).ready(function (){
    start_dialogue();
});

function start_dialogue() {
    $.get('start_dialogue', function (data) {
        let table = "<div>" + data + "</div>"
        $('#answer').html(data);
    })
}

function next_answer() {
    let userInputElem = document.getElementById('userInput');
    let userInput = userInputElem.value;
    userInputElem.value='';

    $.get('get_answer', {sentence : userInput}, function (data) {
        $('#answer').html(data);
    })
}
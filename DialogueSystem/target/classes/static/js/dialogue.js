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
        let res = data.toString()

        if (res == "stop") {
            res = "Всего хорошего. Вы были очень интересным собеседником.";
            const elementStyle = document.getElementById("inputForm").style;
            elementStyle.visibility = "hidden";
        } else if (res == "impasse") {
            res = "Я дико извиняюсь, но мне нужно срочно отлучится. Надеюсь мы ещё увидимся... Пока."
            const elementStyle = document.getElementById("inputForm").style;
            elementStyle.visibility = "hidden";
        }

        $('#answer').html(res);
    })
}
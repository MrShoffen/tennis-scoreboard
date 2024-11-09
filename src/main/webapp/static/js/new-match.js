document.addEventListener('DOMContentLoaded', function () {
    fetchPlayers().then(player_list => {
        setAutocomplete('player_1_form', 'player_1_ac', player_list, start_at_letters = 2);
        setAutocomplete('player_2_form', 'player_2_ac', player_list, start_at_letters = 2);
        console.log(player_list.length);
    });

    setupNewMatchButton();

})

document.addEventListener('DOMContentLoaded', function () {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.forEach(function (tooltipTriggerEl) {
        new bootstrap.Tooltip(tooltipTriggerEl);
    });
});


document.addEventListener('click', function (event) {
    let startButton = document.querySelector('.btn-start');
    if (!startButton.contains(event.target)) {
        let errors = document.querySelectorAll('.error-popup');
        errors.forEach(error => error.classList.add('invisible'));
        let player1 = document.getElementById('player_1_form');
        let player2 = document.getElementById('player_2_form');
        player1.classList.remove('invalid');
        player2.classList.remove('invalid');
    }


})


function sendMatchCreationRequest(player1, player2) {
    const request = {firstPlayer: player1, secondPlayer: player2};

    const urlRequest = context + new_match_api;

    alert(urlRequest);

    fetch(urlRequest, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
    }).then(response => {
        if (!response.ok) {
            console.log(response);
            return response.json().then(error => {
                throw new Error(error.message)
            });
        }

        return response;
    })
        .then(response => {
            window.location.href = response.url;
        })
        .catch(error => {
            alert(error.message);


            // window.location.href = window.location.href;

        });

}

function setupNewMatchButton() {
    let player1 = document.getElementById('player_1_form');
    let player2 = document.getElementById('player_2_form');

    let startButton = document.querySelector('.btn-start');

    startButton.addEventListener('click', function () {

        let correctNames = checkForms(player1, player2);

        if (correctNames) {
            sendMatchCreationRequest(player1.value.trim(), player2.value.trim());
        }
    })

}


function checkForms(player1, player2) {


    let firstCheck = checkInputForm(player1);
    let secondCheck = checkInputForm(player2);
    let thirdCheck = true;

    let name1 = player1.value;
    let name2 = player2.value;

    let errorPop1 = player1.parentElement.parentElement.querySelector('.error-popup');

    if (name1.trim() === name2.trim()) {
        errorPop1.innerHTML = '';
        errorPop1.innerHTML = `Players should not be the same! `;
        errorPop1.classList.remove('invisible');
        player1.classList.add('invalid');
        player2.classList.add('invalid');

        thirdCheck = false;
    }


    return firstCheck && secondCheck && thirdCheck;

}

function checkInputForm(player) {
    let name = player.value;


    let errorPop = player.parentElement.parentElement.querySelector('.error-popup');


    if (name.trim() === '') {
        errorPop.innerHTML = '';
        errorPop.innerHTML = `Player name can't be empty!`;
        errorPop.classList.remove('invisible');
        player.classList.add('invalid');

        return false;
    }

    const validPattern = /^[a-zA-Z]+[a-zA-Z-. ]*[a-zA-Z]+$/;
    if (!validPattern.test(name)) {
        errorPop.innerHTML = '';
        errorPop.innerHTML = `Incorrect name format!`;
        errorPop.classList.remove('invisible');
        player.classList.add('invalid');

        return false;
    }

    if (name.trim().length <= 3 || name.trim().length > 30) {
        errorPop.innerHTML = '';
        errorPop.innerHTML = `Incorrect length!`;
        errorPop.classList.remove('invisible');
        player.classList.add('invalid');

        return false;
    }

    return true;
}


async function fetchPlayers() {
    try {
        let sizeRequest = context + players_api + '?page_number=1&page_size=1&player_name=';
        const sizeResponse = await fetch(sizeRequest);
        const sizeData = await sizeResponse.json();
        pageSize = sizeData.totalPages;

        let url = context + players_api + '?page_number=1&page_size=' + pageSize + '&player_name=';
        const response = await fetch(url);
        const data = await response.json();
        let players = [];
        data.entities.forEach(player => {
            players.push(player.name);
        });

        return players; // возвращаем массив игроков
    } catch (error) {
        console.error('err', error);
        return []; // возвращаем пустой массив в случае ошибки
    }
}

function setAutocomplete(id_formfield, id_autocomplete_div, result_list, start_at_letters = 3, count_results = 5) {
    let input = document.getElementById(id_formfield);
    let autocomplete_div = document.getElementById(id_autocomplete_div);
    input.onkeyup = function () {
        var characters = input.value;
        if (characters.length >= start_at_letters) {
            var res = matchedStrings(result_list, characters);
            renderResults(res, characters, autocomplete_div, input, count_results);
            autocomplete_div.classList.remove('invisible');

            // clear input field on lost focus if reult not in result list
            input.onblur = function () {
                setTimeout(function () {
                    autocomplete_div.classList.add("invisible");
                }, 200);
            }
        } else {
            autocomplete_div.classList.add("invisible");
            // delete all childs from result list
            while (autocomplete_div.firstChild) {
                autocomplete_div.removeChild(autocomplete_div.firstChild);
            }
        }
    }
}


function matchedStrings(item_list, search_string) {
    let results = [];
    item_list.filter(function (item) {
        if (item.toLowerCase().includes(search_string.toLowerCase())) {
            results.push(item);
        }
        ;
    });
    return results
}


function renderResults(results, search, container, form_id, max_results) {
    // delete unordered list from previous search result
    while (container.firstChild) {
        container.removeChild(container.firstChild);
    }
    // get properties from input field
    let form_font = window.getComputedStyle(form_id, null).getPropertyValue('font-size');
    let form_width = form_id.offsetWidth;

    //set result list to same width less borders
    container.style.width = form_width.toString() + 'px';

    if (results.length > 0) {
        // create ul and set classes
        let ul = document.createElement('UL');
        ul.classList.add('list-group', 'mt-1');

        // create list of results and append to ul
        if (results.length > max_results) {
            results = results.slice(0, max_results);
        }
        results.map(function (item) {
            let a = document.createElement('A');
            a.classList.add('newMatch-result', 'list-group-item', 'p-1', 'ac-element'); // newMatch used for init click event, other classes are from bootstrap
            a.setAttribute("reference", form_id.id); // used for click-Event to fill the form
            a.style.fontSize = form_font;
            a.href = '#';

            // see function below - marked search string in results
            a.innerHTML = colored_result(item, search);

            // add Eventlistener for search renderResults
            a.addEventListener("click", function (event) {
                event.preventDefault();
                event.stopPropagation();

                // get text from list item and set it into reffered form field
                let content = a.innerText;
                let form_id = a.getAttribute('reference');
                document.getElementById(form_id).value = content;

                // after choosen a result make div with results invisible -> after changing input content again,
                // all of childs of current div will be deleted [line 48,49]
                container.classList.add('invisible');

            });
            ul.append(a);
        });

        // append ul to container and make container visible
        container.append(ul);
        container.classList.remove('invisible');
        //choose_result(); // add Eventlistener to every result in ul
    } else {
        container.classList.add('invisible');

    }
}


// create span's with colored marked search strings
function colored_result(string, search) {
    let splitted = string.toLowerCase().split(search.toLowerCase());

    let sp = []; // array of all spans, created in folling loop
    let start = 0; //start for slicing

    splitted.map(function (element, index) {
        // empty string at the beginning
        if (element == false) {
            sp.push("<span class='text-info'>" + string.slice(start, start + search.length) + "</span>");
            start = start + search.length;
        } else if (index + 1 == splitted.length) {
            sp.push("<span>" + string.slice(start, start + element.length) + "</span>");
        } else {
            sp.push("<span>" + string.slice(start, start + element.length) + "</span>");
            start = start + element.length;
            sp.push("<span class='text-info'>" + string.slice(start, start + search.length) + "</span>");
            start = start + search.length;
        }
    });
    return sp.join('')
}

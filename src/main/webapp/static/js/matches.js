const host = "/tennis-scoreboard";


document.addEventListener('DOMContentLoaded', function () {

    configureRadioButtons();
    configureSearchBox();

    requestMatches(extractParamsFromUrl());
});


function updateCurrentSearch() {
    const currentSearch = document.querySelector('.current-search');

    const player = extractParamsFromUrl().player_name;
    if (player.trim()) {
        const currentSearchLabel = document.querySelector('.current-search-label');
        currentSearchLabel.textContent = player;
        currentSearch.style.display = 'block';
    } else {
        currentSearch.style.display = 'none';
    }
}

function buildRequest(page_number, page_size, player_name) {
    return {
        page_number: +page_number,
        page_size: +page_size,
        player_name: player_name
    };
}

function extractParamsFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);

    return buildRequest(urlParams.get('page_number') || 1,
        urlParams.get('page_size') || selectedSize(),
        urlParams.get('player_name') || '')


}

function configureSearchBox() {
    const buttonSearch = document.querySelector('.btn-search');
    const buttonClear = document.querySelector('.btn-clear');

    const input = document.querySelector('.input-search');
    const searchBox = document.querySelector('.search-box');

    const currentSearchClearButton = document.querySelector('.btn-clear-current-search');


    function handleSearch() {
        let backgroundColor = window.getComputedStyle(input).backgroundColor;
        if (backgroundColor === "rgba(0, 0, 0, 0)" && input.value.trim()) {

            input.value = input.value.trim();
            requestMatches(
                buildRequest(1, selectedSize(), input.value)
            );
        } else {
            buttonClear.style.display = 'block';
            input.focus();
            input.classList.add('input-search-full')
        }

    }

    buttonSearch.addEventListener('click', handleSearch);

    input.addEventListener('keydown', (event) => {
        if (event.key === 'Enter') {
            handleSearch();
        }
    });

    currentSearchClearButton.addEventListener('click', function () {
        requestMatches(
            buildRequest(1, selectedSize(), '')
        );

    })

    buttonClear.addEventListener('click', function () {
        input.value = '';
    })

    const collapseForm = () => {

        input.classList.remove('input-search-full');

        buttonClear.style.display = 'none';


    };

    // Обработка кликов вне формы
    document.addEventListener('click', function (event) {
        if (!searchBox.contains(event.target)) {  // Если клик вне формы
            collapseForm();
        }
    });


}


function loadPage(params) {

    let l = {name: 'anna', size: 5};

    const par = new URLSearchParams(params);


    const url = host + "/matches?" + par.toString();
    window.history.pushState(null, null, url);
}

function selectedSize() {
    const selectedRadio = document.querySelector('input[name="page_size"]:checked');
    if (selectedRadio) {
        const selectedValue = selectedRadio.id; // или selectedRadio.value, если значение будет храниться в атрибуте value
        return selectedValue;
    }
}

function configureRadioButtons() {
    const params = new URLSearchParams(window.location.search);
    const selectedPage = params.get('page_size');
    console.log(selectedPage)

    if (selectedPage) {
        const radioToCheck = document.getElementById(selectedPage);
        if (radioToCheck) {
            radioToCheck.checked = true;
        }
    }

    const radioButtons = document.querySelectorAll('input[name="page_size"]');
    radioButtons.forEach(radioButton => {
        radioButton.addEventListener('change', () => {
            requestMatches(
                buildRequest(1, selectedSize(), extractParamsFromUrl().player_name)
            );
        });
    });
}


function requestMatches(params) {

    const par = new URLSearchParams(params).toString();
    const url = host + "/matches-data?" + par;

    fetch(url)
        .then(response => response.json())
        .then(json => {
            fillMatchesTable(json.matches);

            setupPagination(json.totalPages, params);
        })
        .catch(error => {
            alert("hee")
        });
    loadPage(params);
    updateCurrentSearch();

}

function fillMatchesTable(data) {
    const tbody = document.querySelector('.match_table tbody');
    tbody.innerHTML = '';

    data.forEach(match => {
        const row = document.createElement('tr');

        let id = document.createElement('td');
        id.innerHTML = match.id
        row.appendChild(id);

        let player1 = document.createElement('td');
        player1.innerHTML = match.firstPlayer + (match.firstPlayer === match.winner ? " <i class=\"fa-solid fa-trophy\"></i>": "");
        row.appendChild(player1);

        let player2 = document.createElement('td');
        player2.innerHTML = match.secondPlayer  + (match.secondPlayer === match.winner ? " <i class=\"fa-solid fa-trophy\"></i>": "");
        row.appendChild(player2);



        // row.innerHTML =
        //     " +
        //     "<td>${match.firstPlayer}</td>" +
        //     "<td>${match.secondPlayer}</td>" +
        //     "<td>${match.winner}</td>"
        // ;

        tbody.appendChild(row);
    });
}


function setupPagination(totalPages, params) {
    let pagination = document.querySelector('.match_pagination');

    pagination.innerHTML = '';


    let prevElement = createPageButton(pagination, 'Prev');
    if (params.page_number === 1) {
        prevElement.classList.add('disabled')
    } else {
        prevElement.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches(
                buildRequest(--params.page_number, params.page_size, params.player_name)
            );
        });
    }


    if (totalPages >= 6) {
        setupSixOrMorePages(totalPages, params, pagination);
    } else {
        setupFiveOrLessPages(totalPages, params, pagination);
    }

    let nextElement = createPageButton(pagination, 'Next');
    if (params.page_number === totalPages) {
        nextElement.classList.add('disabled');
    } else {
        nextElement.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches(
                buildRequest(++params.page_number, params.page_size, params.player_name)
            )

        });
    }


}

function setupFiveOrLessPages(totalPages, params, pagination) {
    for (let i = 1; i <= totalPages; i++) {
        const button = createPageButton(pagination, i)

        checkCurrentNumberPageActive(params, i, button)
    }
}

function createPageButton(pagination, name) {
    const liElement = document.createElement('li');
    liElement.classList.add('page-item');
    liElement.innerHTML = `<a class="page-link" href="#">${name}</a>`;
    pagination.appendChild(liElement);

    return liElement;
}

function checkCurrentNumberPageActive(params, checkedNumber, pageElement) {
    if (params.page_number === checkedNumber) {
        pageElement.classList.add('active')
    } else {
        pageElement.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches(
                buildRequest(checkedNumber, params.page_size, params.player_name)
            )
        });
    }

}

function setupSixOrMorePages(totalPages, params, pagination) {

    let first = createPageButton(pagination, 1);
    checkCurrentNumberPageActive(params, 1, first);

    let prevMiddle = createPageButton(pagination, '...');
    prevMiddle.classList.add('disabled', 'disabled-custom');

    if (params.page_number !== 1 && params.page_number !== totalPages) {
        let middle = createPageButton(pagination, params.page_number);
        middle.classList.add('active');
    } else {
        const middlePage = Math.floor(totalPages / 2);
        let middle = createPageButton(pagination, middlePage);
        middle.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches(
                buildRequest(middlePage, params.page_size, params.player_name)
            );
        });
    }

    let postMiddle = createPageButton(pagination, '...');
    postMiddle.classList.add('disabled', 'disabled-custom');

    let last = createPageButton(pagination, totalPages);
    checkCurrentNumberPageActive(params, totalPages, last)
}

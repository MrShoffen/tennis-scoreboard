document.addEventListener('DOMContentLoaded', function () {
    configurePageSizePlugin();
    configureSearchBarPlugin();

    updatePage(currentRequest());
});


function configurePageSizePlugin() {
    const selectedPage = currentRequest().page_size;

    if (selectedPage) {
        const radioToCheck = document.getElementById(selectedPage);
        if (radioToCheck) {
            radioToCheck.checked = true;
        }
    }

    const radioButtons = document.querySelectorAll('input[name="page_size"]');
    radioButtons.forEach(radioButton => {
        radioButton.addEventListener('change', () => {
            updatePage(
                buildRequest(1, selectedSize(), currentRequest().player_name)
            );
        });
    });
}


function highlightText() {

    let searchedValue = currentRequest().player_name.endsWith('%') ? currentRequest().player_name.slice(0, -1) : currentRequest().player_name;

    const links = document.querySelectorAll('td > a'); // Все элементы <a>

    const regex = new RegExp(searchedValue, 'gi');

    if (searchedValue.trim()) {
        links.forEach(link => {
            if (link.textContent.match(regex)) {
                let winner = link.children.length === 1;

                link.innerHTML = link.textContent.replace(regex, (matched) => `<span class="highlight">${matched}</span>`);
                if (winner) {
                    link.innerHTML += '<i class="fa-solid fa-trophy"></i>'
                }

            }
        });
    }


}


function updateCurrentSearch() {
    const currentSearch = document.querySelector('.current-search');


    const player = currentRequest().player_name;
    if (player.trim()) {
        const currentSearchLabel = document.querySelector('.current-search-label');
        currentSearchLabel.textContent = player.endsWith('%') ? player.slice(0, -1) : player;
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

function currentRequest() {
    const urlParams = new URLSearchParams(window.location.search);

    return buildRequest(urlParams.get('page_number') || 1,
        urlParams.get('page_size') || selectedSize(),
        urlParams.get('player_name') || '')

}


function configureSearchBarPlugin() {
    const searchBox = document.querySelector('.search-box');

    const buttonSearch = document.querySelector('.btn-search');
    const buttonClearForm = document.querySelector('.btn-clear');
    const inputForm = document.querySelector('.input-search');

    const buttonClearCurrentSearchLabel = document.querySelector('.btn-clear-current-search');


    function handleSearch() {
        let backgroundColor = window.getComputedStyle(inputForm).backgroundColor;
        if (backgroundColor === "rgba(0, 0, 0, 0)" && inputForm.value.trim()) {

            inputForm.value = inputForm.value.trim();
            updatePage(
                buildRequest(1, currentRequest().page_size, inputForm.value + '%')
            );
        } else {
            buttonClearForm.style.display = 'block';
            inputForm.focus();
            inputForm.classList.add('input-search-full')
        }

    }

    buttonSearch.addEventListener('click', handleSearch);

    inputForm.addEventListener(
        'keydown', (event) => {
            if (event.key === 'Enter') {
                handleSearch();
            }
        });

    buttonClearCurrentSearchLabel.addEventListener('click', function () {
        updatePage(
            buildRequest(1, currentRequest().page_size, '')
        );
    })

    buttonClearForm.addEventListener('click', function () {
        inputForm.value = '';
    })

    document.addEventListener('click', function (event) {
        if (!searchBox.contains(event.target)) {  // Если клик вне формы
            inputForm.classList.remove('input-search-full');
            buttonClearForm.style.display = 'none';
        }
    });
}


function updateUrl(params) {
    let url = context + frontend + '?' + new URLSearchParams(params);
    window.history.replaceState(null, null, url);
}

function selectedSize() {
    const selectedRadio = document.querySelector('input[name="page_size"]:checked');
    if (selectedRadio) {
        return selectedRadio.id;
    }
}


function removeLoader() {
    let loader = document.querySelector('.bouncing-ball-loader');
    if (loader) {
        loader.remove();
    }
}

async function updatePage(params) {

    const url = context + apiJSON + '?' + new URLSearchParams(params).toString();

    fetch(url)
        .then(response => response.json())
        .then(json => {
            removeLoader();
            fillDataTables(json.entities);
            highlightText();
            configurePaginationPlugin(json.totalPages, params);
            configureGoButton(json.totalPages);
        })
        .catch(error => {
            alert("hee")
        });
    updateUrl(params);
    updateCurrentSearch();
}


function configurePaginationPlugin(totalPages, params) {

    let pagination = document.querySelector('.match_pagination');

    pagination.innerHTML = '';




    let prevElement = createPageButton(pagination, 'Prev');
    if (params.page_number === 1) {
        prevElement.classList.add('disabled')
    } else {
        prevElement.addEventListener('click', async function (event) {
            event.preventDefault();
            await changePageWithAnimation('prev', buildRequest(--params.page_number, params.page_size, params.player_name));

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
        nextElement.addEventListener('click', async function (event) {
            event.preventDefault();

            await changePageWithAnimation('next', buildRequest(++params.page_number, params.page_size, params.player_name));
        });
    }

}

async function changePageWithAnimation(direction, request) {
    let table = document.querySelector('.item-table');
    table.classList.add('fade-out-' + direction);

    await new Promise(resolve => setTimeout(resolve, 200)); // Время совпадает с длительностью анимации

    await updatePage(request);

    table.classList.remove('fade-out-' + direction);
    table.classList.add('fade-in-' + direction);

    setTimeout(() => {
        table.classList.remove('fade-in-' + direction);
    }, 200);
}

function configureGoButton(totalPages) {


    const goBox = document.querySelector('.go-box');

    goBox.innerHTML = '';

    const buttonGo = document.createElement('button');
    buttonGo.classList.add('btn-go');
    buttonGo.innerHTML = '<i class="fa-solid fa-arrow-right"></i>';

    const inputForm = document.createElement('input');
    inputForm.classList.add('input-go');
    inputForm.setAttribute('type', 'text');
    inputForm.setAttribute('placeholder', 'Page...');

    goBox.appendChild(buttonGo);
    goBox.appendChild(inputForm);


    function handleGo(currentTotalPages) {
        inputForm.classList.remove('invalid');

        let backgroundColor = window.getComputedStyle(inputForm).backgroundColor;
        if (backgroundColor === "rgba(0, 0, 0, 0)" && inputForm.value.trim()) {
            let targetPage = +inputForm.value.trim();
            let currentPage = currentRequest().page_number;
            if(Number.isInteger(targetPage) &&   targetPage >= 1 && targetPage <= currentTotalPages && targetPage !== currentPage){


                let direction = targetPage > currentPage ? 'next' : 'prev';

                changePageWithAnimation( direction, buildRequest(targetPage, currentRequest().page_size, currentRequest().player_name));

            } else {
                inputForm.value = '';
                inputForm.classList.add('invalid');
                inputForm.focus();

            }
        } else {
            inputForm.focus();
            inputForm.classList.add('input-go-full')
        }

    }


    buttonGo.addEventListener('click', () => handleGo(totalPages));

    inputForm.addEventListener(
        'keydown', (event) => {
            if (event.key === 'Enter') {
                handleGo(totalPages);
            }
        });


    document.addEventListener('click', function (event) {
        if (!goBox.contains(event.target)) {  // Если клик вне формы
            inputForm.classList.remove('input-go-full');
        }
    });
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

            let direction = checkedNumber > params.page_number ? 'next' : 'prev';

             changePageWithAnimation( direction, buildRequest(checkedNumber, params.page_size, params.player_name));

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

            let direction = middlePage > params.page_number ? 'next' : 'prev';

            changePageWithAnimation( direction, buildRequest(middlePage, params.page_size, params.player_name));


        });
    }

    let postMiddle = createPageButton(pagination, '...');
    postMiddle.classList.add('disabled', 'disabled-custom');

    let last = createPageButton(pagination, totalPages);
    checkCurrentNumberPageActive(params, totalPages, last)
}

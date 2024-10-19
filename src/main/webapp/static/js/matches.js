const host = "/tennis-scoreboard";

//
function loadPage(params) {

    let l = {name: 'anna', size: 5};

    const par = new URLSearchParams(params);


    const url = host + "/matches?" + par.toString();
    window.history.pushState(null, null, url);
}



document.addEventListener('DOMContentLoaded', function () {
    // alert(page);
    const urlParams = new URLSearchParams(window.location.search);
    const page = {
        page_number: + urlParams.get('page_number') || 1
    };

    requestMatches(page);
});


function requestMatches(params) {

    const par = new URLSearchParams(params).toString();
    const url = host + "/matches-data?" + par;

    fetch(url)
        .then(response => response.json())
        .then(json => {
            fillMatchesTable(json.matches);

            setupPagination(json.totalPages, params.page_number);
        })
        .catch(error => {
            alert("hee")
        });
    loadPage(params);
}

function fillMatchesTable(data) {
    const tbody = document.querySelector('.match_table tbody');
    tbody.innerHTML = '';

    data.forEach(match => {
        const row = document.createElement('tr');
        row.innerHTML = `
                        <td>${match.id}</td>
                        <td>${match.firstPlayer}</td>
                        <td>${match.secondPlayer}</td>
                        <td>${match.winner}</td>
                    `;
        tbody.appendChild(row);
    });
}


function setupPagination(totalPages, currentPage) {
    let pagination = document.querySelector('.match_pagination');

    pagination.innerHTML = '';

    console.log(totalPages + ' ' + currentPage)

    let prevElement = createPageButton(pagination, 'Prev');
    if (currentPage === 1) {
        console.log('should disable')
        prevElement.classList.add('disabled')
    } else {
        prevElement.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches({page_number: --currentPage});
        });
    }


    if (totalPages >= 6) {
        setupSixOrMorePages(totalPages, currentPage, pagination);
    } else {
        setupFiveOrLessPages(totalPages, currentPage, pagination);
    }

    let nextElement = createPageButton(pagination, 'Next');
    if (currentPage === totalPages) {
        nextElement.classList.add('disabled');
    } else {
        nextElement.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches({page_number: ++currentPage})
        });
    }


}

function setupFiveOrLessPages(totalPages, currentPage, pagination) {
    for (let i = 1; i <= totalPages; i++) {
        const button = createPageButton(pagination, i)

        checkCurrentNumberPageActive(currentPage, i, button)
    }
}

function createPageButton(pagination, name) {
    const liElement = document.createElement('li');
    liElement.classList.add('page-item');
    liElement.innerHTML = `<a class="page-link" href="#">${name}</a>`;
    pagination.appendChild(liElement);

    return liElement;
}

function checkCurrentNumberPageActive(currentPage, checkedNumber, pageElement) {
    if (currentPage === checkedNumber) {
        pageElement.classList.add('active')
    } else {
        pageElement.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches({page_number: checkedNumber})
        });
    }

}

function setupSixOrMorePages(totalPages, currentPage, pagination) {

    let first = createPageButton(pagination, 1);
    checkCurrentNumberPageActive(currentPage, 1, first);

    let prevMiddle = createPageButton(pagination, '...');
    prevMiddle.classList.add('disabled', 'disabled-custom');

    if (currentPage !== 1 && currentPage !== totalPages) {
        let middle = createPageButton(pagination, currentPage);
        middle.classList.add('active');
    } else {
        const middlePage = Math.floor(totalPages / 2);
        let middle = createPageButton(pagination, middlePage);
        middle.addEventListener('click', function (event) {
            event.preventDefault();
            requestMatches({page_number: middlePage});
        });
    }

    let postMiddle = createPageButton(pagination, '...');
    postMiddle.classList.add('disabled', 'disabled-custom');

    let last = createPageButton(pagination, totalPages);
    checkCurrentNumberPageActive(currentPage, totalPages, last)
}

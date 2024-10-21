const frontend = matches_frontend;

const apiJSON = matches_apiJSON;

//dont touch
function fillDataTables(data) {
    const tbody = document.querySelector('.item-table tbody');
    tbody.innerHTML = '';

    data.forEach(match => {
        const row = document.createElement('tr');

        let id = document.createElement('td');
        id.innerHTML = match.id
        row.appendChild(id);

        let player1 = document.createElement('td');
        player1.innerHTML = match.firstPlayer + (match.firstPlayer === match.winner ? " <i class=\"fa-solid fa-trophy\"></i>" : "");
        row.appendChild(player1);

        let player2 = document.createElement('td');
        player2.innerHTML = match.secondPlayer + (match.secondPlayer === match.winner ? " <i class=\"fa-solid fa-trophy\"></i>" : "");
        row.appendChild(player2);


        tbody.appendChild(row);
    });
}
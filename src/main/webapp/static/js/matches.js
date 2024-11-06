const frontend = matches_frontend;

const apiJSON = matches_api;

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
        let player1Link = document.createElement('a');
        player1Link.innerHTML = match.firstPlayer + (match.firstPlayer === match.winner ? ` <i class="fa-solid fa-trophy" style="color: rgb(214,154,0)"></i>` : "");
        player1Link.href = context + matches_frontend + '?player_name=' + match.firstPlayer + '&page_size=' + selectedSize();
        player1.appendChild(player1Link)
        row.appendChild(player1);

        let player2 = document.createElement('td');
        let player2Link = document.createElement('a');
        player2Link.innerHTML = match.secondPlayer + (match.secondPlayer === match.winner ? ` <i class="fa-solid fa-trophy" style="color: rgb(214,154,0)"></i>` : "");
        player2Link.href = context + matches_frontend + '?player_name=' + match.secondPlayer + '&page_size=' + selectedSize();
        player2.appendChild(player2Link)
        row.appendChild(player2);


        tbody.appendChild(row);
    });

    // highlightText(searchedText);

}
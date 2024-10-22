const frontend = players_frontend;

const apiJSON = players_api;

//dont touch
function fillDataTables(data) {
    const tbody = document.querySelector('.item-table tbody');
    tbody.innerHTML = '';

    data.forEach(player => {
        const row = document.createElement('tr');

        let id = document.createElement('td');
        id.innerHTML = player.id
        row.appendChild(id);

        let name = document.createElement('td');
        let nameLink = document.createElement('a');
        nameLink.innerHTML = player.name;
        //todo rework
        nameLink.href = context + matches_frontend + '?player_name=' + player.name;
        name.appendChild(nameLink);
        row.appendChild(name);

        let winRate = document.createElement('td');
        winRate.innerHTML = player.matchesPlayed;
        row.appendChild(winRate);



        tbody.appendChild(row);
    });
}
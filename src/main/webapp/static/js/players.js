const frontend = players_frontend;

const apiJSON = players_apiJSON;

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


        tbody.appendChild(row);
    });
}
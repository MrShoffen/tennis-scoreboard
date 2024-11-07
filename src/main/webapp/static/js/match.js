document.addEventListener('DOMContentLoaded', function () {

    const urlParams = new URLSearchParams(window.location.search);
    const currentRequest = buildRequest(urlParams.get('id'));

    if (currentRequest.id != null && currentRequest.id > 0) {
        updatePage(currentRequest);
    }


})




function buildRequest(matchId) {
    return {
        id: +matchId
    };
}

function updatePage(params) {

    const url = context + finished_match_api + '?' + new URLSearchParams(params).toString();

    fetch(url)
        .then(response => response.json())
        .then(json => {
            fillMatchData(json);
        })
        .catch(error => {
            alert("hee")
        });
}

function buildHref(player) {
    let nameLink = document.createElement('a');
    nameLink.innerHTML = player;
    //todo rework
    nameLink.href = context + players_frontend + '?player_name=' + player;

    return nameLink;

}

function fillMatchData(match) {

    let title = document.querySelector('h3');
    title.innerHTML = 'Match № ' + match.id;

    let firstPlayer = document.getElementById('first-player').querySelector('h4');
    firstPlayer.innerHTML = '';
    firstPlayer.appendChild(buildHref(match.firstPlayer));


    let secondPlayer = document.getElementById('second-player').querySelector('h4');
    secondPlayer.innerHTML = '';
    secondPlayer.appendChild(buildHref(match.secondPlayer));

    let winner = document.getElementById('winner').querySelector('h4');
    winner.innerHTML = '';
    winner.appendChild(buildHref(match.winner));


}

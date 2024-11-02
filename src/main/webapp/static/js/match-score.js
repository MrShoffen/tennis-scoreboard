const firstPlayerBlock = document.getElementById('first-player');
const secondPlayerBlock = document.getElementById('second-player');

const firstPlayerScoreButton = document.querySelector('.first-player-point-button');
const secondPlayerScoreButton = document.querySelector('.second-player-point-button');


document.addEventListener('DOMContentLoaded', function () {

    let url = context + match_score_api + window.location.search;


    fetch(url)
        .then(response => response.json())
        .then(json => {

            setUpPlayerScore(firstPlayerBlock, json.firstPlayer, json.firstPlayerSets, json.firstPlayerCurrentPoints);
            setUpPlayerScore(secondPlayerBlock, json.secondPlayer, json.secondPlayerSets, json.secondPlayerCurrentPoints);

            setupPlayerScoreButton(firstPlayerScoreButton, json.firstPlayer);
            setupPlayerScoreButton(secondPlayerScoreButton, json.secondPlayer);

        })
        .catch(error => {
            alert("hee")
        });


})


function buildScoreRequest(pointWinner) {
    return {
        pointWinner: pointWinner
    }
}

function sendScoreRequest(request) {
    let url = context + match_score_api + window.location.search;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
    }).then(response => {

    });


}

function setupPlayerScoreButton(element, name) {

    let button = element.querySelector('.btn-point');
    button.innerHTML = 'Point to ' + name;

    button.addEventListener('click', function () {

       sendScoreRequest(buildScoreRequest(name));
    })


}

function setUpPlayerScore(playerBlock, name, sets, currentPoints) {
    let nameElement = playerBlock.querySelector('h4');
    let pointsElement = playerBlock.querySelector('.points');
    let set1Element = playerBlock.querySelector('.set1');
    let set2Element = playerBlock.querySelector('.set2');
    let set3Element = playerBlock.querySelector('.set3');


    nameElement.innerHTML = name;

    pointsElement.innerHTML = currentPoints;
    set1Element.innerHTML = sets[0];
    set2Element.innerHTML = sets[1];
    set3Element.innerHTML = sets[2];

}



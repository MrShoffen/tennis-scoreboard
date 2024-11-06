const firstPlayerBlock = document.getElementById('first-player');
const secondPlayerBlock = document.getElementById('second-player');

const firstPlayerScoreButton = document.querySelector('.first-player-point-button');
const secondPlayerScoreButton = document.querySelector('.second-player-point-button');


function buildScoreRequest(pointWinner) {
    return {
        pointWinner: pointWinner
    }
}

document.addEventListener('DOMContentLoaded', function () {

    let url = context + match_score_api + window.location.search;


    fetch(url)
        .then(response => response.json())
        .then(json => {

            setUpPlayerScore(firstPlayerBlock, json.firstPlayer, json.firstPlayerSets, json.firstPlayerCurrentPoints);
            setUpPlayerScore(secondPlayerBlock, json.secondPlayer, json.secondPlayerSets, json.secondPlayerCurrentPoints);

            setupPlayerScoreButton(firstPlayerScoreButton, json.firstPlayer);
            setupPlayerScoreButton(secondPlayerScoreButton, json.secondPlayer);

            setupPointListeners();

        })
        .catch(error => {
            alert("hee")
        });


})


function setupPointListeners(playerBlock,) {
    let mutableScoreElements = document.querySelectorAll('.score-element');


    mutableScoreElements.forEach((targetNode) => {

        const config = {
            childList: true,
            characterData: true,
            subtree: true
        };


        const callback = function (mutationsList, observer) {
            for (const mutation of mutationsList) {
                const newValue = mutation.target.textContent;
                if ((mutation.type === 'childList' || mutation.type === 'characterData') && newValue !== '0' ) {
                    targetNode.classList.add('highlight');

                    setTimeout(() => {
                        targetNode.classList.remove('highlight');
                    }, 300); // Длительность анимации 1 секунда
                }
            }
        };
        const observer = new MutationObserver(callback);
        observer.observe(targetNode, config);

    })

}

function setUpPlayerScore(playerBlock, name, sets, currentPoints) {
    function updateElement(element, newValue) {
        let oldValue = element.innerHTML;
        let newValueStr = String(newValue).trim();
        if(oldValue !== newValueStr){
            console.log('inside' + element);
            console.log(oldValue);
            console.log(newValue);
            element.innerHTML = newValue;
        }
    }

    let nameElement = playerBlock.querySelector('h4');
    updateElement(nameElement,name);

    let pointsElement = playerBlock.querySelector('.points');
    updateElement(pointsElement,currentPoints);

    let set1Element = playerBlock.querySelector('.set1');
    updateElement(set1Element,sets[0])

    let set2Element = playerBlock.querySelector('.set2');
    updateElement(set2Element,sets[1])


    let set3Element = playerBlock.querySelector('.set3');
    updateElement(set3Element,sets[2])






}

function sendScoreRequest(request) {
    let uuid = window.location.search;
    let url = context + match_score_api + uuid;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
    }).then(response => {
        console.log(response.status);
        // alert(response.url);
        console.log(response.url);
        if (!response.url.includes(uuid)) {
            window.location.href = response.url;
        } else {
            return response.json().then(json => {
                console.log('Получен JSON:', json);
                setUpPlayerScore(firstPlayerBlock, json.firstPlayer, json.firstPlayerSets, json.firstPlayerCurrentPoints);
                setUpPlayerScore(secondPlayerBlock, json.secondPlayer, json.secondPlayerSets, json.secondPlayerCurrentPoints);

            });
        }

    })
        .catch(error => {
            console.error('err', error);
        });


}

function setupPlayerScoreButton(element, name) {

    let button = element.querySelector('.btn-point');
    button.innerHTML = 'Point to ' + name;

    button.addEventListener('click', function () {

        sendScoreRequest(buildScoreRequest(name));
    })


}



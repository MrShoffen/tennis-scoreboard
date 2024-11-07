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
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.message)
                });
            }

            return response.json();
        })
        .then(json => {


            setupScore(json);

            setupPlayerScoreButton(firstPlayerScoreButton, json.firstPlayer);
            setupPlayerScoreButton(secondPlayerScoreButton, json.secondPlayer);

            setupPointListeners();

        })
        .catch(error => {
            alert(error.message)
        });


})

function setupScore(score) {

    function lessThan4Points(points) {
        switch (points) {
            case 0:
                return "0";
            case 1:
                return "15";
            case 2:
                return "30";
            case 3:
                return "40";
            default:
                throw new Error("Invalid score: " + score);
        }
    }

    function parseIntToTennisPoint(first, second) {
        if (first <= 3 && second <= 3) {
            return lessThan4Points(first);
        } else if (first > second) {
            return "AD";
        } else if (first < second) {
            return "40";
        } else if (first === second) {
            return "40"
        }
        return "err";


    }

    let firstPlayerStringScore = score.inTiebreak ? score.currentPoints[score.firstPlayer] :
        parseIntToTennisPoint(+score.currentPoints[score.firstPlayer], +score.currentPoints[score.secondPlayer]);

    let secondPlayerStringScore = score.inTiebreak ? score.currentPoints[score.secondPlayer] :
        parseIntToTennisPoint(+score.currentPoints[score.secondPlayer], +score.currentPoints[score.firstPlayer]);


    setupGameLabel(score.inTiebreak);
    setUpPlayerScore(firstPlayerBlock, score.firstPlayer, score.sets[score.firstPlayer], firstPlayerStringScore);
    setUpPlayerScore(secondPlayerBlock, score.secondPlayer, score.sets[score.secondPlayer], secondPlayerStringScore);

}

function setupGameLabel(inTiebreak) {
    const gameLabel = document.querySelector('.score-names .points');

    if (inTiebreak) {
        updateElement(gameLabel, "TIEBREAK");
        gameLabel.style.color = 'red';
    } else {
        updateElement(gameLabel, "GAME");
        gameLabel.style.color = 'rgb(210, 210, 210)';
    }
}


function setupPointListeners() {
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
                if ((mutation.type === 'childList' || mutation.type === 'characterData') && newValue !== '0') {
                    targetNode.classList.add('highlight');
                    firstPlayerScoreButton.querySelector('button').disabled = true;
                    secondPlayerScoreButton.querySelector('button').disabled = true;
                    setTimeout(() => {
                        targetNode.classList.remove('highlight');
                        firstPlayerScoreButton.querySelector('button').disabled = false;
                        secondPlayerScoreButton.querySelector('button').disabled = false;

                    }, 300); // Длительность анимации 1 секунда
                }
            }
        };
        const observer = new MutationObserver(callback);
        observer.observe(targetNode, config);

    })

}

function updateElement(element, newValue) {
    let oldValue = element.innerHTML;
    let newValueStr = String(newValue).trim();
    if (oldValue !== newValueStr) {
        element.innerHTML = newValue;
    }
}


function setUpPlayerScore(playerBlock, name, sets, currentPoints) {


    let nameElement = playerBlock.querySelector('h4');
    updateElement(nameElement, name);

    let pointsElement = playerBlock.querySelector('.points');
    updateElement(pointsElement, currentPoints);

    let set1Element = playerBlock.querySelector('.set1');
    updateElement(set1Element, sets[0])

    let set2Element = playerBlock.querySelector('.set2');
    updateElement(set2Element, sets[1])


    let set3Element = playerBlock.querySelector('.set3');
    updateElement(set3Element, sets[2])


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

        if (!response.url.includes(uuid)) {
            window.location.href = response.url;
        } else {
            return response.json();
        }

    }).then(json => {
        setupScore(json);
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



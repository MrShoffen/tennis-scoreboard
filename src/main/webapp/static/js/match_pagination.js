document.addEventListener('DOMContentLoaded', function () {
    const host = "/tennis-scoreboard";

    function requestMatches() {
        fetch(`${host}/matches-data`)
            .then(response => response.json())
            .then(data => {
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
            )
            .catch(error => {
                alert("hee")
            });
    }

    requestMatches();
});
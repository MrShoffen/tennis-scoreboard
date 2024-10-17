const button = document.querySelector('.btn-search');
const input = document.querySelector('.input-search');

function handleSearch() {
    let backgroundColor = window.getComputedStyle(input).backgroundColor;
    if (backgroundColor === "rgba(0, 0, 0, 0)" && input.value.trim()) {
        alert(input.value.trim());
    }

}

button.addEventListener('click',handleSearch);

input.addEventListener('keydown', (event) => {
    if (event.key === 'Enter') {
        handleSearch();
    }
});
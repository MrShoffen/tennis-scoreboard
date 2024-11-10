function sendMatchCreationRequest(e, t) {
    const n = {firstPlayer: e, secondPlayer: t}, i = context + new_match_api;
    fetch(i, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(n)
    }).then((e => e.ok ? e : e.json().then((e => {
        throw new Error(e.message)
    })))).then((e => {
        window.location.href = e.url
    })).catch((e => {
        alert(e.message), window.location.href = window.location.href
    }))
}

function setupNewMatchButton() {
    let e = document.getElementById("player_1_form"), t = document.getElementById("player_2_form");
    document.querySelector(".btn-start").addEventListener("click", (function () {
        checkForms(e, t) && sendMatchCreationRequest(e.value.trim(), t.value.trim())
    }))
}

function checkForms(e, t) {
    let n = checkInputForm(e), i = checkInputForm(t), s = !0, r = e.value, l = t.value,
        a = e.parentElement.parentElement.querySelector(".error-popup");
    return r.trim() === l.trim() && (a.innerHTML = "", a.innerHTML = "Players should not be the same! ", a.classList.remove("invisible"), e.classList.add("invalid"), t.classList.add("invalid"), s = !1), n && i && s
}

function checkInputForm(e) {
    let t = e.value, n = e.parentElement.parentElement.querySelector(".error-popup");
    if ("" === t.trim()) return n.innerHTML = "", n.innerHTML = "Player name can't be empty!", n.classList.remove("invisible"), e.classList.add("invalid"), !1;
    return /^[a-zA-Z]+[a-zA-Z-. ]*[a-zA-Z]+$/.test(t) ? !(t.trim().length <= 3 || t.trim().length > 30) || (n.innerHTML = "", n.innerHTML = "Incorrect length!", n.classList.remove("invisible"), e.classList.add("invalid"), !1) : (n.innerHTML = "", n.innerHTML = "Incorrect name format!", n.classList.remove("invisible"), e.classList.add("invalid"), !1)
}

async function fetchPlayers() {
    try {
        let e = context + players_api + "?page_number=1&page_size=1&player_name=";
        const t = await fetch(e), n = await t.json();
        pageSize = n.totalPages;
        let i = context + players_api + "?page_number=1&page_size=" + pageSize + "&player_name=";
        const s = await fetch(i), r = await s.json();
        let l = [];
        return r.entities.forEach((e => {
            l.push(e.name)
        })), l
    } catch (e) {
        return console.error("err", e), []
    }
}

function setAutocomplete(e, t, n, i = 3, s = 5) {
    let r = document.getElementById(e), l = document.getElementById(t);
    r.onkeyup = function () {
        var e = r.value;
        if (e.length >= i) renderResults(matchedStrings(n, e), e, l, r, s), l.classList.remove("invisible"), r.onblur = function () {
            setTimeout((function () {
                l.classList.add("invisible")
            }), 200)
        }; else for (l.classList.add("invisible"); l.firstChild;) l.removeChild(l.firstChild)
    }
}

function matchedStrings(e, t) {
    let n = [];
    return e.filter((function (e) {
        e.toLowerCase().includes(t.toLowerCase()) && n.push(e)
    })), n
}

function renderResults(e, t, n, i, s) {
    for (; n.firstChild;) n.removeChild(n.firstChild);
    let r = window.getComputedStyle(i, null).getPropertyValue("font-size"), l = i.offsetWidth;
    if (n.style.width = l.toString() + "px", e.length > 0) {
        let l = document.createElement("UL");
        l.classList.add("list-group", "mt-1"), e.length > s && (e = e.slice(0, s)), e.map((function (e) {
            let s = document.createElement("A");
            s.classList.add("newMatch-result", "list-group-item", "p-1", "ac-element"), s.setAttribute("reference", i.id), s.style.fontSize = r, s.href = "#", s.innerHTML = colored_result(e, t), s.addEventListener("click", (function (e) {
                e.preventDefault(), e.stopPropagation();
                let t = s.innerText, i = s.getAttribute("reference");
                document.getElementById(i).value = t, n.classList.add("invisible")
            })), l.append(s)
        })), n.append(l), n.classList.remove("invisible")
    } else n.classList.add("invisible")
}

function colored_result(e, t) {
    let n = e.toLowerCase().split(t.toLowerCase()), i = [], s = 0;
    return n.map((function (r, l) {
        0 == r ? (i.push("<span class='text-info'>" + e.slice(s, s + t.length) + "</span>"), s += t.length) : l + 1 == n.length ? i.push("<span>" + e.slice(s, s + r.length) + "</span>") : (i.push("<span>" + e.slice(s, s + r.length) + "</span>"), s += r.length, i.push("<span class='text-info'>" + e.slice(s, s + t.length) + "</span>"), s += t.length)
    })), i.join("")
}

document.addEventListener("DOMContentLoaded", (function () {
    fetchPlayers().then((e => {
        setAutocomplete("player_1_form", "player_1_ac", e, start_at_letters = 2), setAutocomplete("player_2_form", "player_2_ac", e, start_at_letters = 2), console.log(e.length)
    })), setupNewMatchButton()
})), document.addEventListener("DOMContentLoaded", (function () {
    [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]')).forEach((function (e) {
        new bootstrap.Tooltip(e)
    }))
})), document.addEventListener("click", (function (e) {
    if (!document.querySelector(".btn-start").contains(e.target)) {
        document.querySelectorAll(".error-popup").forEach((e => e.classList.add("invisible")));
        let e = document.getElementById("player_1_form"), t = document.getElementById("player_2_form");
        e.classList.remove("invalid"), t.classList.remove("invalid")
    }
}));
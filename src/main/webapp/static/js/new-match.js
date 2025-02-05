function sendMatchCreationRequest(e, t) {
    const n = {firstPlayer: e, secondPlayer: t}, s = context + new_match_api;
    fetch(s, {
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
        if (checkForms(e, t)) {
            sendMatchCreationRequest(e.value.replace(/\s{2,}/g, " ").trim(), t.value.replace(/\s{2,}/g, " ").trim())
        }
    }))
}

function checkForms(e, t) {
    let n = checkInputForm(e), s = checkInputForm(t), i = !0,
        l = e.parentElement.parentElement.querySelector(".error-popup"), r = e.value.replace(/\s{2,}/g, " ").trim(),
        a = t.value.replace(/\s{2,}/g, " ").trim();
    return console.log(r), console.log(a), r === a && (l.innerHTML = "", l.innerHTML = "Players should not be the same! ", l.classList.remove("invisible"), e.classList.add("invalid"), t.classList.add("invalid"), i = !1), n && s && i
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
        let s = context + players_api + "?page_number=1&page_size=" + pageSize + "&player_name=";
        const i = await fetch(s), l = await i.json();
        let r = [];
        return l.entities.forEach((e => {
            r.push(e.name)
        })), r
    } catch (e) {
        return console.error("err", e), []
    }
}

function setAutocomplete(e, t, n, s = 3, i = 5) {
    let l = document.getElementById(e), r = document.getElementById(t);
    l.onkeyup = function () {
        var e = l.value;
        if (e.length >= s) renderResults(matchedStrings(n, e), e, r, l, i), r.classList.remove("invisible"), l.onblur = function () {
            setTimeout((function () {
                r.classList.add("invisible")
            }), 200)
        }; else for (r.classList.add("invisible"); r.firstChild;) r.removeChild(r.firstChild)
    }
}

function matchedStrings(e, t) {
    let n = [];
    return e.filter((function (e) {
        e.toLowerCase().includes(t.toLowerCase()) && n.push(e)
    })), n
}

function renderResults(e, t, n, s, i) {
    for (; n.firstChild;) n.removeChild(n.firstChild);
    let l = window.getComputedStyle(s, null).getPropertyValue("font-size"), r = s.offsetWidth;
    if (n.style.width = r.toString() + "px", e.length > 0) {
        let r = document.createElement("UL");
        r.classList.add("list-group", "mt-1"), e.length > i && (e = e.slice(0, i)), e.map((function (e) {
            let i = document.createElement("A");
            i.classList.add("newMatch-result", "list-group-item", "p-1", "ac-element"), i.setAttribute("reference", s.id), i.style.fontSize = l, i.href = "#", i.innerHTML = colored_result(e, t), i.addEventListener("click", (function (e) {
                e.preventDefault(), e.stopPropagation();
                let t = i.innerText, s = i.getAttribute("reference");
                document.getElementById(s).value = t, n.classList.add("invisible")
            })), r.append(i)
        })), n.append(r), n.classList.remove("invisible")
    } else n.classList.add("invisible")
}

function colored_result(e, t) {
    let n = e.toLowerCase().split(t.toLowerCase()), s = [], i = 0;
    return n.map((function (l, r) {
        0 == l ? (s.push("<span class='text-info'>" + e.slice(i, i + t.length) + "</span>"), i += t.length) : r + 1 == n.length ? s.push("<span>" + e.slice(i, i + l.length) + "</span>") : (s.push("<span>" + e.slice(i, i + l.length) + "</span>"), i += l.length, s.push("<span class='text-info'>" + e.slice(i, i + t.length) + "</span>"), i += t.length)
    })), s.join("")
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
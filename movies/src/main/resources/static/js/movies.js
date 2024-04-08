

document.addEventListener("DOMContentLoaded", function () {
    document.addEventListener("click", function (event) {

        if (event.target && event.target.classList.contains("buy-tickets-btn")) {
            var sessionId = event.target.dataset.sessionId;
            
            var form = document.getElementById("ticket-form");
            form.action = "/sessions/" + sessionId;

        }
    });
});

// Function to reload the fragment with the new date
function reloadFragment(button) {
    var date = button.getAttribute("data-date");
    $.ajax({
        url: "/sessionfragment",
        method: "GET",
        data: { date: date },
        success: function (response) {
            $("#fragmentContainer").html(response);
        },
        error: function (xhr, status, error) {
            console.error("Error:", error);
        }
    });

    var activeButton = document.querySelector(".btn.active");
    console.log(activeButton)
    activeButton.classList.remove("active")
        
    button.classList.add("active")
    button.disabled = true;
}


function getDatesForNext7Days(startDate) {
    var dates = [];

    for (var i = 0; i < 7; i++) {
        var date = new Date(startDate);
        date.setDate(startDate.getDate() + i);
        dates.push(date);
    }

    return dates;
}

function generateButtons(startDate = new Date()) {
    var dates = getDatesForNext7Days(startDate);
    var buttonContainer = document.getElementById('dateButtons');
    buttonContainer.textContent = "";

    for (var i = 0; i < dates.length; i++) {
        var date = dates[i];
        var button = document.createElement('a');
        button.setAttribute('class', "btn btn-dark btn-date"); 
        button.setAttribute('data-date', date.toISOString().split('T')[0]);
        button.setAttribute('onclick', "reloadFragment(this)");
        button.innerHTML = `
            <div><h3>${date.toLocaleString('en', { weekday: 'short' })}</h3></div>
            <div><h6>${date.getDate()} ${date.toLocaleString('en', { month: 'short' })}</h6></div>
        `;
        if (i === 0) {
            button.classList.add("active");
            button.disabled = true;
        }
        buttonContainer.appendChild(button);
    }

    if (document.documentElement.dataset.bsTheme === "dark") {
        var btnPrimaryElements = document.querySelectorAll(".btn");
        btnPrimaryElements.forEach(function (element) {
            element.classList.remove("btn-light")
            element.classList.add('btn-dark');
        });
    }
}

function previousWeek() {
    var date = new Date();
    generateButtons(date)
    var firstButton = document.getElementById('dateButtons').firstElementChild
    reloadFragment(firstButton)
    var prevBtn = document.getElementById('prevBtn')
    prevBtn.disabled = true
    var nextBtn = document.getElementById('nextBtn')
    nextBtn.disabled = false
}

function nextWeek() {
    var date = new Date();
    date.setDate(date.getDate() + 7)
    generateButtons(date)
    var firstButton = document.getElementById('dateButtons').firstElementChild
    reloadFragment(firstButton)
    var prevBtn = document.getElementById('prevBtn')
    prevBtn.disabled = false
    var nextBtn = document.getElementById('nextBtn')
    nextBtn.disabled = true
}


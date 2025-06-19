document.addEventListener("DOMContentLoaded", async function () {

    retriveUsers();
  
});

document.getElementById("search-button").addEventListener("click", function() {
    const searchInput = document.getElementById("search-input").value.trim();

    if (searchInput !== "") {
        retriveFilteredUsers(searchInput);
    } else {
        retriveUsers();
    }
});

function retriveUsers() {
    const lista = document.querySelector(".component-list");
    const username = sessionStorage.getItem("user");

    // Petición a la API
    fetch("/social/users?exclude=" + username)
        .then(response => response.json())
        .then(data => {

            lista.innerHTML = "";
            show_info(data);

            
        })
        .catch(error => {
            console.error("Error al cargar ejercicios:", error);
        });
}

function retriveFilteredUsers(filter) {
    const lista = document.querySelector(".component-list");
    const username = sessionStorage.getItem("user");

    // Petición a la API
    fetch("/social/users/"+ filter+ "?exclude=" + username)
        .then(response => response.json())
        .then(data => {

            lista.innerHTML = "";
            show_info(data);

        })
        .catch(error => {
            console.error("Error al cargar ejercicios:", error);
        });
}

document.getElementById("cerrarSesion").addEventListener("click", function () {
    
    sessionStorage.removeItem("user");
    window.location.href = "landingpage.html";
});

function show_info(users) {
    const lista = document.querySelector(".component-list");
    lista.innerHTML = "";

    users.forEach(user => {
        const li = document.createElement("li");
        li.classList.add("component-item");

        li.innerHTML = `
            <span class="component-name">${user.username}</span>
            <div class="component-actions">
                <button class="btn btnseguir">Seguir</button>
                <button class="btn btnperfil">Perfil</button>
            </div>
        `;

        const perfilBtn = li.querySelector(".btnperfil");
        perfilBtn.addEventListener("click", () => {
            sessionStorage.setItem("search-user", user.username);
            window.location.href = "userpage.html";
        });

        lista.appendChild(li);
    });
}
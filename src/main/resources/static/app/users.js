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

    // Petición a la API
    fetch("/social/users")
        .then(response => response.json())
        .then(data => {

            lista.innerHTML = "";


            data.forEach(user => {
                const li = document.createElement("li");
                li.classList.add("component-item");

                li.innerHTML = `
                    <span class="component-name">${user.username}</span>
                    <div class="component-actions">
                        <button class="btn btn-info">Seguir</button>
                        <button class="btn btn-edit" >Perfil</button>
                    </div>
                `;

                const infoBtn = li.querySelector(".btn-info");
                infoBtn.addEventListener("click", () => {
                    sessionStorage.setItem("routine", ejercicio.id);
                    // Redirigir con parámetro en la URL
                    window.location.href = "inforoutine.html";
                });

                const editBtn = li.querySelector(".btn-edit");
                editBtn.addEventListener("click", () => {
                    sessionStorage.setItem("routine", ejercicio.id);
                    sessionStorage.setItem("insert", "false");
                    // Redirigir con parámetro en la URL
                    window.location.href = "modifyroutine.html";
                });

                lista.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Error al cargar ejercicios:", error);
        });
}

function retriveFilteredUsers(filter) {
    const lista = document.querySelector(".component-list");

    // Petición a la API
    fetch("/social/users/"+ filter)
        .then(response => response.json())
        .then(data => {

            lista.innerHTML = "";


            data.forEach(user => {
                const li = document.createElement("li");
                li.classList.add("component-item");

                li.innerHTML = `
                    <span class="component-name">${user.username}</span>
                    <div class="component-actions">
                        <button class="btn btn-info">Seguir</button>
                        <button class="btn btn-edit" >Perfil</button>
                    </div>
                `;

                const infoBtn = li.querySelector(".btn-info");
                infoBtn.addEventListener("click", () => {
                    sessionStorage.setItem("routine", ejercicio.id);
                    // Redirigir con parámetro en la URL
                    window.location.href = "inforoutine.html";
                });

                const editBtn = li.querySelector(".btn-edit");
                editBtn.addEventListener("click", () => {
                    sessionStorage.setItem("routine", ejercicio.id);
                    sessionStorage.setItem("insert", "false");
                    // Redirigir con parámetro en la URL
                    window.location.href = "modifyroutine.html";
                });

                lista.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Error al cargar ejercicios:", error);
        });
}
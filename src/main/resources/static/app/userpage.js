const search_user = sessionStorage.getItem("search-user");

window.addEventListener('DOMContentLoaded', function () {
    // Tu código aquí se ejecutará cuando la página haya cargado completamente
    document.getElementById("profile_username").textContent = search_user;
    console.log("Username from sessionStorage:", search_user);
    retriveRoutine();
});

document.getElementById("linkAtras").addEventListener("click", function () {
    sessionStorage.removeItem("search-user");
    window.location.href = "users.html";
});


function retriveRoutine() {
    const lista = document.querySelector(".info-grid");

    // Petición a la API
    fetch("/routine/" + search_user)
        .then(response => response.json())
        .then(data => {

            lista.innerHTML = "";


            data.forEach(ejercicio => {
                const li = document.createElement("li");
                li.classList.add("component-item");

                li.innerHTML = `
                    <span class="component-name">${ejercicio.name}</span>
                    <div class="component-actions">
                        <button class="btn btn-info">Ver</button>
                    </div>
                `;

                const infoBtn = li.querySelector(".btn-info");
                infoBtn.addEventListener("click", () => {
                    sessionStorage.setItem("routine", ejercicio.id);
                    // Redirigir con parámetro en la URL
                    window.location.href = "inforoutine.html?from=userpage";
                });

                lista.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Error al cargar ejercicios:", error);
        });
}
document.getElementById("cerrarSesion").addEventListener("click", function () {

    sessionStorage.removeItem("user");
    window.location.href = "landingpage.html";
});

const search_user = sessionStorage.getItem("search-user");

window.addEventListener('DOMContentLoaded', function () {
    // Tu código aquí se ejecutará cuando la página haya cargado completamente
    document.getElementById("profile_username").textContent = search_user;
    console.log("Username from sessionStorage:", search_user);
    retriveUserData();
    retriveRoutine();
});

document.getElementById("linkAtras").addEventListener("click", function () {
    sessionStorage.removeItem("search-user");
    window.location.href = "users.html";
});

document.getElementById("follow").addEventListener("click", function () {
    const followButton = document.getElementById("follow");
    if (followButton.classList.contains("btn-follow")) {
        followUser();
    } else if (followButton.classList.contains("btn-unfollow")) {
        unfollowUser();
    }

});




function retriveUserData() {
    const routines = document.getElementById("profile_routines");
    const followers = document.getElementById("profile_followers");
    const followed = document.getElementById("profile_followed");
    fetch("/social/" + search_user)
        .then(response => response.json())
        .then(data => {
            console.log("User data retrieved:", data);
            if (data.routines) {
                routines.textContent = data.routines;
            } else {
                routines.textContent = "No Info";
            }
            if (data.followers) {
                followers.textContent = data.followers;
            } else {
                followers.textContent = "No Info";
            }
            if (data.followed) {
                followed.textContent = data.followed;
            } else {
                followed.textContent = "No Info";
            }
        })
        .catch(error => {
            console.error("Error retrieving user data:", error);
            // Manejo de errores, podrías mostrar un mensaje al usuario
            document.getElementById("profile_routines").textContent = "Error";
            document.getElementById("profile_followers").textContent = "Error";
            document.getElementById("profile_followed").textContent = "Error";
        });
}


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

function followUser() {

    const followButton = document.getElementById("follow");

    fetch("/social/follow?follower=" + sessionStorage.getItem("user") + "&followed=" + sessionStorage.getItem("search-user"), {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                followButton.textContent = "Dejar de seguir";
                followButton.className = "";
                followButton.className = "btn-unfollow";
            } else {
                throw new Error("Error al seguir al usuario");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            document.querySelector(".error-message").style.display = "block";
        });

}

function unfollowUser() {
    const followButton = document.getElementById("follow");

    fetch("/social/unfollow?follower=" + sessionStorage.getItem("user") + "&followed=" + sessionStorage.getItem("search-user"), {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                followButton.textContent = "Seguir";
                followButton.className = "";
                followButton.className = "btn-follow";
            } else {
                throw new Error("Error al dejar de seguir al usuario");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            document.querySelector(".error-message").style.display = "block";
        });
}

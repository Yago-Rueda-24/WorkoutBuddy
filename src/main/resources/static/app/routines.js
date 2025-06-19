const username = sessionStorage.getItem("user");

document.getElementById("btnAdd").addEventListener("click", function () {
    // Aquí puedes agregar la lógica para añadir un nuevo ejercicio
    // Por ejemplo, mostrar un formulario modal o redirigir a otra página
    sessionStorage.setItem("insert", "true");
    window.location.href = "modifyroutine.html";
});

document.addEventListener("DOMContentLoaded", function () {
    retriveRoutine();
});

document.getElementById("cerrarSesion").addEventListener("click", function () {
    // Eliminar el usuario del sessionStorage
    sessionStorage.removeItem("user");
    // Redirigir a la página de inicio de sesión
    window.location.href = "landingpage.html";
});

function deleteExercise(id) {
    // Petición a la API para eliminar el ejercicio
    fetch("/routine/delete/" + id, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                console.log("Ejercicio eliminado correctamente");
                retriveRoutine(); 
            } else {
                console.error("Error al eliminar el ejercicio");
            }
        })
        .catch(error => {
            console.error("Error en la petición:", error);
        });
        
}

function retriveRoutine() {
    const lista = document.querySelector(".component-list");

    // Petición a la API
    fetch("/routine/" + username)
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
                        <button class="btn btn-edit" data-id="${ejercicio.id}">Editar</button>
                        <button class="btn btn-delete" data-id="${ejercicio.id}">Eliminar</button>
                    </div>
                `;

                const infoBtn = li.querySelector(".btn-info");
                infoBtn.addEventListener("click", () => {
                    sessionStorage.setItem("routine", ejercicio.id);
                    // Redirigir con parámetro en la URL
                    window.location.href = "inforoutine.html?from=mainpage";
                });

                const editBtn = li.querySelector(".btn-edit");
                editBtn.addEventListener("click", () => {
                    sessionStorage.setItem("routine", ejercicio.id);
                    sessionStorage.setItem("insert", "false");
                    // Redirigir con parámetro en la URL
                    window.location.href = "modifyroutine.html";
                });

                const deleteBtn = li.querySelector(".btn-delete");
                deleteBtn.addEventListener("click", () => {
                    // Redirigir con parámetro en la URL
                    deleteExercise(ejercicio.id);
                });


                lista.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Error al cargar ejercicios:", error);
        });
}


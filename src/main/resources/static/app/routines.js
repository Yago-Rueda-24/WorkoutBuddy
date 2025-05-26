const username = sessionStorage.getItem("user");

document.getElementById("btnAdd").addEventListener("click", function () {
    // Aquí puedes agregar la lógica para añadir un nuevo ejercicio
    // Por ejemplo, mostrar un formulario modal o redirigir a otra página
    console.log(username);
});

document.addEventListener("DOMContentLoaded", function () {
    retriveRoutine();
});

function addExercise() {

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
                    window.location.href = "inforoutine.html";
                });


                lista.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Error al cargar ejercicios:", error);
        });
}


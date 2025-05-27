
const title = document.querySelector(".content-title");
const lista = document.querySelector(".component-list");
const routine = sessionStorage.getItem("routine");
document.addEventListener("DOMContentLoaded", function () {
 

    console.log(routine);
    // Aquí usas el ID que corresponda. Podrías capturarlo dinámicamente desde query params si quieres hacerlo reutilizable.
    if (sessionStorage.getItem("insert") === "true") {
        console.log("1");
    } else {
        console.log("2");
        retriveExercise(routine);
    }


});

function retriveExercise(routine) {
    fetch("/routine/exercise/" + routine)
        .then(response => response.json())
        .then(data => {
            // Mostrar el nombre de la rutina como título
            title.textContent = data.name;

            // Limpiar la lista actual
            lista.innerHTML = "";

            // Crear componentes dinámicamente por cada ejercicio
            if (Array.isArray(data.exercises)) {
                data.exercises.forEach(ejercicio => {
                    const li = document.createElement("li");
                    li.classList.add("component-item");

                    li.innerHTML = `
                    <input type="text" class="component-name" value="${ejercicio.name}">
                    <input type="number" class="component-reps" value="${ejercicio.reps}" placeholder="Reps">
                    <input type="number" class="component-sets" value="${ejercicio.sets}" placeholder="Sets">
                    <div class="component-actions">
                        <button class="btn btn-delete" data-id="${ejercicio.id}">Eliminar</button>
                    </div>
                    `;




                    lista.appendChild(li);
                });
            } else {
                console.warn("No exercises found in the routine data.");
            }
        })
        .catch(error => {
            console.error("Error al cargar la rutina:", error);
        });
}

document.addEventListener("DOMContentLoaded", function () {
    const title = document.querySelector(".content-title");
    const lista = document.querySelector(".component-list");
    const routine = sessionStorage.getItem("routine");
    const username = sessionStorage.getItem("user");
    console.log(routine);

    // Aquí usas el ID que corresponda. Podrías capturarlo dinámicamente desde query params si quieres hacerlo reutilizable.
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
                        <span class="component-name">${ejercicio.name}</span>
                        <span class="component-details">Reps ${ejercicio.reps}  </span>
                        <span class="component-details">Sets ${ejercicio.sets}</span>
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
});

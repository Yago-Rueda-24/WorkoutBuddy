
const title = document.querySelector(".content-title");
const lista = document.querySelector(".component-list");
const routine = sessionStorage.getItem("routine");
const username = sessionStorage.getItem("user");


document.addEventListener("DOMContentLoaded", async function () {


    console.log(routine);
    // Aquí usas el ID que corresponda. Podrías capturarlo dinámicamente desde query params si quieres hacerlo reutilizable.
    if (sessionStorage.getItem("insert") === "true") {
        console.log("1");
        
    } else {
        console.log("2");
        await retriveExercise(routine);
        
    }


});

document.getElementById("btnAdd").addEventListener("click", addExercise);
document.getElementById("btnSave").addEventListener("click", save);




async function save() {
    const exercises = [];
    const items = document.querySelectorAll(".component-item");

    items.forEach(item => {
        const name = item.querySelector(".component-name").value;
        const reps = item.querySelector(".component-reps").value;
        const sets = item.querySelector(".component-sets").value;

        if (name && reps && sets) {
            exercises.push({
                name: name,
                reps: parseInt(reps, 10),
                sets: parseInt(sets, 10)
            });
        }
    });

    try {
        const response = await fetch("/routine/add/" + username, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name: "foo", exercises })
        });

        if (!response.status === 201) {
            throw new Error("Error al guardar los datos de la rutina");
        }

        alert("Rutina guardada correctamente");
    } catch (error) {
        console.error("Error al guardar la rutina:", error);
    }
}

async function retriveExercise(routine) {
    try {
        const response = await fetch("/routine/exercise/" + routine);

        if (!response.ok) {
            throw new Error("Error al obtener los datos de la rutina");
        }

        const data = await response.json();

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
                li.querySelector(".btn-delete").addEventListener("click", function () {
                    lista.removeChild(li);
                });
                lista.appendChild(li);
            });
        } else {
            console.warn("No exercises found in the routine data.");
        }

    } catch (error) {
        console.error("Error al cargar la rutina:", error);
    }
}

function addExercise() {
    const li = document.createElement("li");
    li.classList.add("component-item");
    li.innerHTML = `
        <input type="text" class="component-name" placeholder="Nombre del ejercicio">
        <input type="number" class="component-reps" placeholder="Reps">
        <input type="number" class="component-sets" placeholder="Sets">
        <div class="component-actions">
            <button class="btn btn-delete">Eliminar</button>
        </div>
    `;
    lista.appendChild(li);

    // Añadir evento de eliminación al botón
    li.querySelector(".btn-delete").addEventListener("click", function () {
        lista.removeChild(li);
    });
}



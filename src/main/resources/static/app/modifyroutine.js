
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
    let invalid = false;
    const titulo = document.getElementById("titulo").value;
    let message = "";
    if (titulo.trim() === "") {
        invalid = true;
        message = "Por favor, introduce un título para la rutina.";
    }
    if (items.length === 0) {
        invalid = true;
        message = "Por favor, añade al menos un ejercicio a la rutina.";
    }

    items.forEach(item => {
        const name = item.querySelector(".component-name").value;
        const reps = item.querySelector(".component-reps").value;
        const sets = item.querySelector(".component-sets").value;

        if (name.trim() !== "" && parseInt(reps, 10) > 0 && parseInt(sets, 10) > 0) {
            exercises.push({
                name: name,
                reps: parseInt(reps, 10),
                sets: parseInt(sets, 10)
            });
        } else {
            invalid = true;
            message = "Por favor, completa todos los campos de los ejercicios correctamente.";
        }
    });


    if (!invalid) {
        try {
            const response = await fetch("/routine/add/" + username, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ name: titulo, exercises })
            });

            if (response.status !== 201) {
                const errorData = await response.json();
                console.error("Errores del backend:", errorData);
                throw new Error("Error al guardar los datos de la rutina");
            } else {

                alert("Rutina guardada correctamente");
            }


        } catch (error) {
            console.error("Error al guardar la rutina:", error);
        }
    } else {
        alert(message);
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
                    <input type="number" class="component-reps" value="${ejercicio.reps}" placeholder="Reps" min="1" max="100">
                    <input type="number" class="component-sets" value="${ejercicio.sets}" placeholder="Sets" min="1" max="100">
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
        <input type="number" class="component-reps" placeholder="Reps" min="1" max="100">
        <input type="number" class="component-sets" placeholder="Sets" min="1" max="100">
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



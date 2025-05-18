document.getElementById("signup-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const passwordrepeat = document.getElementById("passwordrepeat").value;

    fetch("/login/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
            passwordrepeat: passwordrepeat,
        }),
    })
        .then((response) => response.text())
        .then((data) => {
            if (data === "true") {
                console.log("Usuario creado correctamente");
                window.location.href = "/mainpage.html"; 
                
            } else {
                console.log("Error al crear el usuario");
                document.getElementById("messager-error").innerText = "Error al crear la cuenta";
            }
        })
        .catch((error) => {
            console.error("Error al hacer la petición:", error);
            document.getElementById("respuesta").innerText = "Error al conectar con el servidor";
        });
});
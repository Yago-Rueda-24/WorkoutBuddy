document.getElementById("login-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/login/trylog", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: username,
            password: password,
        }),
    })
        .then((response) => response.text())
        .then((data) => {
            if (data === "true") {

                window.location.href = "/mainpage.html"; 
                
            } else {
                document.getElementById("messager-error").innerText = "Usuario o password incorrectos";
            }
        })
        .catch((error) => {
            console.error("Error al hacer la petición:", error);
            document.getElementById("respuesta").innerText = "Error al conectar con el servidor";
        });
});

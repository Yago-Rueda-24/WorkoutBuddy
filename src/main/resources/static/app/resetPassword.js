

document.getElementById('login-form').addEventListener('submit', async function (event) {
    event.preventDefault();

    const password = document.getElementById('password').value;
    const passwordrepeat = document.getElementById('passwordrepeat').value;
    const messagerError = document.getElementById('messager-error');

    if (!password || !passwordrepeat) {
        messagerError.innerText = "Por favor, completa todos los campos.";
        return;
    }
    if (password !== passwordrepeat) {
        messagerError.innerText = "Las contraseñas no coinciden.";
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const user = params.get("user");
    const token = params.get("token");

    fetch("/login/resetPassword", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            username: user,
            token: token,
            password: password,
            repeatpassword: passwordrepeat

        })
    }).then(response => {
        if (response.ok) {
            sessionStorage.setItem("user", user);
            window.location.href = "/login.html";
        }else{
            return response.json().then(data => {
                messagerError.innerText = data.message || "Error al restablecer la contraseña.";
            });
        }
    }
    ).catch(error => {
        console.error("Error al hacer la petición:", error);
        messagerError.innerText = "Error al conectar con el servidor.";
    });


    console.log("Usuario:", user);
    console.log("Token:", token);


});
async function checkLoginStatus() {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  try {
    const response = await fetch("/login/trylog", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    });

    if (response.ok) {
      const data = await response.json();
      sessionStorage.setItem("user", username);
      console.log("Respuesta de éxito:", data.message);
      window.location.href = "/mainpage.html";
    } else if (response.status === 401) {
      const errorData = await response.json();
      console.error("Error de inicio de sesión:", errorData.message);
      document.getElementById("messager-error").innerText = errorData.message;
    }
    else {
      console.error("Error en la respuesta del servidor. Código:", response.status);
      document.getElementById("messager-error").innerText = "Error al iniciar sesión";
    }
  } catch (error) {
    console.error("Error al hacer la petición:", error);
    document.getElementById("messager-error").innerText = "Error al conectar con el servidor";
  }
}

document.getElementById("login-form").addEventListener("submit", function (event) {
  event.preventDefault();
  checkLoginStatus();
});

document.getElementById("forgotPassword").addEventListener("click", function (event) {
  event.preventDefault();

  const username = document.getElementById("username").value;
  if (!username) {
    alert("Por favor, introduce tu nombre de usuario.");
    return;
  }
  fetch("/login/password/" + username, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    }
  })
    .then(response => response.json())
    .then(data => {
      alert(data.message);
    })
    .catch(error => {
      alert("Error al recuperar la contraseña");
      console.error(error);
    });


});

async function signup() {
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const passwordrepeat = document.getElementById("passwordrepeat").value;

  try {
    const response = await fetch("/login/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
        passwordrepeat: passwordrepeat,
      }),
    });

    if (response.ok) {
      const data = await response.json();
      console.log("Respuesta de éxito:", data.message); 
      window.location.href = "/mainpage.html";
    } else if (response.status === 409) {
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

document.getElementById("signup-form").addEventListener("submit", function(event) {
  event.preventDefault();
  signup();
});
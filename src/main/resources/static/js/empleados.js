console.log("empleados")

document.addEventListener('DOMContentLoaded', function () {
	
    fetch('/empleados/generarCodigo')
        .then(response => {
            console.log('Fetch response received:', response);
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Data received:', data);
            document.getElementById('codigo').value = data.codigo;
        })
        .catch(error => console.error('Error:', error));
});

function registrarEmpleado(event) {
	
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    fetch(form.action, {
        method: form.method,
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(() => {
            Swal.fire({
                icon: 'success',
                title: 'Éxito',
                text: 'Empleado registrado correctamente.',
                timer: 2000, // 5 segundos
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/empleados'; // Redirige después de mostrar la alerta
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al registrar el empleado.',
            });
        });

    return false; // Evita el envío del formulario
}
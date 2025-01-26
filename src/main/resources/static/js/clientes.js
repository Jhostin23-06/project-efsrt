document.addEventListener('DOMContentLoaded', function () {
    console.log('DOM fully loaded and parsed');

    fetch('/clientes/generarCodigo')
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

function registrarCliente(event) {
    event.preventDefault(); // Evita el envío del formulario

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
                text: 'Cliente registrado correctamente.',
                timer: 2000, // 5 segundos
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/clientes'; // Redirige después de mostrar la alerta
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al registrar el cliente.',
            });
        });

    return false; // Evita el envío del formulario
}

function editarCliente(event) {
    event.preventDefault(); // Evita el envío del formulario

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
                text: 'Cliente editado correctamente.',
                timer: 2000, // 5 segundos
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/clientes'; // Redirige después de mostrar la alerta
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al editar el cliente.',
            });
        });

    return false; // Evita el envío del formulario
}

function eliminarCliente(clienteId) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: 'No podrás revertir esto.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/clientes/eliminar/${clienteId}`, {
                method: 'PUT'
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
                        text: 'Cliente eliminado correctamente.',
                        timer: 2000, // 5 segundos
                        timerProgressBar: true
                    }).then(() => {
                        window.location.href = '/clientes'; // Redirige después de mostrar la alerta
                    });
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Hubo un problema al eliminar el cliente.',
                    });
                });
        }
    });
}

const editarClienteModal = document.getElementById('editarClienteModal');
editarClienteModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const clienteId = button.getAttribute('data-id');

    fetch(`/clientes/${clienteId}`)
        .then(response => response.text())
        .then(text => {
            console.log("Response text: ", text);
            const cliente = JSON.parse(text);
            document.getElementById('editarId').value = cliente.id;
            document.getElementById('editarCodigo').value = cliente.codCliente;
            document.getElementById('editarNombre').value = cliente.nomCliente;
            document.getElementById('editarDireccion').value = cliente.dirCliente;
            document.getElementById('editarTelefono').value = cliente.telCliente;
            document.getElementById('editarFechaIngreso').value = cliente.fechaingCli;
        })
        .catch(error => console.error('Error:', error));
});

function validarFormulario() {
    const codigo = document.getElementById('codigo').value.trim();
    const nombre = document.getElementById('nombre').value.trim();
    const direccion = document.getElementById('direccion').value.trim();
    const telefono = document.getElementById('telefono').value.trim();

    if (codigo === '' || nombre === '' || direccion === '' || telefono === '' ) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben estar completos.',
        });
        return false;
    }

    if (!/^\d{9}$/.test(telefono)) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'El teléfono debe tener 9 dígitos.',
        });
        return false;
    }

    return true;
}

function validarFormularioEditar() {
    const codigo = document.getElementById('editarCodigo').value.trim();
    const nombre = document.getElementById('editarNombre').value.trim();
    const direccion = document.getElementById('editarDireccion').value.trim();
    const telefono = document.getElementById('editarTelefono').value.trim();
    const fechaIngreso = document.getElementById('editarFechaIngreso').value;

    if (codigo === '' || nombre === '' || direccion === '' || telefono === '' || fechaIngreso === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben estar completos.',
        });
        return false;
    }

    if (!/^\d{9}$/.test(telefono)) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'El teléfono debe tener 9 dígitos.',
        });
        return false;
    }

    Swal.fire({
        icon: 'success',
        title: 'Éxito',
        text: 'Cliente editado correctamente.',
    });

    return true;
}

document.addEventListener('DOMContentLoaded', function () {
    const infoClienteModal = document.getElementById('infoClienteModal');
    infoClienteModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const clienteId = button.getAttribute('data-id');

        fetch(`/clientes/${clienteId}`)
            .then(response => response.json())
            .then(cliente => {
                document.getElementById('infoId').textContent = cliente.id;
                document.getElementById('infoCodigo').textContent = cliente.codCliente;
                document.getElementById('infoNombre').textContent = cliente.nomCliente;
                document.getElementById('infoDireccion').textContent = cliente.dirCliente;
                document.getElementById('infoTelefono').textContent = cliente.telCliente;
                document.getElementById('infoFechaIngreso').textContent = cliente.fechaingCli;
            })
            .catch(error => console.error('Error:', error));
    });
});
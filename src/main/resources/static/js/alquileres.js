document.addEventListener('DOMContentLoaded', function () {
    console.log('DOM fully loaded and parsed');

    fetch('/alquileres/generarCodigo')
        .then(response => {
            console.log('Fetch response received:', response);
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('codigo').value = data.codigo;
        })
        .catch(error => console.error('Error:', error));

});

function editarAlquiler(event) {
    event.preventDefault(); // Evita el envío del formulario

    if (!validarFormularioEditar()) {
        return false; // Si la validación falla, no envía el formulario
    }

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
                text: 'Alquiler editado correctamente.',
                timer: 2000, // 5 segundos
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/alquileres'; // Redirige después de mostrar la alerta
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al editar el alquiler.',
            });
        });

    return false; // Evita el envío del formulario
}


function registrarAlquiler(event) {
    event.preventDefault();

    if (!validarFormulario()) {
        return false;
    }

    const form = event.target;

    let fechaPrest = document.getElementById('fechaPrest').value;
    let fechaDev = document.getElementById('fechaDev').value;

    // Si la fecha de préstamo está vacía, asignar la fecha actual
    if (!fechaPrest) {
        fechaPrest = new Date().toISOString().split('T')[0]; // Fecha en formato YYYY-MM-DD
    }

    // Si la fecha de devolución está vacía, asignar la fecha actual
    if (!fechaDev) {
        fechaDev = new Date().toISOString().split('T')[0]; // Fecha en formato YYYY-MM-DD
    }

    // Actualizar los valores en los campos antes de enviar el formulario
    document.getElementById('fechaPrest').value = fechaPrest;
    document.getElementById('fechaDev').value = fechaDev;

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
                text: 'Alquiler registrado correctamente.',
                timer: 2000,
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/alquileres';
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al registrar el alquiler.',
            });
        });

    return false;
}

function validarFormulario() {
    const codigo = document.getElementById('codigo').value;
    const pelicula = document.getElementById('idPelicula').value;
    const empleado = document.getElementById('idEmpleado').value;
    const cliente = document.getElementById('idCliente').value;

    if ( codigo === '' || empleado === '' || pelicula === '' || cliente === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos son obligatorios.',
        });
        return false;
    }

    return true;

}

const editarAlquilerModal = document.getElementById('editarAlquilerModal');
editarAlquilerModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const alquilerId = button.getAttribute('data-id');


    fetch(`/alquileres/${alquilerId}` )
        .then(response => response.text())
        .then(text => {
            console.log("Response text: ", text);
            const alquiler = JSON.parse(text);
            document.getElementById('editarId').value = alquiler.id;
            document.getElementById('editarCodigo').value = alquiler.codAlquiler;
            document.getElementById('editarPrestamo').value = alquiler.fechaPrest;
            document.getElementById('editarDevolucion').value = alquiler.fechaDev;


            console.log(alquiler);  // Verifica qué valores tienen los campos idEmpleado y idCliente


            // Set selected empleado
            const empleadoSelect = document.getElementById('editarEmpleado');
            console.log('Empleado select options:', empleadoSelect.options);  // Imprimir opciones de empleado
            for (let option of empleadoSelect.options) {
                console.log('Comparando empleado:', option.value, 'con', alquiler.idEmpleado.id);
                if (parseInt(option.value) === alquiler.idEmpleado.id) {  // Comparar con idEmpleado.id
                    option.selected = true;
                    break;
                }
            }

            // Set selected pelicula
            const peliculaSelect = document.getElementById('editarPelicula');
            console.log('Pelicula select options:', peliculaSelect.options);  // Imprimir opciones de pelicula
            for (let option of peliculaSelect.options) {
                console.log('Comparando pelicula:', option.value, 'con', alquiler.idPelicula.id);
                if (parseInt(option.value) === alquiler.idPelicula.id) {  // Comparar con idPelicula.id
                    option.selected = true;
                    break;
                }
            }

            // Set selected cliente
            const clienteSelect = document.getElementById('editarCliente');
            console.log('Cliente select options:', clienteSelect.options);  // Imprimir opciones de cliente
            for (let option of clienteSelect.options) {
                console.log('Comparando cliente:', option.value, 'con', alquiler.idCliente.id);
                if (parseInt(option.value) === alquiler.idCliente.id) {  // Comparar con idCliente.id
                    option.selected = true;
                    break;
                }
            }


        })
        .catch(error => console.error('Error:', error));
});

function validarFormularioEditar() {
    const codigo = document.getElementById('editarCodigo').value.trim();
    const prestamo = document.getElementById('editarPrestamo').value.trim();
    const devolucion = document.getElementById('editarDevolucion').value.trim();
    const pelicula = document.getElementById('editarPelicula').value.trim();
    const empleado = document.getElementById('editarEmpleado').value.trim();
    const cliente = document.getElementById('editarCliente').value.trim();

    if (codigo === '' || prestamo === '' || devolucion === '' || pelicula === '' || empleado === '' || cliente === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben estar completos.',
        });
        return false;
    }
    return true;
}

document.addEventListener('DOMContentLoaded', function () {
    const infoAlquilerModal = document.getElementById('infoAlquilerModal');
    infoAlquilerModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const alquileresId = button.getAttribute('data-id');


        fetch(`/alquileres/${alquileresId}`)
            .then(response => response.json())
            .then(alquiler => {
                document.getElementById('infoId').textContent = alquiler.id;
                document.getElementById('infoCodigo').textContent = alquiler.codAlquiler;
                document.getElementById('infoPrestamo').textContent = alquiler.fechaPrest;
                document.getElementById('infoDevolucion').textContent = alquiler.fechaDev;
                document.getElementById('infoPelicula').textContent = alquiler.idPelicula.nomPelicula;
                document.getElementById('infoEmpleado').textContent = alquiler.idEmpleado.nomEmpleado;
                document.getElementById('infoCliente').textContent = alquiler.idCliente.nomCliente;
                console.log(alquiler);
            })

            .catch(error => console.error('Error:', error));
    });
});

function eliminarAlquiler(alquilerId) {
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
            fetch(`/alquileres/eliminar/${alquilerId}`, {
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
                        text: 'Alquiler eliminado correctamente.',
                        timer: 500, //
                        timerProgressBar: true
                    }).then(() => {
                        window.location.href = '/alquileres'; // Redirige después de mostrar la alerta
                    });
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Hubo un problema al eliminar el alquiler.',
                    });
                });
        }
    });
}


const buscar = (id) => {
	
	console.log(id)
	
	fetch(`/empleados/${id}`)
		.then(response => response.json())
		.then(data => {
			
			console.log(data)
		})
		.catch(e => console.log(e));
}

document.addEventListener('DOMContentLoaded', function () {
	
    fetch('/empleados/generarCodigo')
        .then(response => {
			
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

function eliminarEmpleado(clienteId) {
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
            fetch(`/empleados/eliminar/${clienteId}`, {
                method: 'DELETE'
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
                        window.location.href = '/empleados'; // Redirige después de mostrar la alerta
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

const editarEmpleadoModal = document.getElementById('editarEmpleadoModal');
editarEmpleadoModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const empleadoId = button.getAttribute('data-id');

    fetch(`/empleados/${empleadoId}`)
        .then(response => response.text())
        .then(text => {
            console.log("Response text: ", text);
            const empleado = JSON.parse(text);
            document.getElementById('editarId').value = empleado.id;
            document.getElementById('editarCodigo').value = empleado.codEmpleado;
            document.getElementById('editarNombre').value = empleado.nomEmpleado;
            document.getElementById('editarDireccion').value = empleado.dirEmpleado;
            document.getElementById('editarTelefono').value = empleado.telEmpleado;
            document.getElementById('editarFechaIngreso').value = empleado.fechaingEmp;
        })
        .catch(error => console.error('Error:', error));
});

document.addEventListener('DOMContentLoaded', function () {
    const infoEmpleadoModal = document.getElementById('infoEmpleadoModal');
    infoEmpleadoModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const clienteId = button.getAttribute('data-id');

        fetch(`/empleados/${clienteId}`)
            .then(response => response.json())
            .then(cliente => {
                document.getElementById('infoId').textContent = cliente.id;
                document.getElementById('infoCodigo').textContent = cliente.codEmpleado;
                document.getElementById('infoNombre').textContent = cliente.nomEmpleado;
                document.getElementById('infoDireccion').textContent = cliente.dirEmpleado;
                document.getElementById('infoTelefono').textContent = cliente.telEmpleado;
                document.getElementById('infoFechaIngreso').textContent = cliente.fechaingEmp;
            })
            .catch(error => console.error('Error:', error));
    });
});
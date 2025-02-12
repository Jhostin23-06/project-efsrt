document.addEventListener('DOMContentLoaded', function () {
    console.log('DOM fully loaded and parsed');

    fetch('/actores/generarCodigo')
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

function editarActor(event) {
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
                text: 'Actor editado correctamente.',
                timer: 2000, // 5 segundos
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/actores'; // Redirige después de mostrar la alerta
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al editar el actor.',
            });
        });

    return false; // Evita el envío del formulario
}

const editarActorModal = document.getElementById('editarActorModal');
editarActorModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const actorId = button.getAttribute('data-id');

    fetch(`/actores/${actorId}`)
        .then(response => response.json())
        .then(actor => {
            document.getElementById('editarId').value = actor.id;
            document.getElementById('editarCodigo').value = actor.codActor;
            document.getElementById('editarNombre').value = actor.nomActor;
            console.log(document.getElementById('editarNombre').value);
            console.log(actor.nomActor);
            console.log(actor.codActor);
        })
        .catch(error => console.error('Error:', error));
});

function validarFormulario() {

    const codigo = document.getElementById('codigo').value.trim();
    const nombre = document.getElementById('nombre').value.trim();

    if (codigo === '' || nombre === '' ) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben estar completos.',
        });
        return false;
    }

    return true;

}

function validarFormularioEditar() {
    const codigo = document.getElementById('editarCodigo').value.trim();
    const nombre = document.getElementById('editarNombre').value.trim();

    if (codigo === '' || nombre === '') {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Todos los campos deben estar completos.',
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

function registrarActor(event) {
    event.preventDefault(); // Evita el envío del formulario

    const form = event.target;
    const formData = new FormData(form);

    if (!validarFormulario()) {
        return false; // Si la validación falla, no envía el formulario
    }

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
                window.location.href = '/actores'; // Redirige después de mostrar la alerta
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
document.addEventListener('DOMContentLoaded', function () {
    console.log('DOM fully loaded and parsed');

    fetch('/peliculas/generarCodigo')
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

function registrarPelicula(event) {
    event.preventDefault(); // Prevent form submission

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
                text: 'Película registrada correctamente.',
                timer: 2000, // 2 seconds
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/peliculas'; // Redirect after alert
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al registrar la película.',
            });
        });

    return false; // Prevent form submission
}

function editarPelicula(event) {
    event.preventDefault(); // Prevent form submission

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
                text: 'Película editada correctamente.',
                timer: 2000, // 2 seconds
                timerProgressBar: true
            }).then(() => {
                window.location.href = '/peliculas'; // Redirect after alert
            });
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al editar la película.',
            });
        });

    return false; // Prevent form submission
}

function eliminarPelicula(peliculaId) {
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
            fetch(`/peliculas/eliminar/${peliculaId}`, {
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
                        text: 'Película eliminada correctamente.',
                        timer: 2000, // 2 seconds
                        timerProgressBar: true
                    }).then(() => {
                        window.location.href = '/peliculas'; // Redirect after alert
                    });
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Hubo un problema al eliminar la película.',
                    });
                });
        }
    });
}

const editarPeliculaModal = document.getElementById('editarPeliculaModal');
editarPeliculaModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const peliculaId = button.getAttribute('data-id');

    fetch(`/peliculas/${peliculaId}`)
        .then(response => response.text())
        .then(text => {
            console.log("Response text: ", text);
            const pelicula = JSON.parse(text);
            document.getElementById('editarId').value = pelicula.id;
            document.getElementById('editarCodigo').value = pelicula.codPelicula;
            document.getElementById('editarNombre').value = pelicula.nomPelicula;
            document.getElementById('editarGenero').value = pelicula.idGenero;
            document.getElementById('editarDuracion').value = pelicula.duracion;
            document.getElementById('editarAnio').value = pelicula.anio;
            document.getElementById('editarCopias').value = pelicula.copias;
        })
        .catch(error => console.error('Error:', error));
});

function validarFormulario() {
    const codigo = document.getElementById('codigo').value.trim();
    const nombre = document.getElementById('nombre').value.trim();
    const genero = document.getElementById('genero').value.trim();
    const duracion = document.getElementById('duracion').value.trim();
    const anio = document.getElementById('anio').value.trim();
    const copias = document.getElementById('copias').value.trim();

    if (codigo === '' || nombre === '' || genero === '' || duracion === '' || anio === '' || copias === '') {
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
    const genero = document.getElementById('editarGenero').value.trim();
    const duracion = document.getElementById('editarDuracion').value.trim();
    const anio = document.getElementById('editarAnio').value.trim();
    const copias = document.getElementById('editarCopias').value.trim();

    if (codigo === '' || nombre === '' || genero === '' || duracion === '' || anio === '' || copias === '') {
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
        text: 'Película editada correctamente.',
    });

    return true;
}

document.addEventListener('DOMContentLoaded', function () {
    const infoPeliculaModal = document.getElementById('infoPeliculaModal');
    infoPeliculaModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const peliculaId = button.getAttribute('data-id');

        fetch(`/peliculas/${peliculaId}`)
            .then(response => response.json())
            .then(pelicula => {
                document.getElementById('infoId').textContent = pelicula.id;
                document.getElementById('infoCodigo').textContent = pelicula.codPelicula;
                document.getElementById('infoNombre').textContent = pelicula.nomPelicula;
                document.getElementById('infoGenero').textContent = pelicula.idGenero;
                document.getElementById('infoDuracion').textContent = pelicula.duracion;
                document.getElementById('infoAnio').textContent = pelicula.anio;
                document.getElementById('infoCopias').textContent = pelicula.copias;
            })
            .catch(error => console.error('Error:', error));
    });
});
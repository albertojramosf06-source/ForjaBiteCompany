const tipoDocumento = document.getElementById("tipoDocumento");
const datosDocumento = document.getElementById("datos-documento");
const campoPais = document.getElementById("campo-pais");
const paisEmisor = document.getElementById("paisEmisor");
const numeroDocumento = document.getElementById("numeroDocumento");
const etiquetaDocumento = document.getElementById("etiqueta-documento");

function actualizarDocumento() {
    const esDni = tipoDocumento.value === "DNI";
    const esPasaporte = tipoDocumento.value === "PASAPORTE";
    const seleccionado = esDni || esPasaporte;

    datosDocumento.hidden = !seleccionado;
    numeroDocumento.disabled = !seleccionado;
    numeroDocumento.required = seleccionado;
    numeroDocumento.maxLength = esDni ? 9 : 32;
    numeroDocumento.placeholder = esDni ? "Tu DNI" : "Tu número de pasaporte";
    etiquetaDocumento.textContent = esDni ? "DNI" : "Número de pasaporte";
    campoPais.hidden = !esPasaporte;
    paisEmisor.disabled = !esPasaporte;
    paisEmisor.required = esPasaporte;
}

tipoDocumento.addEventListener("change", () => {
    numeroDocumento.value = "";
    actualizarDocumento();
});
actualizarDocumento();

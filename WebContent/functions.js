/*function validacion_busqueda_matricula() {
	console.log("Comprobación búsqueda matrícula");
	var x = document.forms["buscar_matricula"]["matricula"].value;
	if (validar_matricula(x)){ 
		//console.log("Final de validación, OK");
		return true;
	} else {
		console.log("Final de validación, Error");
		return false;
	};
	
}

function validacion_modificar_alumno() {
	var a = document.forms["modificar_alumno"]["dniAlumno"].value;
	if (validar_matricula(a)) {
		return true;
	} else {
		return false;
	};
}*/

/*function validacion_modificar_vehiculo_bd() {
	var a = document.forms["modificar_vehiculo"]["matriculavieja"].value;
	if (validar_matricula_bd(a)) {
		return true;
	} else {
		return false;
	};
}*/

/*function validacion_anadir_vehiculo() {
	var x = document.forms["anyadir_vehiculo"]["matricula"].value;
	if (validar_matricula(x)) {
		return true;
	} else {
		return false;
	};
}

function validacion_borrar_vehiculo() {
	var x = document.forms["borrar_vehiculo"]["matricula"].value;
	if (validar_matricula(x)) {
		return true;
	} else {
		return false;
	};
}

function validar_matricula(x) {
	if (x == null || x == "") {
		alert("Escribe la matrícula");
		console.log("Comprobación nula");
		return false;
	} else if(x.length!=7) {
		alert("No has introducido una matrícula válida (núm caracteres)");
		console.log("Error, número de caracteres matrícula");
		return false;
	} else {
		var expreg = /^[0-9]{4}[A-Z,a-z]{3}$/;
		if (expreg.test(x)) {
			//alert("La matrícula es correcta");
			//console.log("Correcto");
			return true;
		} else {
			//alert("La matrícula NO es correcta");
			//console.log("Error en formato matrícula");
			return false;
		}
	}
}*/
	
/*function validar_matricula_bd(x) {
	if (x == null || x == "") {
		alert("Escribe la matrícula");
		window.open( "Escribe la matrícula" );
		console.log("Comprobación nula");
		return false;
	} else if(x.length!=7) {
		alert("No has introducido una matrícula válida (núm caracteres)");
		console.log("Error, número de caracteres matrícula");
		return false;
	} else {
		var expreg = /^[0-9]{4}[A-Z,a-z]{3}$/;
		if (expreg.test(x)) {
			//alert("La matrícula es correcta");
			//console.log("Correcto");
			return true;
		} else {
			//alert("La matrícula NO es correcta");
			//console.log("Error en formato matrícula");
			return false;
		}
	}
}*/

/*function validar_alumno(x) {
	if (x == null || x == "") {
		alert("Escribe el DNI");
		console.log("Comprobación nula");
		return false;
	} else if(x.length!=9) {
		alert("No has introducido un DNI válido (núm caracteres)");
		console.log("Error, número de caracteres DNI");
		return false;
	} else {
		var expreg = /^[0-9]{8}[A-Z,a-z]{1}$/;
		if (expreg.test(x)) {
			//alert("La matrícula es correcta");
			//console.log("Correcto");
			return true;
		} else {
			//alert("La matrícula NO es correcta");
			//console.log("Error en formato matrícula");
			return false;
		}
	}
}*/
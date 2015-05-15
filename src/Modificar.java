import com.zubiri.matriculas.Alumno;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Modificar
 */
public class Modificar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO="root";
	private static final String CONTRA="zubiri";
	static final String URL_BD="jdbc:mysql://localhost/matriculasBD";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Modificar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType( "text/html; charset=iso-8859-1" );
		
		Connection con = null;	
		Statement sentencia = null;
		
		System.out.println("Empieza modificando");

		Boolean confirmacion = Boolean.parseBoolean(request.getParameter("confirmacion"));
		
		Alumno alumno = new Alumno(request.getParameter("dniAlumno"),"","",0,"");
		
		try {
			
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);
			
			sentencia = con.createStatement();
			
			String sql;		    
			System.out.println("Referencia: " + alumno.getDni());
			
			sql="SELECT * FROM alumnos WHERE dni=\""+alumno.getDni()+"\"";
			
			ResultSet buscar = sentencia.executeQuery(sql);
			int cont = 0;
			while (buscar.next()) {
				cont++;
			}
			if (cont > 0) {
				System.out.println("Contador: " + cont);
				if (confirmacion!=true) {
					formulario_modificar(response,request.getParameter("dniAlumno"));
				} else {
					alumno.setDni(request.getParameter("dniAlumno"));
					alumno.setNombre(request.getParameter("nombre"));
					alumno.setApellido(request.getParameter("apellido"));
					alumno.setAnyoInscripcion(Integer.parseInt(request.getParameter("anyo_inscripcion")));
					alumno.setCiclo(request.getParameter("ciclo"));
					String cambios="";
					cambios="dni = \""+alumno.getDni()+"\",";
					cambios+=" nombre = \""+alumno.getNombre()+"\",";
					cambios+=" apellido = \""+alumno.getApellido()+"\",";
					cambios+=" anyo_inscripcion = "+alumno.getAnyoInscripcion()+",";
					cambios+=" ciclo = \""+alumno.getCiclo()+"\"";
					
					try {
						// Register JDBC driver
						Class.forName("com.mysql.jdbc.Driver");
						// Open a connection
						con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);			        
						sentencia = con.createStatement();
						
						System.out.println("UPDATE alumnos SET "+cambios+" WHERE matricula=\""+alumno.getDni()+"\"");
						sql="UPDATE alumnos SET "+cambios+" WHERE dni=\""+alumno.getDni()+"\"";
						int modificar = sentencia.executeUpdate(sql);
						System.out.println("Valor crear: " + modificar);
						if (modificar==1) {
							response(response, "Se ha modificado el alumno con DNI " + alumno.getDni() + ".<br>" + cambios);
						} else {
							response(response, "¡Error! No se ha modificado el vehículo, compruebe las matrícula1: " + alumno.getDni());
						}
						con.close();
					} catch(ArrayIndexOutOfBoundsException e) {
						response(response, "No se encontró el alumno");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				response(response, "No se encontró el alumno");
			}
			con.close();
		
		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "No se encontro el alumno");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void response(HttpServletResponse response,String msg) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
			out.println("<title> Respuesta </title>");
			out.println("<link rel='stylesheet' type='text/css' href='stylebd.css'>");
		out.println("</head>");
		out.println("<body>");				
		out.println("<p>" + msg + "</p>");
		out.println("<a href='index.html'> <button> Volver </button> </a>");
		out.println("</body>");
		out.println("</html>");
	}
	
	private void formulario_modificar(HttpServletResponse response, String referencia) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		System.out.println("Se está modificando el alumno con DNI: " + referencia);

		Connection con = null;	
		Statement sentencia = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);
			
			sentencia = con.createStatement();
			
			String sql;		    
			System.out.println("Referencia: "+referencia);		     
			//sql="SELECT matricula, marca FROM coches WHERE matricula='"+referencia+"'";
			sql="SELECT * FROM alumnos WHERE dni=\""+referencia+"\"";
			ResultSet buscar = sentencia.executeQuery(sql);
			String dni = null;
			String nombre = null;
			String apellido = null;
			Integer anyo_inscripcion = 0;
			String ciclo = null;
			while (buscar.next()) {
				dni = buscar.getString("dni");
				nombre = buscar.getString("nombre");
				apellido = buscar.getString("apellido");
				anyo_inscripcion = buscar.getInt("anyo_inscripcion");
				ciclo = buscar.getString("ciclo");
				System.out.println("DNI: " + dni);
				System.out.println("Nombre: " + nombre);
				System.out.println("Apellido: " + apellido);
				System.out.println("Año Inscripción: " + anyo_inscripcion);
				System.out.println("Ciclo: " + ciclo);
				
			}

			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
				out.println("<title> Modificar alumno </title>");
				out.println("<link rel='stylesheet' type='text/css' href='stylebd.css'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<fieldset>	<legend> Modificar Vehículo " + referencia + "</legend>");
				out.println("<form name='modificar_alumno' method='post' onsubmit='return validacion_modificar_alumno()' action='Modificar'>");
					out.println("<input name='gestion' hidden='true' type='text' value='modificar_vehiculo'/>");
					out.println("<input name='dniAlumno' type='text' value='" + referencia + "' hidden='true'/> <br>");
					out.println("<label>DNI a modificar: </label> <input type='text' value='" + referencia + "' disabled/> <br>");
					out.println("<label>Nombre: </label> <input name='nombre' type='text' id='nombre' value='" + nombre + "' /> <br>");
					out.println("<label>Apellido: </label> <input name='apellido' type='text' id='apellido' value='"+apellido+"' /> <br>");
					out.println("<label>Año de inscripción </label> <input name='anyo_inscripcion' type='text' id='anyo_inscripcion' value='"+anyo_inscripcion+"' /> <br>");
					out.println("<label> Ciclo </label> <input name='ciclo' type='text' id='ciclo' value='"+ciclo+"' /> <br>");
					out.println("<input name=\"confirmacion\" hidden=\"true\" type=\"text\" value='true'></input>");
					out.println("<input type='submit' id='submit' value='Modificar'>");
				out.println("</form>");
			out.println("</fieldset>");
			out.println("<br> <a href='index.html'> <button> Volver </button> </a>");
			out.println("<script type=\"text/javascript\">");
				out.println("function validacion_modificar_vehiculo_bd() { var a = document.forms[\"modificar_vehiculo\"][\"matriculanueva\"].value; if (validar_matricula_bd(a)) { return true; } else { return false; };}");
				out.println("function validar_matricula_bd(x) { if (x == null || x == \"\") { alert(\"Escribe la matrícula\"); console.log(\"Comprobación nula\"); return false; } else if(x.length!=7) { alert(\"No has introducido una matrícula válida (núm caracteres)\"); console.log(\"Error, número de caracteres matrícula\"); return false; } else { var expreg = /^[0-9]{4}[A-Z,a-z]{3}$/; if (expreg.test(x)) { return true; } else {	alert(\"La matrícula NO es correcta\");	console.log(\"Error en formato matrícula\"); return false; } } }");
			out.println("</script>");
			out.println("</body>");
			out.println("</html>");

			con.close();

		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "no se encontro el vehiculo");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

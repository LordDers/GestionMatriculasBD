import com.zubiri.matriculas.Alumno;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Matricular
 */
public class Insertar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO="root";
	private static final String CONTRA="zubiri";
	private static final String URL_BD="jdbc:mysql://localhost/matriculasBD";

    /**
     * Default constructor. 
     */
    public Insertar() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Empieza añadiendo");
		//alumno('dni', 'nombre', 'apeliido', 'anyo_inscripcion', 'ciclo');		
		
		String dni = request.getParameter("dni");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		int anyo = Integer.parseInt(request.getParameter("anyo_inscripcion"));
		String ciclo = request.getParameter("ciclo");
		System.out.println("Nuevo alumno"+dni);
		
		Alumno nuevoAlumno = new Alumno(dni,nombre,apellido,anyo,ciclo);
		
		Connection con = null;	
		Statement sentencia = null;
		try {
			
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);
			
			sentencia = con.createStatement();
			
			String sql;
			//INSERT INTO coches VALUES ("0000AAA", "prueba1", true, true, 4, 100);
			System.out.println("INSERT INTO alumnos VALUES (\""+
																nuevoAlumno.getDni()+"\",\""+
																nuevoAlumno.getNombre()+"\","+
																nuevoAlumno.getApellido()+","+
																nuevoAlumno.getAnyoInscripcion()+",\""+
																nuevoAlumno.getCiclo()+"\")");
			
			sql="INSERT INTO alumnos VALUES (\""+
					nuevoAlumno.getDni()+"\",\""+
					nuevoAlumno.getNombre()+"\","+
					nuevoAlumno.getApellido()+","+
					nuevoAlumno.getAnyoInscripcion()+",\""+
					nuevoAlumno.getCiclo()+"\")";
			
			int crear = sentencia.executeUpdate(sql);
			System.out.println("Valor crear: "+crear);
			if (crear==1){
				response(response, dni);
			}else{
				response(response,"error al matricular el alumno", "relleno");
			}	
			con.close();    
			
		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "no se encontro el vehiculo");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Respuesta simple
	private void response(HttpServletResponse response, String msg, String relleno) throws IOException {
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
	
	//muestra de alumno matriculado
	private void response(HttpServletResponse response, String dni) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<form name=\"buscar_alumno\" method=\"post\" onsubmit=\"return validacion_busqueda_alumno()\" action=\"BuscarAlumno\">");
				out.println("<label> Alumno matriculado: </label>");
				out.println("<input name=\"alumno\" type=\"text\" placeholder=\""+dni+"\"/>");
				out.println("<input type=\"submit\" id=\"submit\" value=\"mostrar\">");
				out.println("</form>");
		out.println("<a href='index.html'> <button> Volver </button> </a>");
		out.println("</body>");
		out.println("</html>");
	}

}

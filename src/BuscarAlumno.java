import com.zubiri.matriculas.Alumno;

import java.io.PrintWriter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuscarAlumno
 */
public class BuscarAlumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO="root";
	private static final String CONTRA="zubiri";
	private static final String URL_BD="jdbc:mysql://localhost/matriculasBD";

    /**
     * Default constructor. 
     */
    public BuscarAlumno() {
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
		response.setContentType( "text/html; charset=iso-8859-1" );
		
		Connection con = null;	
		Statement sentencia = null;
		
		System.out.println("Empieza buscando");

		String referencia=request.getParameter("alumno");
		
		try {
			
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//System.out.println("bd: "+URL_BD+" usuario: "+USUARIO+" contra: "+CONTRA);
			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);
			
			sentencia = con.createStatement();
			
			String sql;		    
			System.out.println("Referencia: "+referencia);			
			//sql="SELECT * FROM alumnos WHERE dni='12345678b'";
			sql="SELECT * FROM alumnos WHERE dni=\""+referencia+"\"";
			System.out.println("Sql: "+sql);			
			
			ResultSet buscar = sentencia.executeQuery(sql);
			int cont = 0;
			String dni = null;
			String nombre = null;
			String apellido = null;
			Integer anyo = 0;
			String ciclo = null;
			while (buscar.next()) {
				dni = buscar.getString("dni");
				nombre = buscar.getString("nombre");
				apellido = buscar.getString("apellido");
				anyo = buscar.getInt("anyo_inscripcion");
				ciclo = buscar.getString("ciclo");
				System.out.println("Dni: "+dni);
				System.out.println("Nombre: "+nombre);				
				cont++;
			}			
			
			//alumno('dni', 'nombre', 'apeliido', 'anyo_inscripcion', 'ciclo');				
			Alumno encontrado = new Alumno(dni,nombre,apellido,anyo,ciclo);
			
			if (cont > 0) {
				response(response, encontrado);
			} else {
				response(response, "No se encontró el vehículo");
			}
			con.close();
		
		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "no se encontro el vehiculo");
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	// Respuesta simple
	private void response(HttpServletResponse response, String msg) throws IOException {
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
	
	// Buscar y Añadir
	private void response(HttpServletResponse response, Alumno encontrado) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
			out.println("<title>  </title>");
			out.println("<link rel='stylesheet' type='text/css' href='stylebd.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table align=\"center\" border=5><tr>");
			out.println("<th>DNI</th>");
			out.println("<th>Nombre</th>");
			out.println("<th>Apellido</th>");
			out.println("<th>Año de inscripción</th>");
			out.println("<th>Ciclo</th>");
		out.println("</tr><tr>");
			out.println("<td>" + encontrado.getDni() + "</td>");
			out.println("<td>" + encontrado.getNombre() + "</td>");
			out.println("<td>" + encontrado.getApellido() + "</td>");		
			out.println("<td>" + encontrado.getAnyoInscripcion() + "</td>");		
			out.println("<td>" + encontrado.getCiclo() + "</td>");
		out.println("</tr><tr>");
			out.println("<td colspan=6>");
				out.println("<center> <a href='index.html'> <button> Volver </button> </a> </center>");
			out.println("</td>");
		out.println("</tr></table>");
		out.println("</body>");
		out.println("</html>");
	}

}

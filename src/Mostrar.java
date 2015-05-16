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
 * Servlet implementation class MostrarAlumnos
 */
public class Mostrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO="root";
	private static final String CONTRA="zubiri";
	static final String URL_BD="jdbc:mysql://localhost/matriculasBD";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mostrar() {
        super();
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
		response.setContentType( "text/html; charset=iso-8859-1" );
		
		Connection con = null;	
		Statement sentencia = null;
		try {
			System.out.println("En el try crear");
			
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);

			sentencia = con.createStatement();	        
			String sql;

			sql="CREATE TABLE IF NOT EXISTS alumnos (dni VARCHAR(9) NOT NULL, nombre VARCHAR(20), apellido VARCHAR(25), anyo_inscripcion INTEGER(4), ciclo VARCHAR(20), PRIMARY KEY(dni))";

			sentencia.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("En el catch 1");
			System.err.println("Error "+ e);
		}

		String gestion=request.getParameter("gestion");
		System.out.println(gestion);
		if (gestion.equals("Mostrar_Alumnos")) {
			System.out.println("Empieza mostrando");

			try {
				System.out.println("En el try mostrar");
				// Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");

				// Open a connection
				con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);

				sentencia = con.createStatement();

				String sql;		        
				sql="SELECT * FROM alumnos";
				ResultSet mostrar = sentencia.executeQuery(sql);
				int cont=0;
				String dni = null;
				String nombre = null;
				String apellido = null;
				Integer anyo = 0;
				String ciclo = null;
				System.out.println("Pre while");
				while (mostrar.next()) {	
					dni = mostrar.getString("dni");
					nombre = mostrar.getString("nombre");
					apellido = mostrar.getString("apellido");
					anyo = mostrar.getInt("anyo_inscripcion");
					ciclo = mostrar.getString("ciclo");
					System.out.println("Dni: "+dni);
					System.out.println("Nombre: "+nombre);
					cont++;
				}
				System.out.println("Post while");
				
				Alumno encontrados = new Alumno(dni,nombre,apellido,anyo,ciclo);
				
				if (cont > 0) {
					response(response, encontrados);
				} else {
					//response(response, "No se encontró alumno");
				}
				con.close();

			} catch(Exception e) {
				System.out.println("En el catch 2");
				System.err.println("Error "+ e);
			}
		}
	}
	// Mostrar
	private void response(HttpServletResponse response, Alumno encontrados) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
			out.println("<title> Alumnos </title>");
			out.println("<link rel='stylesheet' type='text/css' href='stylebd.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>-------------------------------</p>");
		for (int i = 0; i<encontrados.legth; i++){
				out.println("<p>-------------------------------</p>");
				out.println("<p> <b>DNI:</b> " + encontrados.getDni(i) + " | ");
				out.print(" <b>Nombre:</b> " + encontrados.getNombre(i) + " | ");
				out.print(" <b>Nombre:</b> " + encontrados.getApellido(i) + " | ");
				out.print(" <b>Ciclo:</b> " + encontrados.getAnyoInscripcion(i) + "</p>");
				out.print(" <b>Ciclo:</b> " + encontrados.getCiclo(i) + "</p>");
				out.println("<p>-------------------------------</p>");
		}
		out.println("<a href='index.html'> <button> Volver </button> </a>");
		out.println("</body>");
		out.println("</html>");
	}
}
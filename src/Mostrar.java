import com.zubiri.matriculas.Alumno;
import com.zubiri.matriculas.Alumnos;

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

public class Mostrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO="root";
	private static final String CONTRA="zubiri";
	static final String URL_BD="jdbc:mysql://localhost/matriculasBD";

    public Mostrar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

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
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);

			sentencia = con.createStatement();

			String sql;		        
			sql="SELECT * FROM alumnos";
			ResultSet mostrar = sentencia.executeQuery(sql);
			int cont = 0;
			String dni = null;
			String nombre = null;
			String apellido = null;
			Integer anyo = 0;
			String ciclo = null;
			while (mostrar.next()) {	
				dni = mostrar.getString("dni");
				nombre = mostrar.getString("nombre");
				apellido = mostrar.getString("apellido");
				anyo = mostrar.getInt("anyo_inscripcion");
				ciclo = mostrar.getString("ciclo");
				Alumno encontrado = new Alumno(dni,nombre,apellido,anyo,ciclo);
				Alumnos.anyadirAlumno(encontrado);
				cont++;
			}
			
			if (cont > 0) {
				response(response);
			} else {
				//response(response, "No se encontró alumno");
			}
			con.close();

		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "no se encontro el alumno");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// Mostrar
	private void response(HttpServletResponse response) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
			out.println("<title> Alumnos matriculados </title>");
			out.println("<link rel='stylesheet' type='text/css' href='stylebd.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>-------------------------------</p>");	
		for (int i = 0; i<Alumnos.alumnos.size(); i++){
				out.println("<p> <b>DNI:</b> " + Alumnos.alumnos.get(i).getDni() + " | ");
				out.print(" <b>Nombre:</b> " + Alumnos.alumnos.get(i).getNombre() + " | ");
				out.print(" <b>Apellido:</b> " + Alumnos.alumnos.get(i).getApellido() + " | ");
				out.print(" <b>Año inscripcion:</b> " + Alumnos.alumnos.get(i).getAnyoInscripcion() + "</p>"); 
				out.print(" <b>Ciclo:</b> " + Alumnos.alumnos.get(i).getCiclo() + "</p>");
				out.println("<p>-------------------------------</p>");
		}
		Alumnos.alumnos.clear();
		out.println("<a href='index.html'> <button> Volver </button> </a>");
		out.println("</body>");
		out.println("</html>");
	}
}

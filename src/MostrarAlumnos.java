

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
 * Servlet implementation class MostrarAlumno
 */
public class MostrarAlumnos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO="root";
	private static final String CONTRA="zubiri";
	static final String URL_BD="jdbc:mysql://localhost/matriculasBD";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MostrarAlumnos() {
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
		if (gestion.equals("mostrar_vehiculos")) {
			System.out.println("Empieza mostrando");

			try {
				System.out.println("En el try mostrar");
				// Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");

				// Open a connection
				con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);

				sentencia = con.createStatement();

				String sql;		        
				sql="SELECT matricula, marca FROM coches";
				ResultSet mostrar = sentencia.executeQuery(sql);
				int cont=0;
				String [] matricula = new String[100];
				String [] marca = new String[100];
				System.out.println("Pre while");
				while (mostrar.next()) {	
					System.out.println("Matrícula:"+mostrar.getString("matricula"));
					matricula[cont] = mostrar.getString("matricula");
					System.out.println("Matrícula "+matricula[cont]);
					marca[cont] = mostrar.getString("marca");
					System.out.println("Marca "+marca[cont]);
					cont++;
				}
				System.out.println("Post while");

				con.close();    
				response(response,matricula,marca);

			} catch(Exception e) {
				System.out.println("En el catch 2");
				System.err.println("Error "+ e);
			}
		}
	}
	
	// Mostrar
	private void response(HttpServletResponse response, String [] matricula, String [] marca) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
			out.println("<title> Vehículos </title>");
			out.println("<link rel='stylesheet' type='text/css' href='stylebd.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<p>-------------------------------</p>");
		for (int i=0; i<matricula.length; i++) {	
			if (matricula[i] == null) {
				break;
			} else {
				//out.println("<p>-------------------------------</p>");
				out.println("<p> <b>Matrícula:</b> " + matricula[i] + " | ");
				out.print(" <b>Marca:</b> " + marca[i] + "</p>");
				out.println("<p>-------------------------------</p>");
			}
		}
		out.println("<a href='index.html'> <button> Volver </button> </a>");
		out.println("</body>");
		out.println("</html>");
	}
}

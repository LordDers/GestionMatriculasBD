import com.zubiri.matriculas.Alumno;

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
	static final String URL_BD="jdbc:mysql://localhost/pruebas_java";

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
		
		System.out.println("Empieza modificando");

		String referencia=request.getParameter("matricula");
		
		try {
			
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);
			
			sentencia = con.createStatement();
			
			String sql;		    
			System.out.println("Referencia: "+referencia);		     
			//sql="SELECT * FROM alumnos WHERE dni=''";
			sql="SELECT * FROM alumnos WHERE dni=\""+referencia+"\"";
			
			ResultSet buscar = sentencia.executeQuery(sql);
			int cont = 0;
			String matricula = null;
			String marca = null;
			Boolean motor = false;
			Boolean automatico = false;
			Integer n_ruedas = 0;
			Integer consumo = 0;
			while (buscar.next()) {
				matricula = buscar.getString("matricula");
				marca = buscar.getString("marca");
				motor = buscar.getBoolean("motor");
				automatico = buscar.getBoolean("automatico");
				n_ruedas = buscar.getInt("n_ruedas");
				consumo = buscar.getInt("consumo");
				System.out.println("Matrícula: "+matricula);
				System.out.println("Marca: "+marca);
				//response(response,matricula,marca,motor,automatico,n_ruedas,consumo);
				cont++;
			}
			if (cont > 0) {
				response(response,matricula,marca,motor,automatico,n_ruedas,consumo);
			} else {
				response(response, "No se encontró el vehículo");
			}
			con.close();
		
		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "no se encontro el vehiculo");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Respuesta simple
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
				out.println("<th>Matrícula</th>");
				out.println("<th>Marca</th>");
				out.println("<th>Motor</th>");
				out.println("<th>Automático</th>");
				out.println("<th>Número de ruedas</th>");
				out.println("<th>Consumo</th>");
			out.println("</tr><tr>");
				out.println("<td>" + matricula + "</td>");
				out.println("<td>" + marca + "</td>");
				if (motor) {
					out.println("<td>Sí</td>");
				} else {
					out.println("<td>No</td>");
				}			
				if (automatico) {
					out.println("<td>Sí</td>");
				} else {
					out.println("<td>No</td>");
				}			
				out.println("<td>" + n_ruedas + "</td>");
				out.println("<td>" + consumo + "</td>");
			out.println("</tr><tr>");
				out.println("<td colspan=6>");
					out.println("<center> <a href='index.html'> <button> Volver </button> </a> </center>");
				out.println("</td>");
			out.println("</tr></table>");
			out.println("</body>");
			out.println("</html>");
		}

		
	}

}

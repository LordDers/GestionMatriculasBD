import com.zubiri.matriculas.Alumno;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuscarAlumno
 */
public class BuscarAlumno extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		
		//private static final String USUARIO = "root";
		final String USUARIO = "root";
		final String CONTRA = "zubiri";
		final String URL_BD = "jdbc:mysql://localhost/pruebas_java";
		
		System.out.println("Empieza buscando");
		String referencia=request.getParameter("matricula");
		
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
			while (buscar.next()) {
				/*matricula = buscar.getString("matricula");
				marca = buscar.getString("marca");
				motor = buscar.getBoolean("motor");
				automatico = buscar.getBoolean("automatico");
				n_ruedas = buscar.getInt("n_ruedas");
				consumo = buscar.getInt("consumo");
				System.out.println("Matrícula: "+matricula);
				System.out.println("Marca: "+marca);*/
				
				response(response,Alumno alumno);
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
		
	}

}

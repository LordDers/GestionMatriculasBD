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

import com.zubiri.matriculas.Alumno;

/**
 * Servlet implementation class Borrar
 */
public class Borrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO="root";
	private static final String CONTRA="zubiri";
	static final String URL_BD="jdbc:mysql://localhost/matriculasBD";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Borrar() {
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
		System.out.println("Borrando");
		
		Alumno alumno = new Alumno(request.getParameter("dniAlumno"),"","",0,"");
		
		//String sentenciado = request.getParameter("dniAlumno");
		Boolean confirmacion = Boolean.parseBoolean(request.getParameter("confirmacion"));

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);
			
			sentencia = con.createStatement();
			
			String sql;		    
			System.out.println("Referencia: "+alumno.getDni());		     
			//sql="SELECT matricula, marca FROM coches WHERE matricula='"+referencia+"'";
			sql="SELECT * FROM alumnos WHERE dni=\""+alumno.getDni()+"\"";
			
			ResultSet buscar = sentencia.executeQuery(sql);
			int cont = 0;
			while (buscar.next()) {
				cont++;
			}
			if (cont > 0) {
				System.out.println("Contador: " + cont);
				if (confirmacion!=true) {
					confirmacion=false;
					response(response, "¿Seguro que quieres borrar el alumno?", alumno.getDni());
				} else {
					try {					
						// Register JDBC driver
						Class.forName("com.mysql.jdbc.Driver");
						// Open a connection
						con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);			        
						sentencia = con.createStatement();

						System.out.println("DELETE FROM alumnos where dni=\""+alumno.getDni()+"\"");
						sql="DELETE FROM alumnos where dni=\""+alumno.getDni()+"\"";
						int borrar = sentencia.executeUpdate(sql);
						System.out.println("Valor borrar: " + borrar);
						if (borrar==1) {
							response(response, "Se ha borrado el alumno");
						} else {
							response(response, "No se ha borrado el alumno, compruebe el DNI: " + alumno.getDni() + ".");
						}
						con.close();			    	
					} catch(ArrayIndexOutOfBoundsException e) {
						//response(response, "no se encontro el vehiculo");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				response(response, "No se encontró el alumno");
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "no se encontro el vehiculo");
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
	
	private void response(HttpServletResponse response,String msg ,String dni) throws IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
			out.println("<title> Borrar alumno </title>");
			out.println("<link rel='stylesheet' type='text/css' href='stylebd.css'>");
		out.println("</head>");
		out.println("<body align='center'>");
		out.println("<p>" + msg + "</p>");
		out.println("<p>DNI: " + dni + "</p>");
		out.println("<form name=\"borrar_vehiculo\" method=\"post\" action=\"Borrar\" style='margin-right: auto;'>");
			out.println("<input name='gestion' hidden='true' type='text'  value='borrar_vehiculo'/>");
			out.println("<input name=\"dniAlumno\" hidden=\"true\" type=\"text\"  value=" + dni + "></input>");
			out.println("<input name=\"confirmacion\" hidden=\"true\" type=\"text\"  value='true'></input>");
			out.println("<p> <input type='submit' id='submit' value='Borrar'> </p>");
		out.println("</form>");
		out.println("<a href='index.html'> <button> Volver </button> </a>");
		out.println("</body>");
		out.println("</html>");
	}
}

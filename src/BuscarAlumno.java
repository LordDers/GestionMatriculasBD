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

		Boolean confirmacion = Boolean.parseBoolean(request.getParameter("confirmacion"));
		String matriculavieja1 = request.getParameter("matriculavieja");
		
		try {
			
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);
			
			sentencia = con.createStatement();
			
			String sql;		    
			System.out.println("Referencia: "+matriculavieja1);		     
			//sql="SELECT matricula, marca FROM coches WHERE matricula='"+referencia+"'";
			sql="SELECT * FROM coches WHERE matricula=\""+matriculavieja1+"\"";
			
			ResultSet buscar = sentencia.executeQuery(sql);
			int cont = 0;
			while (buscar.next()) {
				cont++;
			}
			if (cont > 0) {
				System.out.println("Contador: " + cont);
				if (confirmacion!=true) {
					//formulario_modificar(response,request.getParameter("matriculavieja"));
				} else {
					int n_ruedas = Integer.parseInt(request.getParameter("numruedas"));
					boolean motor = Boolean.parseBoolean(request.getParameter("motor"));
					String marca = request.getParameter("marca");
					String matriculanueva = request.getParameter("matriculanueva");
					String matriculavieja = request.getParameter("matriculavieja");
					boolean automatico = Boolean.parseBoolean(request.getParameter("automatico"));
					int consumo = Integer.parseInt(request.getParameter("consumo"));
					String cambios="";
					cambios="matricula = \""+matriculanueva+"\",";
					cambios+=" marca = \""+marca+"\",";
					cambios+=" motor = "+motor+",";
					cambios+=" automatico = "+automatico+",";
					cambios+=" n_ruedas = \""+n_ruedas+"\",";
					cambios+=" consumo = \""+consumo+"\"";
					
					try {
						// Register JDBC driver
						Class.forName("com.mysql.jdbc.Driver");
						// Open a connection
						con = DriverManager.getConnection(URL_BD,USUARIO,CONTRA);			        
						sentencia = con.createStatement();
						
						//String sql;
						//INSERT INTO coches VALUES ("0000AAA", "prueba1", true, true, 4, 100);
						//DELETE FROM coches where matricula="0000AAA";
						System.out.println("UPDATE coches SET "+cambios+" WHERE matricula=\""+matriculavieja+"\"");
						sql="UPDATE coches SET "+cambios+" WHERE matricula=\""+matriculavieja+"\"";
						int modificar = sentencia.executeUpdate(sql);
						System.out.println("Valor crear: " + modificar);
						if (modificar==1) {
							//response(response, "Se ha modificado el vehículo " + matriculavieja + ".<br>" + cambios);
						} else {
							//response(response, "¡Error! No se ha modificado el vehículo, compruebe las matrícula1: "+matriculavieja+" matricula2 "+matriculanueva );
						}
						con.close();
					} catch(ArrayIndexOutOfBoundsException e) {
						//response(response, "no se encontro el vehiculo");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				//response(response, "No se encontró el vehículo");
			}
			con.close();
		
		} catch(ArrayIndexOutOfBoundsException e) {
			//response(response, "no se encontro el vehiculo");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}

package cursojavaeitclase8;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CursoJavaEITClase8 {

    public static void main(String[] args) throws Exception {
        List<Empleado> empleados = new ArrayList<Empleado>();
        int id = 10001;
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "");
            String q = "select * from employees where emp_no = ?";
            PreparedStatement ps = c.prepareStatement(q);
            getbyId(ps, id);
            PreparedStatement psi = c.prepareStatement("Insert into employees(emp_no, birth_date, first_name, last_name, hire_date) values (?, ?, ?, ?, ?)");
            PreparedStatement psa = c.prepareStatement("UPDATE employees SET first_name = ?, last_name = ? where emp_no = ?"); 
            PreparedStatement psd = c.prepareStatement("DELETE from employees where emp_no = ?");
            //insertar(psi, "-20", "Ramiro", "Margeli");
            actualizar(psa, "GODIO", "ARRIBAS", "-20");
            borrar(psd, "-20");

            /*                 
            Statement stm = c.createStatement(); 
            ResultSet rs = stm.executeQuery(q);            
            while(rs.next()){                                   //next me devuelve un boolean
                int id2 = rs.getInt("emp_no");
                String nombre = rs.getString("first_name");
                String apellido = rs.getString("last_name");
                empleados.add(new Empleado(id2, nombre, apellido));                
                //System.out.println(id+" "+nombre+" "+apellido);   
            }            
            for(Empleado e : empleados){
                System.out.println(e.toString());
                        
            }
             */
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getbyId(PreparedStatement ps, int id) throws Exception {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {                                   //next me devuelve un boolean
            System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        }

    }

    private static void insertar(PreparedStatement psi, String id, String nombre, String apellido) throws Exception {
        
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse("09-05-1992");
        java.sql.Date dateSQL = new java.sql.Date(date.getTime());
        
        
        
        Date d = new java.sql.Date(0);
        psi.setString(1, id);
        psi.setDate(2, dateSQL);
        psi.setString(3, nombre);
        psi.setString(4, apellido);
        psi.setDate(5, dateSQL);              
        psi.execute();

    }

    private static void actualizar(PreparedStatement psa, String nombre, String apellido, String id) throws Exception {
        psa.setString(1, nombre);
        psa.setString(2, apellido);
        psa.setString(3, id);
        psa.execute();        
    }

    private static void borrar(PreparedStatement psd, String id) throws Exception {
        psd.setString(1, id);
        psd.execute();
    }
}

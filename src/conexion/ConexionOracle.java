package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
	
    //private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    //private static final String USUARIO = "USUARIO";
    //private static final String SENHA = "123";
	
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USUARIO = "labdatabase";
    private static final String SENHA = " labDatabase2022";
    
    public static Connection conectar() {
        Connection conexao = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar com o banco de dados Oracle.");
        }
        return conexao;
    }

    public static void fecharConexao(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
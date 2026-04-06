package jdbc;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnection {
        
    private static Connection conn;
    private static Dotenv dotenv;

    private static String URL;
    private static String USER;
    private static String SENHA;
    
    static {
        try {
            System.out.println("Diretório atual: " + System.getProperty("user.dir"));
            
            dotenv = Dotenv.configure()
                    .directory(System.getProperty("user.dir"))
                    .ignoreIfMissing()
                    .load();
            
            URL = dotenv.get("DB_URL", "jdbc:mysql://localhost:3306/controle_de_estoque");
            USER = dotenv.get("DB_USER", "root");
            SENHA = dotenv.get("DB_PASS", "senh123");
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar .env: " + e.getMessage());
            
            URL = "jdbc:mysql://localhost:3306/controle_de_estoque";
            USER = "root";
            SENHA = "senh123";
        }
    }
    
    // faz a conexão com o banco de dados 
    public static Connection getConnection() throws SQLException { 
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, SENHA);
        }
        return conn;
    }
    
    // fecha a conexão com o banco de dados
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}

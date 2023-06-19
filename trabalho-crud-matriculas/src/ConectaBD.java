
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBD {
    private static final String URL = "jdbc:mysql://localhost:3306/crudalunos";
    private static final String USARIO = "root";
    private static final String SENHA = "Spac0440!";

    private static Connection conexao;

    private ConectaBD(){}

    public static Connection getConexao() throws SQLException {
        if (conexao == null){
            conexao = DriverManager.getConnection(URL, USARIO, SENHA);
        }
        return conexao;
    } // fim metodo getConexao()
} // fim classe ConectarDB

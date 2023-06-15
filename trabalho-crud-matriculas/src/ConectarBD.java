
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {
    private static final String URL = "jdbc:mysql://localhost:3306/Aluno";
    private static final String USARIO = "root";
    private static final String SENHA = "*****";

    private static Connection conexao;

    private ConectarBD(){}

    public static Connection getConexao() throws SQLException {
        if (conexao == null){
            conexao = DriverManager.getConnection(URL, USARIO, SENHA);
        }
        return conexao;
    } // fim metodo getConexao()
} // fim classe ConectarDB

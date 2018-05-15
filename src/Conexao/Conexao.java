package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	private static Connection conexao;
	
	public static Connection getConexao() throws SQLException {

		String url = "jdbc:mysql://localhost:3306/trabalho_java";
		String usuario = "root";
		String senha = "";
		
		if (conexao == null) {
			conexao = DriverManager.getConnection(url, usuario, senha);
		}
		
		return conexao;
    }

}
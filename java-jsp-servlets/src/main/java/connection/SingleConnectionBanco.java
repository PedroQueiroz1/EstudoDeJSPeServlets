package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String url = "jdbc:postgresql://localhost:5433/jsp-servlets?autoReconnect=true"; 
	private static String usuario = "postgres";
	// Deixar visível a senha mesmo, poderia criar uma classe com uma constante para adicionar no .gitignore e deixar com maior segurança a aplicação
	// Mas como o objetivo desse projeto é só acadêmico, de estudos mesmo, prefiro deixar assim para futuras modificações.
	private static String senha = "admin";  
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() { // quadno estiver uma instância vai conectar
		conectar();
	}
	
	private static void conectar() {
		
		try {
			
			if (connection == null) {
				Class.forName("org.postgresql.Driver"); // Carrega o driver de conexão do banco
				connection = DriverManager.getConnection(url, usuario, senha);
				connection.setAutoCommit(false); // Não efetuar alterações no banco sem nosso comando
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

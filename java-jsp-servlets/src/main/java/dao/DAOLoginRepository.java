package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection conn;

	public DAOLoginRepository() {
		conn = SingleConnectionBanco.getConnection();
	}

	public boolean validarAutenticacao(ModelLogin modelLogin) throws SQLException {

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?) AND upper(senha) = upper(?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, modelLogin.getLogin());
		pstm.setString(2, modelLogin.getSenha());

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			return true; // Autenticado
		}
		return false; // Não autenticado
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection conn;

	public DAOUsuarioRepository() {
		conn = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin mL) throws Exception {

			String sql = "INSERT INTO public.model_login(login, senha, nome, email) VALUES (?, ?, ?, ?)";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, mL.getLogin());
			pstm.setString(2, mL.getSenha());
			pstm.setString(3, mL.getNome());
			pstm.setString(4, mL.getEmail());
		
			pstm.execute();
			conn.commit();

			return this.consultarUsuario(mL.getLogin());
	}
	
	public ModelLogin consultarUsuario(String login) throws SQLException {
		
		ModelLogin mL = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE upper(login) = upper(?)";		
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, login);
		
		ResultSet rs = pstm.executeQuery();
		
		
		if(rs.next()) {
			
			mL.setId(rs.getLong("id"));
			mL.setEmail(rs.getString("email"));
			mL.setLogin(rs.getString("login"));
			mL.setLogin(rs.getString("senha"));
			mL.setNome(rs.getString("nome"));
			
		}
		
		return mL;
	}
}

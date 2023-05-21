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

		PreparedStatement pstm;

		if (mL.isNovo()) {
			String sql = "INSERT INTO public.model_login(login, senha, nome, email) VALUES (?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mL.getLogin());
			pstm.setString(2, mL.getSenha());
			pstm.setString(3, mL.getNome());
			pstm.setString(4, mL.getEmail());

			pstm.execute();
			conn.commit();

		} else {
			String sql = "UPDATE model_login SET login = ?, senha = ?, nome = ?, email = ? WHERE id = ?";

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mL.getLogin());
			pstm.setString(2, mL.getSenha());
			pstm.setString(3, mL.getNome());
			pstm.setString(4, mL.getEmail());
			pstm.setLong(5, mL.getId());

			pstm.executeUpdate();
			conn.commit();

		}

		return this.consultarUsuario(mL.getLogin());
	}

	public ModelLogin consultarUsuario(String login) throws SQLException {

		ModelLogin mL = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER(?)";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, login);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {

			mL.setId(rs.getLong("id"));
			mL.setEmail(rs.getString("email"));
			mL.setLogin(rs.getString("login"));
			mL.setLogin(rs.getString("senha"));
			mL.setNome(rs.getString("nome"));

		}
		return mL;
	}

	public boolean validarLogin(String login) throws Exception {
		String sql = "SELECT COUNT(1) > 0 AS existe FROM model_login WHERE UPPER(login) = UPPER(?)";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, login);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			return rs.getBoolean("existe");
		}

		return false;
	}
	
	public void deletarUsuario(String idUsuario) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setLong(1, Long.parseLong(idUsuario));
		pstm.executeUpdate();
		
		conn.commit();
	}

}

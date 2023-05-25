package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection conn;

	public DAOUsuarioRepository() {
		conn = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin mL, Long usuarioLogado) throws Exception {

		PreparedStatement pstm;

		if (mL.isNovo()) {
			String sql = "INSERT INTO public.model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mL.getLogin());
			pstm.setString(2, mL.getSenha());
			pstm.setString(3, mL.getNome());
			pstm.setString(4, mL.getEmail());
			pstm.setLong(5, usuarioLogado);
			pstm.setString(6, mL.getPerfil());
			pstm.setString(7, mL.getSexo());
			pstm.setString(8, mL.getCep());
			pstm.setString(9, mL.getLogradouro());
			pstm.setString(10, mL.getBairro());
			pstm.setString(11, mL.getLocalidade());
			pstm.setString(12, mL.getUf());
			pstm.setString(13, mL.getNumero());

			pstm.execute();
			conn.commit();

			if (mL.getFotosUsuario() != null && !mL.getFotosUsuario().isEmpty()) {
				sql = "UPDATE model_login SET fotousuario = ?, extensaofotousuario = ? WHERE login = ?";

				pstm = conn.prepareStatement(sql);
				pstm.setString(1, mL.getFotosUsuario());
				pstm.setString(2, mL.getExtensaoFotoUsuario());
				pstm.setString(3, mL.getLogin());

				pstm.executeUpdate();
				conn.commit();
			}
		} else {
			String sql = "UPDATE model_login SET login = ?, senha = ?, nome = ?, email = ?, perfil = ?, sexo = ?, cep = ?, logradouro = ?, bairro = ?, localidade = ?, uf = ?, numero = ? WHERE id = ?";

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mL.getLogin());
			pstm.setString(2, mL.getSenha());
			pstm.setString(3, mL.getNome());
			pstm.setString(4, mL.getEmail());
			pstm.setString(5, mL.getPerfil());
			pstm.setString(6, mL.getSexo());
			pstm.setString(7, mL.getCep());
			pstm.setString(8, mL.getLogradouro());
			pstm.setString(9, mL.getBairro());
			pstm.setString(10, mL.getLocalidade());
			pstm.setString(11, mL.getUf());
			pstm.setString(12, mL.getNumero());
			pstm.setLong(13, mL.getId());

			pstm.executeUpdate();
			conn.commit();

			if (mL.getFotosUsuario() != null && !mL.getFotosUsuario().isEmpty()) {
				sql = "UPDATE model_login SET fotousuario = ?, extensaofotousuario = ? WHERE id = ?";

				pstm = conn.prepareStatement(sql);
				pstm.setString(1, mL.getFotosUsuario());
				pstm.setString(2, mL.getExtensaoFotoUsuario());
				pstm.setLong(3, mL.getId());

				pstm.execute();
				conn.commit();
			}

		}

		return this.consultarUsuario(mL.getLogin(), usuarioLogado);
	}

	public List<ModelLogin> consultarListaUsuario(Long usuarioLogado) throws SQLException {

		List<ModelLogin> mLs = new ArrayList<>();

		String sql = "SELECT * FROM model_login WHERE useradmin IS FALSE AND usuario_id = " + usuarioLogado;
		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			ModelLogin mL = new ModelLogin();

			mL.setEmail(rs.getString("email"));
			mL.setId(rs.getLong("id"));
			mL.setLogin(rs.getString("login"));
			mL.setNome(rs.getString("nome"));
			mL.setPerfil(rs.getString("perfil"));
			mL.setSexo(rs.getString("sexo"));
			// mL.setLogin(rs.getString("senha"));

			mLs.add(mL);
		}

		return mLs;
	}

	public List<ModelLogin> consultarListaUsuario(String nome, Long usuarioLogado) throws SQLException {

		List<ModelLogin> mLs = new ArrayList<>();

		String sql = "SELECT * FROM model_login WHERE UPPER(nome) LIKE UPPER(?) AND useradmin IS FALSE AND usuario_id = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, "%" + nome + "%");
		pstm.setLong(2, usuarioLogado);

		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			ModelLogin mL = new ModelLogin();

			mL.setEmail(rs.getString("email"));
			mL.setId(rs.getLong("id"));
			mL.setLogin(rs.getString("login"));
			mL.setNome(rs.getString("nome"));
			mL.setPerfil(rs.getString("perfil"));
			mL.setSexo(rs.getString("sexo"));
			// mL.setLogin(rs.getString("senha"));

			mLs.add(mL);
		}

		return mLs;
	}

	public ModelLogin consultarUsuario(String login, Long usuarioLogado) throws SQLException {

		ModelLogin mL = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER(?) AND useradmin IS FALSE AND usuario_id = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, login);
		pstm.setLong(2, usuarioLogado);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {

			mL.setId(rs.getLong("id"));
			mL.setEmail(rs.getString("email"));
			mL.setLogin(rs.getString("login"));
			mL.setSenha(rs.getString("senha"));
			mL.setNome(rs.getString("nome"));
			mL.setPerfil(rs.getString("perfil"));
			mL.setSexo(rs.getString("sexo"));
			mL.setFotosUsuario(rs.getString("fotousuario"));
			mL.setCep(rs.getString("cep"));
			mL.setLogradouro(rs.getString("logradouro"));
			mL.setBairro(rs.getString("bairro"));
			mL.setLocalidade(rs.getString("localidade"));
			mL.setUf(rs.getString("uf"));
			mL.setNumero(rs.getString("numero"));
		}
		return mL;
	}

	public ModelLogin consultarUsuario(String login) throws SQLException {

		ModelLogin mL = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER(?) AND useradmin IS FALSE";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, login);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {

			mL.setId(rs.getLong("id"));
			mL.setEmail(rs.getString("email"));
			mL.setLogin(rs.getString("login"));
			mL.setSenha(rs.getString("senha"));
			mL.setNome(rs.getString("nome"));
			mL.setUseradmin(rs.getBoolean("useradmin"));
			mL.setPerfil(rs.getString("perfil"));
			mL.setSexo(rs.getString("sexo"));
			mL.setFotosUsuario(rs.getString("fotousuario"));
			mL.setCep(rs.getString("cep"));
			mL.setLogradouro(rs.getString("logradouro"));
			mL.setBairro(rs.getString("bairro"));
			mL.setLocalidade(rs.getString("localidade"));
			mL.setUf(rs.getString("uf"));
			mL.setNumero(rs.getString("numero"));

		}
		return mL;
	}

	public ModelLogin consultarUsuarioLogado(String login) throws SQLException {

		ModelLogin mL = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE UPPER(login) = UPPER(?)";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, login);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {

			mL.setId(rs.getLong("id"));
			mL.setEmail(rs.getString("email"));
			mL.setLogin(rs.getString("login"));
			mL.setSenha(rs.getString("senha"));
			mL.setNome(rs.getString("nome"));
			mL.setUseradmin(rs.getBoolean("useradmin"));
			mL.setPerfil(rs.getString("perfil"));
			mL.setSexo(rs.getString("sexo"));
			mL.setFotosUsuario(rs.getString("fotousuario"));
			mL.setCep(rs.getString("cep"));
			mL.setLogradouro(rs.getString("logradouro"));
			mL.setBairro(rs.getString("bairro"));
			mL.setLocalidade(rs.getString("localidade"));
			mL.setUf(rs.getString("uf"));
			mL.setNumero(rs.getString("numero"));

		}
		return mL;
	}

	public ModelLogin consultarUsuarioID(String id, Long usuarioLogado) throws Exception {

		ModelLogin mL = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE id = ? AND useradmin IS FALSE AND usuario_id = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setLong(1, Long.parseLong(id));
		pstm.setLong(2, usuarioLogado);

		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {

			mL.setId(rs.getLong("id"));
			mL.setEmail(rs.getString("email"));
			mL.setLogin(rs.getString("login"));
			mL.setSenha(rs.getString("senha"));
			mL.setNome(rs.getString("nome"));
			mL.setPerfil(rs.getString("perfil"));
			mL.setSexo(rs.getString("sexo"));
			mL.setFotosUsuario(rs.getString("fotousuario"));
			mL.setExtensaoFotoUsuario(rs.getString("extensaofotousuario"));
			mL.setCep(rs.getString("cep"));
			mL.setLogradouro(rs.getString("logradouro"));
			mL.setBairro(rs.getString("bairro"));
			mL.setLocalidade(rs.getString("localidade"));
			mL.setUf(rs.getString("uf"));
			mL.setNumero(rs.getString("numero"));
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
		String sql = "DELETE FROM model_login WHERE id = ? WHERE useradmin IS FALSE";
		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setLong(1, Long.parseLong(idUsuario));
		pstm.executeUpdate();

		conn.commit();
	}

}

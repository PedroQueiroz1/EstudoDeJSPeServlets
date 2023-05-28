package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

/*
 * mT = ModelTelefone
 */
public class DAOTelefoneRepository {

	private Connection conn;
	
	private DAOUsuarioRepository daoUsuarioRep = new DAOUsuarioRepository();
	
	public DAOTelefoneRepository() {
		conn = SingleConnectionBanco.getConnection(); 
	}
	
	public List<ModelTelefone> listarTelefone (Long idUsuarioPai) throws Exception {
		
		List<ModelTelefone> listaTelefone = new ArrayList<>();
		
		String sql = "SELECT * FROM telefone WHERE usuario_pai_id = " + idUsuarioPai;
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();

		
		while(rs.next()) {
		ModelTelefone mT = new ModelTelefone();
		
		mT.setId(rs.getLong("id"));
		mT.setNumero(rs.getString("numero"));
		mT.setUsuario_cad_id(daoUsuarioRep.consultarUsuarioID(rs.getLong("usuario_cad_id")));
		mT.setUsuario_pai_id(daoUsuarioRep.consultarUsuarioID(rs.getLong("usuario_pai_id")));
		
		listaTelefone.add(mT);
	
		}
		
		pstm.execute();
		conn.commit();
		
		return listaTelefone;
	}
	
	public void gravarTelefone (ModelTelefone mT) throws Exception {
		
		String sql = "INSERT INTO public.telefone(numero, usuario_pai_id, usuario_cad_id) VALUES (?, ?, ?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, mT.getNumero());
		pstm.setLong(2, mT.getUsuario_pai_id().getId());
		pstm.setLong(3, mT.getUsuario_cad_id().getId());
		
		pstm.execute();
		conn.commit();
		
	}
	
	public void deletarTelefone (Long id) throws Exception {
		String sql = "DELETE FROM telefone WHERE id = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setLong(1, id);
		
		pstm.executeUpdate();
		conn.commit();
		
	}
}

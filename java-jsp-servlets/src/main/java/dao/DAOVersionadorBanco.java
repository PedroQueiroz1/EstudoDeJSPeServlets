package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;

public class DAOVersionadorBanco implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection conn;

	public DAOVersionadorBanco() {
		conn = SingleConnectionBanco.getConnection();
	}

	public void gravaArquivoSqlRodado(String nome_file) throws Exception {

		String sql = "INSERT INTO versionadorbanco(arquivo_sql) VALUES (?);";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, nome_file);
		pstm.execute();

	}

	public boolean arquivoSqlRodado(String nome_do_arquivo) throws Exception {

		String sql = "SELECT COUNT(1) > 0 AS rodado FROM versionadorbanco WHERE arquivo_sql = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, nome_do_arquivo);

		ResultSet rs = pstm.executeQuery();

		rs.next();

		return rs.getBoolean("rodado");
	}

}

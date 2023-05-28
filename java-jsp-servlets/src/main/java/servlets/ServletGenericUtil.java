package servlets;

import java.io.Serializable;
import java.sql.SQLException;

import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;

public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private DAOUsuarioRepository daoUsuarioRep = new DAOUsuarioRepository();
	
	
	public Long usuarioLogado(HttpServletRequest request) throws SQLException {

		HttpSession session = request.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		return daoUsuarioRep.consultarUsuarioLogado(usuarioLogado).getId();
	}
	
	public ModelLogin usuarioLogadoObjeto(HttpServletRequest request) throws SQLException {

		HttpSession session = request.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		return daoUsuarioRep.consultarUsuarioLogado(usuarioLogado);
	}
}

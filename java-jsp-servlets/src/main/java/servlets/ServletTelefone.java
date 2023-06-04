package servlets;

import java.io.IOException;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

public class ServletTelefone extends ServletGenericUtil {

	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRep = new DAOUsuarioRepository();
	private DAOTelefoneRepository daoTelefoneRep = new DAOTelefoneRepository();

	public ServletTelefone() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equals("excluir")) {

				String idFone = request.getParameter("id");

				daoTelefoneRep.deletarTelefone(Long.parseLong(idFone));

				String usuarioPai = request.getParameter("usuarioPai");

				ModelLogin mL = daoUsuarioRep.consultarUsuarioID(Long.parseLong(usuarioPai));

				List<ModelTelefone> mTList = daoTelefoneRep.listarTelefone(mL.getId());
				request.setAttribute("mTList", mTList);

				request.setAttribute("msg", "Telefone Excluido");
				request.setAttribute("modelLogin", mL);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

				return;
			}

			String idUsuario = request.getParameter("idusuario");

			if (idUsuario != null && !idUsuario.isEmpty()) {
				ModelLogin mL = daoUsuarioRep.consultarUsuarioID(Long.parseLong(idUsuario));

				List<ModelTelefone> mTList = daoTelefoneRep.listarTelefone(mL.getId());
				request.setAttribute("mTList", mTList);

				request.setAttribute("modelLogin", mL);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {
				List<ModelLogin> mLs = daoUsuarioRep.consultarListaUsuario(super.usuarioLogado(request));

				// Retorno
				request.setAttribute("modelLogins", mLs);
				request.setAttribute("totalPagina", daoUsuarioRep.totalPagina(this.usuarioLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String usuario_pai_id = request.getParameter("id");
			String numero = request.getParameter("numero");

			if (!daoTelefoneRep.existeTelefone(numero, Long.valueOf(usuario_pai_id))) {

				ModelTelefone mT = new ModelTelefone();

				mT.setNumero(numero);
				mT.setUsuario_pai_id(daoUsuarioRep.consultarUsuarioID(Long.parseLong(usuario_pai_id)));
				mT.setUsuario_cad_id(super.usuarioLogadoObjeto(request));

				daoTelefoneRep.gravarTelefone(mT);

				request.setAttribute("msg", "Salvo com sucesso");

			} else {
				request.setAttribute("msg", "Telefone já existe");
			}

			List<ModelTelefone> mTList = daoTelefoneRep.listarTelefone(Long.parseLong(usuario_pai_id));

			ModelLogin mL = daoUsuarioRep.consultarUsuarioID(Long.parseLong(usuario_pai_id));
			// Retorno
			request.setAttribute("modelLogin", mL);
			request.setAttribute("mTList", mTList);
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

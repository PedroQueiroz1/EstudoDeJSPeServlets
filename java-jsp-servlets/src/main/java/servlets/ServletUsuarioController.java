package servlets;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

/*
 * mL = modelLogin
 */
@MultipartConfig
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRep = new DAOUsuarioRepository();

	public ServletUsuarioController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idUser = request.getParameter("id");

				daoUsuarioRep.deletarUsuario(idUser);

				List<ModelLogin> mLs = daoUsuarioRep.consultarListaUsuario(super.usuarioLogado(request));
				request.setAttribute("modelLogins", mLs);

				request.setAttribute("msg", "Excluído com sucesso");
				request.setAttribute("totalPagina", daoUsuarioRep.totalPagina(this.usuarioLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
				String idUser = request.getParameter("id");

				daoUsuarioRep.deletarUsuario(idUser);

				response.getWriter().write("Excluído com sucesso!");
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuarioAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");

				List<ModelLogin> dadosJsonUsuario = daoUsuarioRep.consultarListaUsuario(nomeBusca,
						super.usuarioLogado(request));

				ObjectMapper mapper = new ObjectMapper();
				
				String json = mapper.writeValueAsString(dadosJsonUsuario);

				response.addHeader("totalPagina", ""+ daoUsuarioRep.consultarListaUsuarioTotalPaginaPaginacao(nomeBusca, super.usuarioLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUsuarioAjaxPagina")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");
				
				List<ModelLogin> dadosJsonUsuario = daoUsuarioRep.consultarUsuarioListOffSet(nomeBusca, super.usuarioLogado(request), Integer.parseInt(pagina));

				ObjectMapper mapper = new ObjectMapper();
				
				String json = mapper.writeValueAsString(dadosJsonUsuario);

				response.addHeader("totalPagina", ""+ daoUsuarioRep.consultarListaUsuarioTotalPaginaPaginacao(nomeBusca, super.usuarioLogado(request)));
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				String id = request.getParameter("id");

				ModelLogin mL = daoUsuarioRep.consultarUsuarioID(id, super.usuarioLogado(request));

				List<ModelLogin> mLs = daoUsuarioRep.consultarListaUsuario(super.usuarioLogado(request));
				request.setAttribute("modelLogins", mLs);

				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modelLogin", mL);
				request.setAttribute("totalPagina", daoUsuarioRep.totalPagina(this.usuarioLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsuario")) {

				List<ModelLogin> mLs = daoUsuarioRep.consultarListaUsuario(super.usuarioLogado(request));

				request.setAttribute("msg", "Usuários carregados");
				request.setAttribute("modelLogins", mLs);
				request.setAttribute("totalPagina", daoUsuarioRep.totalPagina(this.usuarioLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				
				String idUser = request.getParameter("id");
				
				ModelLogin mL = daoUsuarioRep.consultarUsuarioID(idUser, super.usuarioLogado(request));
				
				if(mL.getFotosUsuario() != null && !mL.getFotosUsuario().isEmpty()) {
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + mL.getExtensaoFotoUsuario());
					response.getOutputStream().write(new Base64().decodeBase64(mL.getFotosUsuario().split("\\,")[1]));
				}
				
			}  else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				
				List<ModelLogin> modelLogins = daoUsuarioRep.consultarListaUsuarioPaginada(this.usuarioLogado(request), offset);
				
				 request.setAttribute("modelLogins", modelLogins);
			     request.setAttribute("totalPagina", daoUsuarioRep.totalPagina(this.usuarioLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else {
				List<ModelLogin> mLs = daoUsuarioRep.consultarListaUsuario(super.usuarioLogado(request));
				request.setAttribute("modelLogins", mLs);
				request.setAttribute("totalPagina", daoUsuarioRep.totalPagina(this.usuarioLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String msg = "Operação realizada com sucesso";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");

			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);

			if (ServletFileUpload.isMultipartContent(request)) {
				Part part = request.getPart("fileFoto"); /* Pega foto da tela */

				if (part.getSize() > 0) {
					byte[] foto = IOUtils.toByteArray(part.getInputStream()); // Converte imagem para byte
					String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64,"
							+ new Base64().encodeBase64String(foto);

					modelLogin.setFotosUsuario(imagemBase64);
					modelLogin.setExtensaoFotoUsuario(part.getContentType().split("\\/")[1]);
				}
			}

			if (daoUsuarioRep.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe usuário com o mesmo login, informe outro login";
			} else {
				if (modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
				}
				msg = "Atualizado com sucesso!";
			}
			modelLogin = daoUsuarioRep.gravarUsuario(modelLogin, super.usuarioLogado(request));

			List<ModelLogin> mLs = daoUsuarioRep.consultarListaUsuario(super.usuarioLogado(request));
			request.setAttribute("modelLogins", mLs);
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("totalPagina", daoUsuarioRep.totalPagina(this.usuarioLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}


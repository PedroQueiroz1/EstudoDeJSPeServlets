package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import connection.SingleConnectionBanco;
import dao.DAOVersionadorBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/*
 * @WebFilter -> Intercepta todas as requisições que vierem do projeto/mapeamento
 */
@WebFilter(urlPatterns = { "/principal/*" })
public class FilterAutenticacao implements Filter {

	private static Connection conn;

	public FilterAutenticacao() {
	}

	/*
	 * Encerra os processos quando o servidor é parado exemplo: mata processo de
	 * conexão com banco
	 */
	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Intercepta as requisições e as respostas no sistema Tudo o que fizer no
	 * sistema vai fazer por aqui
	 * 
	 * exemplos: validação de auth, commit e rollback de transaçõs do banco, validar
	 * e fazer redirecionamento de páginas...
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath();

			// Validar se está logado, se não redireciona para a tela de login...
			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {

				RequestDispatcher redireciona = req.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor, realize o login!");
				redireciona.forward(request, response);
				return; // Para a execução e redireciona para o login

			} else {
				chain.doFilter(request, response);

			}

			conn.commit(); // Deu tudo certo, então commita as alterações no banco de dados
		} catch (Exception e) {
			e.printStackTrace();

			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	 * Inicia os processos/recursos quando o servidor sobe o projeto exemplo: inicia
	 * conexão com o banco
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		conn = SingleConnectionBanco.getConnection();

		DAOVersionadorBanco daoVersBanco = new DAOVersionadorBanco();

		String caminhoPastaSql = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;

		File[] filesSql = new File(caminhoPastaSql).listFiles();

		try {

			for (File file : filesSql) {

				boolean arquivoJaRodado = daoVersBanco.arquivoSqlRodado(file.getName());

				if (!arquivoJaRodado) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while(lerArquivo.hasNext()) {
						
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}
					
					conn.prepareStatement(sql.toString()).execute();
					daoVersBanco.gravaArquivoSqlRodado(file.getName());
					
					conn.commit();
					lerArquivo.close();
				}
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}

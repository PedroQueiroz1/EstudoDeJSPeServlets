<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
	<jsp:include page="theme-loader.jsp"></jsp:include>

	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="mainmenu.jsp"></jsp:include>

					<div class="pcoded-content">

						<jsp:include page="page-header.jsp"></jsp:include>

						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-11">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cadastro de Usuário</h4>

														<!-- enctype é necessário para o add de imagem pelo formulário 
															o enctype foi adicionado no início do formulário -->
														<!-- multipart precisa ser declarado como anotação de classe no Servlet -->
														<!-- '< %= % >' se chama tag scriptlet, utilizado para inserir código Java
															direto na página JSP! -->
														<!--  'dólar {}' se chama EL, ou, Expression Language
															parte do JSP! é uma maneira simplificada de acessar e manipular
															objetos Java diretamente nas páginas JSP.
															No caso do 'dólar { modelLogin.id }' existe um objeto modelLogin com a
															proriedade id definida. Esse EL avaliará e exibirá o valor do id na
															página web.-->
														<!-- 'accept' define o tipo de arquivo que vai aceitar -->
														<!-- foto em base 64 converte a imagem em uma string grande
															servindo para mandar por webservice ou por json -->
														<!-- Foi utilizado a API viaCep para o endereço do usuario -->
														<form class="form-material" enctype="multipart/form-data"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="form-usuario">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>

															<div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																	<c:if
																		test="${modelLogin.fotosUsuario != '' && modelLogin.fotosUsuario != null}">
																		<a
																			href="<%= request.getContextPath() %>/ServletUsuarioController?acao=downloadFoto&id=${modelLogin.id}">
																			<img alt="Imagem Usuario" id="fotoembase64"
																			src="${modelLogin.fotosUsuario}" width="70px">
																		</a>
																	</c:if>

																	<c:if
																		test="${modelLogin.fotosUsuario == '' || modelLogin.fotosUsuario == null}">
																		<img alt="Imagem Usuario" id="fotoembase64"
																			src="assets/images/avatar-blank.jpg" width="70px">
																	</c:if>
																</div>
																<input type="file" id="fileFoto" name="fileFoto"
																	accept="image/*"
																	onchange="visualizarImg('fotoembase64', 'fileFoto');"
																	class="form-control-file"
																	style="margin-top: 15px; margin-left: 5px;">
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.email}">
																<span class="form-bar"></span> <label
																	class="float-label">E-mail:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<select class="form-control"
																	aria-label="Default select example" name="perfil">
																	<option disabled="disabled">[Selecione o
																		Perfil]</option>

																	<option value="ADMIN"
																		<%ModelLogin mL = (ModelLogin) request.getAttribute("modelLogin");

if (mL != null && mL.getPerfil().equals("ADMIN")) {
	out.print(" ");
	out.print("selected=\"selected\"");
	out.print(" ");
}%>>Admin</option>

																	<option value="SECRETARIO"
																		<%mL = (ModelLogin) request.getAttribute("modelLogin");

if (mL != null && mL.getPerfil().equals("SECRETARIO")) {
	out.print(" ");
	out.print("selected=\"selected\"");
	out.print(" ");
}%>>Secretário</option>

																	<option value="AUXILIAR"
																		<%mL = (ModelLogin) request.getAttribute("modelLogin");

if (mL != null && mL.getPerfil().equals("AUXILIAR")) {
	out.print(" ");
	out.print("selected=\"selected\"");
	out.print(" ");
}%>>Auxiliar</option>

																</select> <span class="form-bar"></span> <label
																	class="float-label">Perfil</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input onblur="pesquisaCep();" type="text" name="cep"
																	id="cep" class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.cep}"> <span
																	class="form-bar"></span> <label class="float-label">Cep</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="logradouro" id="logradouro"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.logradouro}">
																<span class="form-bar"></span> <label
																	class="float-label">Logradouro</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="bairro" id="bairro"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.bairro}">
																<span class="form-bar"></span> <label
																	class="float-label">Bairro</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="localidade" id="localidade"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.localidade}">
																<span class="form-bar"></span> <label
																	class="float-label">Localidade</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="uf" id="uf"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.uf}"> <span
																	class="form-bar"></span> <label class="float-label">UF</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.numero}">
																<span class="form-bar"></span> <label
																	class="float-label">Numero</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.login}">
																<span class="form-bar"></span> <label
																	class="float-label">Login</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.senha}">
																<span class="form-bar"></span> <label
																	class="float-label">Senha</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="radio" name="sexo" checked="checked"
																	value="MASCULINO"
																	<%mL = (ModelLogin) request.getAttribute("modelLogin");

if (mL != null && mL.getSexo().equals("MASCULINO")) {

	out.print(" ");
	out.print("checked=\"checked\"");
	out.print(" ");

}%>>Masculino</>
																<input type="radio" name="sexo" value="FEMININO"<%mL = (ModelLogin) request.getAttribute("modelLogin");

if (mL != null && mL.getSexo().equals("FEMININO")) {

	out.print(" ");
	out.print("checked=\"checked\"");
	out.print(" ");

}%>
																%>Feminino</>
															</div>
															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm()">Limpar Formulário</button>
															<button type="submit"
																class="btn btn-success waves-effect waves-light">Salvar</button>
															<button type="button"
																class="btn btn-info waves-effect waves-light"
																onclick="criarDeleteComAjax()">Excluir</button>
															<button type="button" class="btn btn-secondary"
																data-toggle="modal" data-target="#exampleUsuario">Pesquisar</button>
														</form>
													</div>
												</div>
											</div>
										</div>
										<span id="msg">${msg}</span>

										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaResultadosView">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelLogins}" var="mLs">
														<tr>
															<td><c:out value="${mLs.id}"></c:out></td>
															<td><c:out value="${mLs.nome}"></c:out></td>
															<td><a class="btn btn-success"
																href="<%=request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${mLs.id}">Ver</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

										<nav aria-label="Page navigation example">
											<ul class="pagination">
												<%
												int totalPagina = (int) request.getAttribute("totalPagina");

												for (int p = 0; p < totalPagina; p++) {
													String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (p * 5);
													out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
												}
												%>

											</ul>
										</nav>

									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="javascriptfile.jsp"></jsp:include>
</body>

<!-- Modal -->
<div class="modal fade" id="exampleUsuario" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Pesquisa de
					usuário</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">

				<div class="input-group mb-3">
					<input type="text" class="form-control" placeholder="Nome"
						aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
					<div class="input-group-append">
						<button class="btn btn-success" type="button"
							onclick="buscarUsuario();">Buscar</button>
					</div>
				</div>
				<div style="height: 300px; overflow: scroll;">
					<table class="table" id="tabelaResultados">
						<thead>
							<tr>
								<th scope="col">ID</th>
								<th scope="col">Nome</th>
								<th scope="col">Ver</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<nav aria-label="Page navigation example">
					<ul class="pagination" id="ulPaginacaoUsuarioAjax">
					</ul>
				</nav>
				
<span id="totalResultados"></span>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript">

	function pesquisaCep() {
		var cep = $("#cep").val();

		$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
				function(dados) {
					if (!("erro" in dados)) {
						$("#logradouro").val(dados.logradouro);
						$("#bairro").val(dados.bairro);
						$("#localidade").val(dados.localidade);
						$("#uf").val(dados.uf);
					}
				});
	}

	function visualizarImg(fotoembase64, fileFoto) {

		var preview = document.getElementById(fotoembase64); //campo IMG HTML
		var fileUsuario = document.getElementById(fileFoto).files[0];
		var reader = new FileReader();

		reader.onloadend = function() {
			preview.src = reader.result; /* Carrega a foto na tela */
		};

		if (fileUsuario) {
			reader.readAsDataURL(fileUsuario); /* Preview da image */
		} else {
			preview.src = '';
		}
	}

	function verEditar(id) {

		var urlAction = document.getElementById('form-usuario').action; // = http://localhost:8080/java-jsp-servlets/ServletUsuarioController

		window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
	}

	
	function buscarUsuarioPagAjax(url) {
		
		var urlAction = document.getElementById('form-usuario').action;
		var nomeBusca = document.getElementById('nomeBusca').value;
		
		 $.ajax({	     
		     method: "get",
		     url : urlAction,
		     data : url,
		     success: function (response, textStatus, xhr) {
			 
			 var json = JSON.parse(response);
			 
			 
			 $('#tabelaResultados > tbody > tr').remove();
			 $("#ulPaginacaoUsuarioAjax > li").remove();
			 
			  for(var p = 0; p < json.length; p++){
			      $('#tabelaResultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td> '+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn btn-info">Ver</button></td></tr>');
			  }
			  
			  document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
			  
			    var totalPagina = xhr.getResponseHeader("totalPagina");
		
				  for (var p = 0; p < totalPagina; p++){
				      
				      var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUsuarioAjaxPagina&pagina='+ (p * 5);
				      
				      $("#ulPaginacaoUsuarioAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUsuarioPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>'); 
				  }
		     }
		     
		 }).fail(function(xhr, status, errorThrown){
		    alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		 });
	}
	
	
	function buscarUsuario() {
		  
	    var nomeBusca = document.getElementById('nomeBusca').value;
	    
	    if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){  /*Validando que tem que ter valor pra buscar no banco*/
		
		 var urlAction = document.getElementById('form-usuario').action;
		
		 $.ajax({
		     
		     method: "get",
		     url : urlAction,
		     data : "nomeBusca=" + nomeBusca + '&acao=buscarUsuarioAjax',
		     success: function (response, textStatus, xhr) {
			 
			 var json = JSON.parse(response);
			 
			 
			 $('#tabelaResultados > tbody > tr').remove();
			 $("#ulPaginacaoUsuarioAjax > li").remove();
			 
			  for(var p = 0; p < json.length; p++){
			      $('#tabelaResultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td> '+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn btn-info">Ver</button></td></tr>');
			  }
			  
			  document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
			  
			    var totalPagina = xhr.getResponseHeader("totalPagina");
		
			  
			    
				  for (var p = 0; p < totalPagina; p++){
				      
				      var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUsuarioAjaxPagina&pagina='+ (p * 5);
				      
				      $("#ulPaginacaoUsuarioAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscarUsuarioPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>');
				      
				  }
		     }
		 
		 }).fail(function(xhr, status, errorThrown){
		    alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
		 });
	    }
	}

	function criarDeleteComAjax() {

		if (confirm('Deseja realmente excluir os dados?')) {

			var urlAction = document.getElementById('form-usuario').action;
			var idUsuario = document.getElementById('id').value;

			$.ajax({

				method : "get",
				url : urlAction,
				data : "id=" + idUsuario + "&acao=deletarAjax",
				success : function(response) {

					limparForm();
					document.getElementByuId('msg').textContent = response;
					//				alert(response);
				}

			}).fail(function(xhr, status, errorThrown) {
				alert('Erro ao deletar usuario por id: ' + xhr.responseText);
			});
		}
	}

	function criarDelete() {

		if (confirm('Deseja realmente excluir os dados?')) {
			document.getElementById('form-usuario').method = 'get';
			document.getElementById('acao').value = 'deletar';
			document.getElementById('form-usuario').submit();
		}
	}

	function limparForm() {

		// document.getElementById("form-usuario").reset();

		var elementos = document.getElementById('form-usuario').elements;

		for (p = 0; p < elementos.length; p++) {
			elementos[p].value = '';
		}
	}
</script>

</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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

											<div class="row">
												<div class="col-sm-12">
													<!-- Basic Form Inputs card start -->
													<div class="card">
														<div class="card-block">
															<h4 class="sub-title">Relat�rio de Usu�rio</h4>

															<form class="form-material"
																action="<%=request.getContextPath()%>/ServletUsuarioController"
																method="get" id="form-usuario">

																<input type="hidden" id="acaoRelatorioImprimirTipo"
																	name="acao" value="imprimirRelatorioUsuario">

																<div class="form-row align-items-center">
																	<div class="col-sm-3 my-1">
																		<label class="sr-only" for="dataInicial">Data
																			Inicial</label> <input value="${dataInicial}" type="text"
																			class="form-control" id="dataInicial"
																			name="dataInicial">
																	</div>

																	<div class="col-sm-3 my-1">
																		<label class="sr-only" for="dataFinal">Data
																			Final</label> <input value="${dataFinal}" type="text"
																			class="form-control" id="dataFinal" name="dataFinal">
																	</div>
																	<div class="col-auto my-1">
																		<button type="button" onclick="imprimirHtml();"
																			class="btn btn-primary">Imprimir relat�rio</button>
																		<button type="button" onclick="imprimirPdf();"
																			class="btn btn-primary">Imprimir PDF</button>
																		<button type="button" onclick="imprimirExcel();"
																			class="btn btn-primary">Imprimir Excel</button>
																	</div>
																</div>

															</form>

															<div style="height: 300px; overflow: scroll;">
																<table class="table" id="tabelaResultadosView">
																	<thead>
																		<tr>
																			<th scope="col">ID</th>
																			<th scope="col">Nome</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${listarUsuario}" var="mLs">
																			<tr>
																				<td><c:out value="${mLs.id}"></c:out></td>
																				<td><c:out value="${mLs.nome}"></c:out></td>
																			</tr>

																			<c:forEach items="${mLs.telefones}" var="fone">
																				<tr>
																					<td />
																					<td style="font-size: 10px;"><c:out
																							value="${fone.numero}"></c:out></td>
																				</tr>
																			</c:forEach>
																		</c:forEach>
																	</tbody>
																</table>
															</div>

														</div>
													</div>
												</div>
											</div>

										</div>
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

	<script type="text/javascript">
		function imprimirHtml() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioUsuario';
			$("#form-usuario").submit();
		}

		function imprimirExcel() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioExcel';
			$("#form-usuario").submit();
		}
		
		function imprimirPdf(){
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioPdf';
			$("#form-usuario").submit();
			
			return false;
		}

		$(function() {

			$("#dataInicial")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Ter�a',
										'Quarta', 'Quinta', 'Sexta', 'S�bado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'S�b', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Mar�o',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Pr�ximo',
								prevText : 'Anterior'
							});
		});

		$(function() {

			$("#dataFinal")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Ter�a',
										'Quarta', 'Quinta', 'Sexta', 'S�bado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'S�b', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Mar�o',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Pr�ximo',
								prevText : 'Anterior'
							});
		});
	</script>

</body>

</html>
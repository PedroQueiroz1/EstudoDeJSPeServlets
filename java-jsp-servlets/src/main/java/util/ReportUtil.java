package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@SuppressWarnings({"rawtypes","unchecked"})
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	// Lista genérica
	// ServletContext vem da requisição, contexto para saber como chegar nessa pasta
	// de relatórios
	public byte[] gerarRelatorioExcel(List listaDados, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext)
			throws Exception {
		
		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		// Caminho do jasper
		// File.separator = molda de acordo com o sistema operacional, no windows é a
		// barra.
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		
		// Juntar o relatorio com a lista de dados
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);
		
		JRExporter exporter = new JRXlsExporter(); // Excel
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		
		exporter.exportReport();
		
		return baos.toByteArray();
	}
	
	public byte[] gerarRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext)
			throws Exception {
		
		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		// Caminho do jasper
		// File.separator = molda de acordo com o sistema operacional, no windows é a
		// barra.
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		
		// Juntar o relatorio com a lista de dados
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
	
	public byte[] gerarRelatorioPDF(List listaDados, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext)
			throws Exception {
		
		// Cria a lista de dados que vem do nosso SQL da consulta feita
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		// Caminho do jasper
		// File.separator = molda de acordo com o sistema operacional, no windows é a
		// barra.
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		
		// Juntar o relatorio com a lista de dados
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
}

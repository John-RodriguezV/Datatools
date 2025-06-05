package com.ejemplo.reportejasper.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

@RestController
public class ReporteController {

    private final DataSource dataSource;

    public ReporteController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> generarReporte(@RequestParam(defaultValue = "pdf") String formato) {
        try (InputStream jasperStream = new ClassPathResource("reportes/credenciales.jasper").getInputStream()) {

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperStream, new HashMap<>(), dataSource.getConnection());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String fileName = "clientes";
            MediaType mediaType;

            switch (formato.toLowerCase()) {
                case "xlsx":
                    JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                    xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                    SimpleXlsxReportConfiguration xlsxConfig = new SimpleXlsxReportConfiguration();
                    xlsxConfig.setOnePagePerSheet(false);
                    xlsxExporter.setConfiguration(xlsxConfig);
                    xlsxExporter.exportReport();
                    mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    fileName += ".xlsx";
                    break;

                case "docx":
                    JRDocxExporter docxExporter = new JRDocxExporter();
                    docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                    docxExporter.exportReport();
                    mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    fileName += ".docx";
                    break;

                case "pdf":
                default:
                    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clientes.pdf")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(pdfBytes);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(mediaType)
                    .body(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

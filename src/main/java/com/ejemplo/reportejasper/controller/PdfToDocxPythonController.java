package com.ejemplo.reportejasper.controller;

import com.ejemplo.reportejasper.service.PythonConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.nio.file.Files;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/conversion")
public class PdfToDocxPythonController {

    @Autowired
    private PythonConversionService conversionService;

    @PostMapping("/pdf-to-docx")
    public ResponseEntity<byte[]> convertPdfToDocx(@RequestParam("file") MultipartFile multipartFile) {
        try {
            // Guardar archivo PDF temporalmente
            File tempPdf = File.createTempFile("input", ".pdf");
            multipartFile.transferTo(tempPdf);

            // Crear archivo DOCX temporal para salida
            File tempDocx = File.createTempFile("output", ".docx");

            // Llamar al servicio que ejecuta el script Python
            conversionService.convertPdfToDocx(
                    tempPdf.getAbsolutePath(),
                    tempDocx.getAbsolutePath()
            );

            byte[] docxBytes = Files.readAllBytes(tempDocx.toPath());

            // Eliminar archivos temporales
            tempPdf.delete();
            tempDocx.delete();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documento_convertido.docx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(docxBytes);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}

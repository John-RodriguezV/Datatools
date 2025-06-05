package com.ejemplo.reportejasper.controller;

import com.ejemplo.reportejasper.service.PythonDocxToPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/docx-to-pdf")
public class DocxToPdfController {

    @Autowired
    private PythonDocxToPdfService conversionService;

    @PostMapping
    public ResponseEntity<FileSystemResource> convertDocxToPdf(@RequestParam("file") MultipartFile file) {
        try {
            File inputFile = File.createTempFile("input", ".docx");
            file.transferTo(inputFile);

            File outputDir = inputFile.getParentFile();
            conversionService.convertDocxToPdf(inputFile.getAbsolutePath(), outputDir.getAbsolutePath());

            String pdfFileName = inputFile.getName().replaceAll("\\.\\w+$", ".pdf");
            File outputFile = new File(outputDir, pdfFileName);

            if (!outputFile.exists()) {
                throw new IOException("El archivo PDF no fue generado.");
            }

            FileSystemResource resource = new FileSystemResource(outputFile);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(outputFile.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (IOException | InterruptedException e) {
             e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}

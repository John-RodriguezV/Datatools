package com.ejemplo.reportejasper.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PythonDocxToPdfService {

    public void convertDocxToPdf(String inputPath, String outputDir) throws IOException, InterruptedException {
        String pythonExecutable = "C:\\Program Files\\Python313\\python.exe";

        File scriptFile = new File("scripts/convert_docx_to_pdf.py");
        if (!scriptFile.exists()) {
            throw new FileNotFoundException("Script no encontrado: " + scriptFile.getAbsolutePath());
        }

        ProcessBuilder pb = new ProcessBuilder(
                pythonExecutable,
                scriptFile.getAbsolutePath(),
                inputPath,
                outputDir
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder log = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            log.append(line).append("\n");
            System.out.println("PYTHON >> " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Error al convertir DOCX a PDF. Código: " + exitCode + "\nLog:\n" + log);
        }
    }
}

package com.ejemplo.reportejasper.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PythonConversionService {

    public void convertPdfToDocx(String inputPath, String outputPath) throws IOException, InterruptedException {
        // ✅ Ruta corregida al ejecutable real de Python
        String pythonExecutable = "C:\\Program Files\\Python313\\python.exe";
        
        File scriptFile = new File("scripts/convert_pdf_to_docx.py");
        if (!scriptFile.exists()) {
            throw new FileNotFoundException("No se encontró el script: " + scriptFile.getAbsolutePath());
        }

        ProcessBuilder pb = new ProcessBuilder(
                pythonExecutable,
                scriptFile.getAbsolutePath(),
                inputPath,
                outputPath
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        // ✅ Leer salida del proceso Python para debug
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder outputLog = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            outputLog.append(line).append("\n");
            System.out.println("PYTHON >> " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Error al ejecutar el script Python. Código: " + exitCode + "\nLog:\n" + outputLog);
        }
    }
}

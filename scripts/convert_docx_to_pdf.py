import sys
import os
import subprocess

def convert_docx_to_pdf(input_path, output_dir):
    if not os.path.exists(input_path):
        print(f"Archivo .docx no encontrado: {input_path}")
        sys.exit(1)

    libreoffice_path = r"C:\Program Files\LibreOffice\program\soffice.exe"

    try:
        command = [
            libreoffice_path,
            "--headless",
            "--convert-to", "pdf",
            "--outdir", output_dir,
            input_path
        ]
        result = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        print(result.stdout)
        if result.returncode != 0:
            print(result.stderr)
            sys.exit(result.returncode)
    except Exception as e:
        print(f"Error durante la conversión: {e}")
        sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Uso: python convert_docx_to_pdf.py archivo.docx carpeta_destino")
        sys.exit(1)

    input_file = sys.argv[1]
    output_folder = sys.argv[2]
    convert_docx_to_pdf(input_file, output_folder)

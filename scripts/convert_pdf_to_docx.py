import sys
from pdf2docx import Converter

if len(sys.argv) != 3:
    print("Uso: convert_pdf_to_docx.py input.pdf output.docx")
    sys.exit(1)

input_pdf = sys.argv[1]
output_docx = sys.argv[2]

try:
    cv = Converter(input_pdf)
    cv.convert(output_docx, start=0, end=None)
    cv.close()
    print("Conversión completada exitosamente")
except Exception as e:
    print("Error durante la conversión:", e)
    sys.exit(2)

package servicios.reportes;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.io.image.ImageDataFactory;
import java.net.URL;
import java.util.List;
import java.util.ArrayList; // Te será útil para manejar colecciones

public class ReportePDF
{

    // Método 1: Reporte de Alumnos o Personal (Consulta General)
    public void generarConsultaGeneral(String titulo, String carreraTipo, List<Object[]> listaDatos, String ruta) throws Exception
    {
        PdfWriter writer = new PdfWriter(ruta);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        agregarEncabezado(doc);

        doc.add(new Paragraph(titulo).setTextAlignment(TextAlignment.CENTER).setFontSize(16).setBold());
        doc.add(new Paragraph("Tipo/Carrera predominante: " + carreraTipo).setMarginBottom(10));

        // Tabla con 3 columnas
        Table table = new Table(new float[]
        {
            1, 3, 2
        });
        table.addHeaderCell("ID");
        table.addHeaderCell("Nombre");
        table.addHeaderCell("Estatus/Carrera");

        // --- AQUÍ ESTÁ EL CAMBIO: Recorremos la lista ---
        for (Object[] fila : listaDatos)
        {
            table.addCell(fila[0] != null ? fila[0].toString() : "N/A"); // ID

            // Concatenamos nombre completo si es necesario, o tomamos los campos
            String nombreCompleto = fila[1] + " " + fila[2] + " " + fila[3];
            table.addCell(nombreCompleto);

            // Aquí ajustas el índice según tu base de datos (ej. índice 11 o 13)
            table.addCell(fila[11] != null ? fila[11].toString() : "N/A");
        }

        doc.add(table);
        doc.close();
    }

    // Método 2: Reporte de Cita Específica
    public void generarReporteCita(Object[] datosCita, String ruta) throws Exception
    {
        PdfWriter writer = new PdfWriter(ruta);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        agregarEncabezado(doc);

        doc.add(new Paragraph("Detalle de Cita Médica").setTextAlignment(TextAlignment.CENTER).setFontSize(14));

        // Tabla de datos de la cita
        Table table = new Table(new float[]
        {
            1, 3
        });
        table.addCell("Paciente:");
        table.addCell(datosCita[0].toString());
        table.addCell("Padecimiento:");
        table.addCell(datosCita[1].toString());
        table.addCell("Plan:");
        table.addCell(datosCita[4].toString());

        doc.add(table);
        doc.close();
    }

    private void agregarEncabezado(Document doc) throws Exception
    {
        // Carga del Logo desde recursos
        URL url = getClass().getResource("/recursos/logo_uaemex.png");

        if (url != null)
        {
            Image logo = new Image(ImageDataFactory.create(url));

            // Escalar correctamente
            logo.scaleToFit(80, 80);

            // Centrar imagen
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);

            doc.add(logo);
        }

        doc.add(new Paragraph("Centro Universitario UAEM Tianguistenco")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold());

        doc.add(new Paragraph("----------------------------------------------------------------")
                .setMarginBottom(20));
    }
}

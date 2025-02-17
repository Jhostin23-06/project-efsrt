package com.cibertec.projectefsrt.services;

import com.cibertec.projectefsrt.entities.Alquiler;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private AlquilerService alquilerService;

    public void generarReportePDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_alquileres.pdf");

        Document document = new Document(PageSize.A4.rotate()); // Formato apaisado para mejor visualización
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Agregar título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE);
        Paragraph title = new Paragraph("Reporte de Alquileres", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Crear tabla con 7 columnas (antes eran 6)
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{2, 3, 3, 4, 4, 4, 4}); // Ajustar tamaño de columnas

        // Estilo de encabezado
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        PdfPCell cell;
        BaseColor headerColor = new BaseColor(0, 102, 204); // Azul oscuro

        String[] headers = {"ID", "Código", "Fecha Préstamo", "Fecha Devolución", "Película", "Cliente", "Empleado"};

        for (String header : headers) {
            cell = new PdfPCell(new Phrase(header, headFont));
            cell.setBackgroundColor(headerColor);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Obtener datos
        List<Alquiler> alquileres = alquilerService.findAll();
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        for (Alquiler alquiler : alquileres) {

            cell = new PdfPCell(new Phrase(alquiler.getId().toString(), bodyFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(alquiler.getCodAlquiler(), bodyFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(alquiler.getFechaPrest().toString(), bodyFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(alquiler.getFechaDev().toString(), bodyFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(alquiler.getIdPelicula().getNomPelicula(), bodyFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(alquiler.getIdCliente().getNomCliente(), bodyFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(alquiler.getIdEmpleado().getNomEmpleado(), bodyFont)); // Agregar Empleado
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        document.add(table);
        document.close();
    }

}

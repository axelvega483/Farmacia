package com.proyecto.farmacia.PDF;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyecto.farmacia.entity.DetalleVenta;
import com.proyecto.farmacia.entity.Venta;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {

    private static final String RUTA_PDF = "/ruta/a/tu/directorio/pdf";

    public static String generarFacturaPDF(Venta venta) {
        try {
            // Crear el directorio donde se guardará el PDF si no existe
            File directory = new File(RUTA_PDF);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Ruta del archivo PDF
            String pathArchivo = directory + File.separator + "factura-compra-" + venta.getId() + ".pdf";

            // Crear el documento y establecer el tamaño de la página
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(pathArchivo));

            document.open();

            // Títulos y fuentes
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            Font fontCuerpo = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);

            // Título de la factura
            Paragraph encabezado = new Paragraph("Factura de Compra Farmacia", fontTitulo);
            encabezado.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(encabezado);

            // Fecha de compra
            Paragraph fechaCompra = new Paragraph("Fecha: " + venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), fontCuerpo);
            fechaCompra.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(fechaCompra);

            // ID de pedido y cliente
            Paragraph pedidoId = new Paragraph("ID Pedido: " + venta.getId(), fontCuerpo);
            pedidoId.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pedidoId);

            Paragraph cliente = new Paragraph("Cliente: " + venta.getCliente().getNombre(), fontCuerpo);
            cliente.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(cliente);

            document.add(new Paragraph(" "));
            // Detalles del pedido (tabla)
            PdfPTable table = new PdfPTable(3);  // Tres columnas: Producto, Cantidad, Precio
            table.setWidthPercentage(100);  // Ocupa el 100% del ancho de la página

            // Encabezados de la tabla
            table.addCell(new PdfPCell(new Phrase("Producto", fontCuerpo)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", fontCuerpo)));
            table.addCell(new PdfPCell(new Phrase("Precio", fontCuerpo)));

            // Detalles de los productos del pedido
            for (DetalleVenta detalle : venta.getDetalleventas()) {
                table.addCell(new PdfPCell(new Phrase(detalle.getMedicamento().getNombre(), fontCuerpo)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()), fontCuerpo)));
                table.addCell(new PdfPCell(new Phrase("$" + detalle.getMedicamento().getPrecio(), fontCuerpo)));
            }

            document.add(table);

            // Total
            Paragraph total = new Paragraph("Total: $" + venta.getTotal(), fontCuerpo);
            total.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(total);

            document.close();

            return pathArchivo;

        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

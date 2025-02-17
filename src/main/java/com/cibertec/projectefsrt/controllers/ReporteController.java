package com.cibertec.projectefsrt.controllers;

import com.cibertec.projectefsrt.services.ReporteService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/alquileres")
    public void descargarReporte(HttpServletResponse response) {
        try {
            reporteService.generarReportePDF(response);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }


}

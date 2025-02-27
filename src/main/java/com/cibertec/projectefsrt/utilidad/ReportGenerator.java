package com.cibertec.projectefsrt.utilidad;


import com.cibertec.projectefsrt.dto.AlquilerDTO;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportGenerator {
	public byte[] exportToPdf(List<AlquilerDTO> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(list));
    }

    public byte[] exportToXls(List<AlquilerDTO> list) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    private JasperPrint getReport(List<AlquilerDTO> list) throws JRException, IOException {
	    Map<String, Object> params = new HashMap<>();
	    params.put("alquilerData", new JRBeanCollectionDataSource(list));
	
	    // Cargar el archivo JRXML como flujo de entrada (InputStream)
	    InputStream reportStream = getClass().getResourceAsStream("/efsrt.jrxml");
	    if (reportStream == null) {
	        throw new FileNotFoundException("No se encontró el archivo efsrt.jrxml en classpath.");
	    }
	
	    JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
	    JasperPrint report = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
	
	    return report;
     }
}

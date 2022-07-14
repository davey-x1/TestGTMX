package com.technicTest.TestGTMX.dto;

import com.technicTest.TestGTMX.models.Factura;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FacturaDTO {
    private long id;
    private LocalDate fecha;
    private long monto;

    public FacturaDTO(Factura factura){
        this.id = factura.getId();
        this.fecha = factura.getFecha();
        this.monto = factura.getMonto();
    }
}

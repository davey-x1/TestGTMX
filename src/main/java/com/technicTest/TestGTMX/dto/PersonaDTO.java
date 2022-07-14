package com.technicTest.TestGTMX.dto;

import com.technicTest.TestGTMX.models.Persona;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PersonaDTO {
    private long id;
    private String nombre, apellidoPaterno;
    private String apellidoMaterno;
    private long identificacion;

    private List<FacturaDTO> facturas;

    public PersonaDTO(Persona persona){
        this.id = persona.getId();
        this.nombre = persona.getNombre();
        this.apellidoPaterno = persona.getApellidoPaterno();
        this.apellidoMaterno = persona.getApellidoMaterno();
        this.identificacion = persona.getIdentificacion();
        this.facturas = persona.getFacturas().stream().map(FacturaDTO::new).collect(Collectors.toList());
    }
}

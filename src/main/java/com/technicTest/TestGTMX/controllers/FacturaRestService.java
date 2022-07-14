package com.technicTest.TestGTMX.controllers;

import com.technicTest.TestGTMX.dto.FacturaDTO;
import com.technicTest.TestGTMX.models.Factura;
import com.technicTest.TestGTMX.models.Persona;
import com.technicTest.TestGTMX.services.Directorio;
import com.technicTest.TestGTMX.services.Ventas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/factura")
public class FacturaRestService {
    private static final Logger LOG = LoggerFactory.getLogger(FacturaRestService.class);

    @Autowired
    Directorio directorio;

    @Autowired
    Ventas ventas;

    @GetMapping("/find")
    public ResponseEntity<?> getFacturasByPersona(
            @RequestParam long identificacion){

        LOG.info("Encontrando persona con identificacion " + identificacion);
        Persona persona = directorio.findPersonaByIdentificacion(identificacion);
        if (persona == null){
            LOG.error("Persona con identificacion " + identificacion + " no encontrada");
            return new ResponseEntity<>("Persona con identificacion " + identificacion +  " no encontrada", HttpStatus.FORBIDDEN);
        }

        LOG.info("Encontrando todas las facturas de la persona");
        List<Factura> listaFacturas = ventas.findFacturasByPersona(persona);
        List<FacturaDTO> listaDTO = listaFacturas.stream().map(FacturaDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(listaDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> storeFactura(
            @RequestParam long identificacion_persona,
            @RequestParam long monto
    ){

        LOG.info("Encontrando persona con identificacion " + identificacion_persona);
        Persona persona = directorio.findPersonaByIdentificacion(identificacion_persona);
        if (persona == null){
            LOG.error("Persona con identificacion " + identificacion_persona + " no encontrada");
            return new ResponseEntity<>("Persona con identificacion " + identificacion_persona +  " no encontrada", HttpStatus.FORBIDDEN);
        }

        LocalDate fecha = LocalDate.now();

        LOG.info("Creando factura");
        Factura factura = new Factura(fecha, monto);
        persona.addFactura(factura);
        LOG.info("Guardando factura");
        FacturaDTO facturaDTO = new FacturaDTO(ventas.storeFactura(factura));
        LOG.info("Factura creada exitosamente");
        return new ResponseEntity<>(facturaDTO, HttpStatus.ACCEPTED);
    }
}

package com.technicTest.TestGTMX.controllers;

import com.technicTest.TestGTMX.dto.PersonaDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persona")
public class DirectorioRestService {
    private static final Logger LOG = LoggerFactory.getLogger(FacturaRestService.class);

    @Autowired
    Directorio directorio;

    @Autowired
    Ventas ventas;

    @GetMapping("/all")
    public ResponseEntity<?> getPersonas(){
        LOG.info("Encontrando todas las personas");
        List<PersonaDTO> listaDTO = directorio.findPersonas().stream().map(PersonaDTO::new).collect(Collectors.toList());
        LOG.trace("Personas encontradas exitosamente");
        return new ResponseEntity<>(listaDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/find")
    public ResponseEntity<?> getPersonaByIdentificacion(
            @RequestParam long identificacion){
        LOG.info("Encontrando persona con identificacion " + identificacion);
        Persona persona = directorio.findPersonaByIdentificacion(identificacion);

        if(persona == null){
            LOG.error("Persona con identificacion " + identificacion + " no encontrada");
            return new ResponseEntity<>("Persona no encontrada", HttpStatus.FORBIDDEN);
        }

        LOG.trace("Persona con identificacion " + identificacion + " encontrada correctamente");
        PersonaDTO personaDTO = new PersonaDTO(persona);
        return new ResponseEntity<>(personaDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<?> savePersona(
            @RequestParam long identificacion,
            @RequestParam String nombre,
            @RequestParam String apellidoPaterno,
            @RequestParam String apellidoMaterno){

        LOG.info("Encontrando persona con identificacion " + identificacion);
        Persona persona = directorio.findPersonaByIdentificacion(identificacion);
        if(persona != null){
            LOG.error("Persona con identificacion " + identificacion + " ya existe");
            return new ResponseEntity<>("Persona ya registrada", HttpStatus.FORBIDDEN);
        }

        if(nombre.equals("") || apellidoPaterno.equals("")){
            LOG.error("Nombre o apellidoPaterno vacios");
            return new ResponseEntity<>("Hay datos que no pueden estar vacios", HttpStatus.FORBIDDEN);
        }

        Persona nuevaPersona = new Persona(nombre, apellidoPaterno, apellidoMaterno, identificacion);
        LOG.info("Guardando nuevo registro persona en el sistema");
        Persona personaCreada = directorio.storePersona(nuevaPersona);
        LOG.trace("Persona guardada correctamente");
        return new ResponseEntity<>(personaCreada, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/remove")
    @Transactional
    public ResponseEntity<?> deletePersonaByIdentificacion(
            @RequestParam long identificacion){

        LOG.info("Encontrando persona con identificacion " + identificacion);
        Persona persona = directorio.findPersonaByIdentificacion(identificacion);
        if(persona == null){
            LOG.error("Persona con identificacion " + identificacion + " no encontrada");
            return new ResponseEntity<>("Datos incorrectos", HttpStatus.FORBIDDEN);
        }

        List<Factura> facturasList = ventas.findFacturasByPersona(persona);
        facturasList.forEach(factura -> {
            ventas.deleteFactura(factura);
        });

        directorio.deletePersonaByIdentificacion(persona);
        LOG.trace("Persona eliminada correctamente");
        return new ResponseEntity<>("Persona eliminada correctamente", HttpStatus.ACCEPTED);
    }
}

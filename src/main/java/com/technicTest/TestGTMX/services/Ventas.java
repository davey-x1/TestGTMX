package com.technicTest.TestGTMX.services;

import com.technicTest.TestGTMX.models.Factura;
import com.technicTest.TestGTMX.models.Persona;
import com.technicTest.TestGTMX.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Ventas {

    @Autowired
    FacturaRepository facturaRepository;

    public List<Factura> findFacturasByPersona(Persona persona){
        return facturaRepository.findAllByPersona(persona);
    }

    public Factura storeFactura(Factura factura){
        return facturaRepository.save(factura);
    }

    public void deleteFactura(Factura factura){
        facturaRepository.delete(factura);
    }
}

package com.technicTest.TestGTMX.repositories;

import com.technicTest.TestGTMX.models.Factura;
import com.technicTest.TestGTMX.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findAllByPersona(Persona persona);
}

package com.technicTest.TestGTMX.repositories;

import com.technicTest.TestGTMX.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Persona findByIdentificacion(long identificacion);
}

package com.technicTest.TestGTMX.services;

import com.technicTest.TestGTMX.models.Persona;
import com.technicTest.TestGTMX.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Directorio {

    @Autowired
    PersonaRepository personaRepository;

    public Persona findPersonaByIdentificacion(long identificacion){
        return personaRepository.findByIdentificacion(identificacion);
    }

    public List<Persona> findPersonas(){
        return personaRepository.findAll();
    }

    public void deletePersonaByIdentificacion(Persona persona){
        personaRepository.delete(persona);
    }

    public Persona storePersona(Persona persona){
        return personaRepository.save(persona);
    }
}

package com.technicTest.TestGTMX.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @NotBlank (message =  "field cannot be empty")
    private String nombre, apellidoPaterno;

    private String apellidoMaterno;

    private long identificacion;

    @OneToMany(mappedBy="persona", fetch=FetchType.EAGER)
    private Set<Factura> facturas = new HashSet<>();

    public Persona(String nombre, String apellidoPaterno, String apellidoMaterno, long identificacion){
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.identificacion = identificacion;
    }

    public Persona(){

    }

    public void addFactura(Factura factura){
        factura.setPersona(this);
        facturas.add(factura);
    }

}

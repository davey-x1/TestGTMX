package com.technicTest.TestGTMX.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDate fecha;

    private long monto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="persona_id")
    private Persona persona;

    public Factura(LocalDate fecha, long monto){
        this.fecha = fecha;
        this.monto = monto;
    }

    public Factura(){

    }

}

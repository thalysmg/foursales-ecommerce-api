package br.com.foursales.ecommerce.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perfil")
public class Perfil {
    @Id
    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    //TODO [JPA Buddy] generate columns from DB
}
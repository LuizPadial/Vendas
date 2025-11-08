package com.test.venda.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "VENDEDOR")
public class Vendedor extends Pessoa {

    @Id
    @Column(name ="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

}

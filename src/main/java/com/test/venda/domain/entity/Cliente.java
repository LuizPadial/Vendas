package com.test.venda.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "CLIENTE")
public class Cliente extends Pessoa {

    @Id
    @Column(name ="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

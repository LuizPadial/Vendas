package com.test.venda.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Pessoa {

    @Column(name = "CPF")
    @NotBlank
    @CPF
    protected String cpf;

    @Column(name = "NOME_COMPLETO")
    @NotBlank
    @Size(min = 5, max = 60)
    protected String nomeCompleto;


}

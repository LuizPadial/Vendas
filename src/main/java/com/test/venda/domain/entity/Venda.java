package com.test.venda.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VENDA")
public class Venda {

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Vendedor vendedor;

    @ManyToMany
    private List<Produto> produto = new ArrayList<>();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA_VENDA")
    private LocalDateTime dataVenda;

    @Column(name= "VALOR_TOTAL")
    private BigDecimal valorTotal;
}

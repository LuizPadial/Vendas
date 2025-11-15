package com.test.venda.domain.entity;

import com.test.venda.api.dto.request.ClienteRequest;
import com.test.venda.api.dto.request.ProdutoRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 30)
    private String nomeProduto;

    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @DecimalMax(value = "999999999", message = "O preço máximo permitido é 999999999")
    private BigDecimal preco;

    public void editar(ProdutoRequest request) {
        this.nomeProduto = request.getNomeProduto();
        this.preco = request.getPreco();
    }
}

package com.test.venda.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendaResponse {

    private Long idVenda;
    private ClienteResponse cliente;
    private VendedorResponse vendedor;
    private List<ProdutoResponse> produto;
    private BigDecimal valorTotal;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataVenda;


}

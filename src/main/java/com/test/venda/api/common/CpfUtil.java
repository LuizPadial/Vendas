package com.test.venda.api.common;

import com.test.venda.domain.exceptions.NegocioException;

public class CpfUtil {

    public static String formatarCpf(String cpf) {

        String cpfFormatado = cpf.replaceAll("[^0-9]", "");
        int digitosCpf = cpfFormatado.length();
        if (digitosCpf < 11) {
            throw new NegocioException("CPF Inválido.");
        }
        if (digitosCpf > 11) {
            throw new NegocioException("CPF Inválido.");
        }
        return cpfFormatado;
    }
    public static boolean cpfNull(String cpf){
        return cpf == null;
    }

    public static String desformatarCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }
}


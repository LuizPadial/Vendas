package com.test.venda.api.common;

public class NomeUtil {
    public static String normalizarNome(String nome) {
        if (nome == null) return null;
        return nome.trim().toLowerCase();
    }

    public static boolean nomeInvalido(String nome) {
        return nome == null || nome.trim().isEmpty();
    }
}

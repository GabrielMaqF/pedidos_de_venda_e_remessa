package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.token;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenCacheHandler {
	private static final String TOKEN_FILE = ".runtime/token.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void salvarToken(ResponseTokenMicrosftGraph token, long expiresAt) {
        try {
            TokenCache cache = new TokenCache(token.getAccessToken(), expiresAt);
            mapper.writeValue(new File(TOKEN_FILE), cache);
        } catch (IOException e) {
            System.err.println("Erro ao salvar token no cache: " + e.getMessage());
        }
    }

    public static TokenCache lerToken() {
        try {
            File file = new File(TOKEN_FILE);
            if (file.exists()) {
                return mapper.readValue(file, TokenCache.class);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler token do cache: " + e.getMessage());
        }
        return null;
    }

    public static class TokenCache {
        public String accessToken;
        public long expiresAt;

        public TokenCache() {} // Jackson precisa

        public TokenCache(String accessToken, long expiresAt) {
            this.accessToken = accessToken;
            this.expiresAt = expiresAt;
        }
    }
}

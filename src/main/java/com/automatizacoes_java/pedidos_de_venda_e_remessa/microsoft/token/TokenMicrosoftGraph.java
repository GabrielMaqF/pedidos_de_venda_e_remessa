package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.token;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphTokenProperties;

import jakarta.annotation.PostConstruct;

@Service
public class TokenMicrosoftGraph {

    @Autowired
    private GraphTokenProperties graphTokenProperties;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private WebClient webClient;

    private volatile String tokenArmazenado;
    private volatile long tempoExpirarToken;
    private volatile CompletableFuture<String> tokenFuture;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(graphTokenProperties.getUrlToken()).build();

        // Tentativa de carregar token de cache
        TokenCacheHandler.TokenCache cache = TokenCacheHandler.lerToken();
        if (cache != null && System.currentTimeMillis() < cache.expiresAt) {
            this.tokenArmazenado = cache.accessToken;
            this.tempoExpirarToken = cache.expiresAt;
        }
    }

    @Async
    public CompletableFuture<String> getToken() {
        long agora = System.currentTimeMillis();

        if (tokenArmazenado != null && agora < tempoExpirarToken) {
            return CompletableFuture.completedFuture(tokenArmazenado);
        }

        synchronized (this) {
            if (tokenArmazenado != null && agora < tempoExpirarToken) {
                return CompletableFuture.completedFuture(tokenArmazenado);
            }

            if (tokenFuture != null && !tokenFuture.isDone()) {
                return tokenFuture;
            }

            tokenFuture = gerarTokenAsync().thenApply(token -> {
                tokenArmazenado = token.getAccessToken();
                tempoExpirarToken = System.currentTimeMillis() + (token.getExpiresIn() - 60) * 1000L;

                // Salvar no arquivo
                TokenCacheHandler.salvarToken(token, tempoExpirarToken);

                return tokenArmazenado;
            });

            return tokenFuture;
        }
    }

    private CompletableFuture<ResponseTokenMicrosftGraph> gerarTokenAsync() {
        return webClient.post()
            .uri("")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("client_id", graphTokenProperties.getClientId())
                .with("scope", graphTokenProperties.getScope())
                .with("client_secret", graphTokenProperties.getClientSecret())
                .with("grant_type", graphTokenProperties.getGrantType()))
            .retrieve()
            .bodyToMono(ResponseTokenMicrosftGraph.class)
            .toFuture()
            .exceptionally(ex -> {
                throw new RuntimeException("Erro ao obter token Microsoft Graph", ex);
            });
    }
}

/*
@Service
public class TokenMicrosoftGraph {

	@Autowired
	private GraphTokenProperties graphTokenProperties;

	@Autowired
	private WebClient.Builder webClientBuilder;

	private WebClient webClient;

	private volatile CompletableFuture<String> tokenFuture;
	private volatile String tokenArmazenado;
	private volatile long tempoExpirarToken;

	@PostConstruct
	public void init() {
		this.webClient = webClientBuilder.baseUrl(graphTokenProperties.getUrlToken()).build();
	}

	@Async
	public CompletableFuture<String> getToken() {
		long agora = System.currentTimeMillis();

		if (tokenArmazenado != null && agora < tempoExpirarToken) {
			return CompletableFuture.completedFuture(tokenArmazenado);
		}

		synchronized (this) {
			// Verifica novamente após entrar no bloco sincronizado
			if (tokenArmazenado != null && agora < tempoExpirarToken) {
				return CompletableFuture.completedFuture(tokenArmazenado);
			}

			// Se uma chamada de geração já está em andamento, retorna a mesma
			if (tokenFuture != null && !tokenFuture.isDone()) {
				return tokenFuture;
			}

			// Gera um novo token e salva no cache
			tokenFuture = gerarTokenAsync().thenApply(token -> {
				tokenArmazenado = token.getAccessToken();
				tempoExpirarToken = System.currentTimeMillis() + (token.getExpiresIn() - 60) * 1000L;
				return token.getAccessToken();
			});

			return tokenFuture;
		}
	}

//    @Async
//    public CompletableFuture<String> getToken() {
//    	if (tokenArmazenado != null && System.currentTimeMillis() < tempoExpirarToken) {
//            return CompletableFuture.completedFuture(tokenArmazenado);
//        }
//        return gerarTokenAsync()
//            .thenApply(token -> {
//
//            	tokenArmazenado = token.getAccessToken();
//            	tempoExpirarToken = System.currentTimeMillis() + (token.getExpiresIn() - 60) * 1000L;
//
//                return token.getAccessToken();
//            });
//    }

	private CompletableFuture<ResponseTokenMicrosftGraph> gerarTokenAsync() {
		return webClient.post().uri("").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData("client_id", graphTokenProperties.getClientId())
						.with("scope", graphTokenProperties.getScope())
						.with("client_secret", graphTokenProperties.getClientSecret())
						.with("grant_type", graphTokenProperties.getGrantType()))
				.retrieve().bodyToMono(ResponseTokenMicrosftGraph.class).toFuture().exceptionally(ex -> {
					throw new RuntimeException("Erro ao obter token Microsoft Graph", ex);
				});
	}
}
 */

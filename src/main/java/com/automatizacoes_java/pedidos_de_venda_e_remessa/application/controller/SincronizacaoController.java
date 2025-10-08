package com.automatizacoes_java.pedidos_de_venda_e_remessa.application.controller;


import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service.SincronizacaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SincronizacaoController {

    private final SincronizacaoService sync;

    @PostMapping("/all")
    public Map<String, Object> syncAll(){
    	
    	sync.sincronizarTudo();
    	
        Map<String, Object> out = new LinkedHashMap<>();
        Instant start = Instant.now();
        out.put("startedAt", OffsetDateTime.now().toString());

        try {
            out.put("sharepoint_base", runStep("sincronizarTudo", () -> sync.sincronizarTudo()));
            out.put("ordens_servico_1", runStep("sincronizarOrdensDeServico", () -> {
				try {
					sync.sincronizarOrdensDeServico();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
            out.put("notas_fiscais", runStep("sincronizarNotasFiscais", () -> sync.sincronizarNotasFiscais()));
            out.put("cnae", runStep("sincronizarCnae", () -> sync.sincronizarCnae()));
            out.put("tipos_faturamento", runStep("sincronizarTiposFaturamentoContrato", () -> sync.sincronizarTiposFaturamentoContrato()));
            out.put("servicos_cadastrados", runStep("sincronizarServicosCadastrados", () -> {
				try {
					sync.sincronizarServicosCadastrados();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
            out.put("ordens_servico_2", runStep("sincronizarOrdensDeServico", () -> {
				try {
					sync.sincronizarOrdensDeServico();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
            out.put("contratos_servico", runStep("sincronizarContratosServico", () -> sync.sincronizarContratosServico()));

            out.put("status", "OK");
        } catch (Exception e) {
            log.error("Erro na sincronização completa", e);
            out.put("status", "ERROR");
            out.put("error", e.getClass().getSimpleName() + ": " + (e.getMessage() == null ? "" : e.getMessage()));
        } finally {
            out.put("elapsed", Duration.between(start, Instant.now()).toString());
        }
        return out;
    }

    private Map<String, String> runStep(String nome, Runnable r) {
        Map<String, String> step = new LinkedHashMap<>();
        Instant s = Instant.now();
        try {
            r.run();
            step.put("step", nome);
            step.put("status", "OK");
        } catch (Exception e) {
            step.put("step", nome);
            step.put("status", "ERROR");
            step.put("error", e.getMessage());
            throw e; // Propaga p/ parar a pipeline; remova se quiser continuar mesmo com erro.
        } finally {
            step.put("elapsed", Duration.between(s, Instant.now()).toString());
        }
        return step;
    }
}

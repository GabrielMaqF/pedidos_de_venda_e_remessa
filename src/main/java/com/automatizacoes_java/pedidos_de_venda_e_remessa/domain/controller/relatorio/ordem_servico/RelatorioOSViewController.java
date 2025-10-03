package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.controller.relatorio.ordem_servico;


import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/relatorios/os")
@RequiredArgsConstructor
public class RelatorioOSViewController {

    private final JdbcTemplate jdbc;

    // Retorna TODAS as linhas da view, sem paginação,
    // e converte qualquer tipo para String (null continua null).
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, String>> listarTudo() {
        final String sql = """
            SELECT *
            FROM public.v_relatorio_ordens_servico
            ORDER BY "dataDeEmissao" DESC NULLS LAST, "os" DESC
            """;

        return jdbc.query(sql, (rs, rowNum) -> {
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            Map<String, String> row = new LinkedHashMap<>(cols);
            row.put("ID", String.valueOf(rowNum + 1));
            for (int i = 1; i <= cols; i++) {
                String label = md.getColumnLabel(i);       // usa o alias exato da view
                Object val = rs.getObject(i);
                row.put(label, (val == null ? null : String.valueOf(val)));
            }
            return row;
        });
    }
}

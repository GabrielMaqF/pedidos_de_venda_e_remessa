package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatorioService {

	private final JdbcTemplate jdbc;

	public List<Map<String, String>> listarTudoRelatorioOrdemServico() {
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
				String label = md.getColumnLabel(i); // usa o alias exato da view
				Object val = rs.getObject(i);
				row.put(label, (val == null ? null : String.valueOf(val)));
			}
			return row;
		});
	}

}

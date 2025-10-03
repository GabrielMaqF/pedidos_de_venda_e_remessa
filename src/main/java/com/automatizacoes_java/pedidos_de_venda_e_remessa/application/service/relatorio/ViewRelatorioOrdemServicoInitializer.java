package com.automatizacoes_java.pedidos_de_venda_e_remessa.application.service.relatorio;

import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewRelatorioOrdemServicoInitializer {

    private final JdbcTemplate jdbc;
    private final ResourceLoader loader;
    private final DataSource dataSource;

    @Value("${app.view.force-drop-table:false}")
    private boolean forceDropTable;

    private static final String VIEW = "v_relatorio_ordens_servico";
    private static final String SCHEMA = "public"; // ajuste se usa outro schema
    private static final String SQL_PATH = "classpath:sql/V_RELATORIO_ORDENS_SERVICO.sql";

    @EventListener(ApplicationReadyEvent.class) // roda depois que tudo subiu
    public void createOrReplaceView() {
        try {
            // 0) Log do contexto de conexão
            String db = jdbc.queryForObject("select current_database()", String.class);
            String schema = jdbc.queryForObject("select current_schema()", String.class);
            String url = (dataSource instanceof HikariDataSource)
                    ? ((HikariDataSource) dataSource).getJdbcUrl()
                    : "jdbc-url-n/a";
            log.info("Inicializando view {}.{} | DB={} | schema atual={} | URL={}",
                    SCHEMA, VIEW, db, schema, url);

            // 1) Existe TABELA com o mesmo nome?
            boolean tableExists = jdbc.queryForObject("""
                select exists(
                  select 1
                    from information_schema.tables
                   where table_schema = ?
                     and lower(table_name) = lower(?)
                )
                """, Boolean.class, SCHEMA, VIEW);
            if (tableExists) {
                if (forceDropTable) {
                    log.warn("Tabela {}.{} encontrada. Dropando para recriar como VIEW...", SCHEMA, VIEW);
                    jdbc.execute("drop table " + SCHEMA + "." + VIEW + " cascade");
                } else {
                    log.error("Já existe uma TABELA {}.{}. A view não será criada. "
                            + "Ative app.view.force-drop-table=true ou remova/renomeie a tabela.", SCHEMA, VIEW);
                    return;
                }
            }

            // 2) Carregar SQL
            Resource res = loader.getResource(SQL_PATH);
            if (!res.exists()) {
                log.error("Arquivo SQL não encontrado: {}", SQL_PATH);
                return;
            }
            String ddl = new String(res.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
            if (ddl.isEmpty()) {
                log.error("Arquivo SQL está vazio: {}", SQL_PATH);
                return;
            }

            // 3) Executar
            log.info("Executando SQL da view a partir de {}", SQL_PATH);
            jdbc.execute(ddl);

            // 4) Confirmar existência
            boolean viewExists = jdbc.queryForObject("""
                select exists(
                  select 1
                    from pg_catalog.pg_views
                   where schemaname = ?
                     and lower(viewname) = lower(?)
                )
                """, Boolean.class, SCHEMA, VIEW);

            if (viewExists) {
                log.info("✅ View {}.{} criada/atualizada com sucesso.", SCHEMA, VIEW);
            } else {
                log.error("❌ View {}.{} não encontrada após execução. Verifique logs/SQL/Schema.", SCHEMA, VIEW);
            }
        } catch (Exception e) {
            log.error("Erro ao criar/atualizar view " + SCHEMA + "." + VIEW, e);
        }
    }
}

CREATE OR REPLACE VIEW public.v_relatorio_ordens_servico AS
SELECT
    -- Empresa
    e.razao_social  AS "razaoSocialEmpresa",
    e.nome_fantasia AS "nomeFantasiaEmpresa",
    e.cnpj          AS "cnpjEmpresa",

    -- Cliente
    c.razao_social  AS "razaoSocialCliente",
    c.nome_fantasia AS "nomeFantasiaCliente",
    c.cnpj          AS "cnpjCpfCliente",
    c.cep           AS "cepCliente",
    c.cidade        AS "cidadeCliente",
    c.bairro        AS "bairroCliente",
    c.estado        AS "ufEstadoCliente",
    c.telefone      AS "telefoneCliente",
    
    -- Ordem de Serviço
    os.numero_os AS "os",
    (SELECT STRING_AGG(nf.numero_nfse, ', ')
       FROM notas_fiscais_servico_omie nf
      WHERE nf.codigo_os = os.id
        AND nf.empresa_codigo_os = os.empresa_codigo) AS "numeroNfse",

    CASE 
      WHEN os.origem = 'CTR' THEN
        CASE WHEN os.etapa='00' THEN 'Contratos'
             WHEN os.etapa='60' THEN 'Contratos a Renovar'
             ELSE os.etapa END
      ELSE
        CASE WHEN os.etapa='60' THEN 'Faturado'
             WHEN os.etapa='00' THEN 'Orcamento'
             ELSE os.etapa END
    END AS "etapa",

    CASE
      WHEN os.cancelada THEN 'Cancelada'
      WHEN os.faturada  THEN 'Faturada'
      ELSE os.etapa
    END AS "situacao",

    os.dados_adicionaisnf     AS "informacoesAdicionaisDaNotaFiscal",
    os.numero_contrato         AS "numeroDoContratoDeVenda",
    os.cidade_prestacao_servico AS "cidadeDePrestacaoDoServico",
    os.observacoes_os          AS "observacoesOS",

    -- Contrato
    tfc.descricao           AS "tipoFaturamentoContrato",
    ctr.dia_faturamento     AS "diaFaturamentoContrato",
    ctr.data_vigencia_inicial AS "vigenciaInicialContrato",
    ctr.data_vigencia_final   AS "vigenciaFinalContrato",

    -- Entidades relacionadas
    p.nome  AS "projeto",
    v.nome  AS "vendedor",
    (SELECT STRING_AGG(d.nome, ', ')
       FROM ordens_servico_departamentos_omie osd
       JOIN departamentos d
         ON d.codigo = osd.departamento_codigo
        AND d.empresa_codigo = osd.departamento_empresa_codigo
      WHERE osd.ordem_servico_id = os.id
        AND osd.ordem_servico_empresa_codigo = os.empresa_codigo) AS "departamento",
    cat.nome AS "categoria",
    cc.nome  AS "contaCorrente",

    -- Datas
    os.data_previsao     AS "previsaoDeFaturamento",
    os.data_faturamento  AS "dataDeEmissao",
    os.data_inclusao     AS "dataDeInclusao",
    os.data_faturamento  AS "dataDeCompetencia",
    os.data_cancelamento AS "dataDoCancelamento",
    
    -- Item/Valores
    sc.codigo         AS "codigoServicoCadastrado",
    sc.valor_servico  AS "valorServicoCadastrado",
    cnae.codigo       AS "codigoCnae",
    cnae.descricao    AS "cnaePrincipal",
    osi.descricao     AS "descricaoDoServico",
    osi.quantidade    AS "quantidade",
    osi.valor_unitario AS "valorUnitario",
    osi.valor_desconto AS "valorDoDesconto",
    ((osi.valor_unitario * osi.quantidade) - COALESCE(osi.valor_desconto, 0)) AS "valorTotalDoServico",
    os.valor_total_impostos_retidos AS "impostosRetidosValorLiquido",
    osi.valor_iss     AS "valorDoIss",

    -- Alíquotas
    osi.aliquota_cofins AS "aliquotaCofins",
    osi.aliquota_csll   AS "aliquotaCsll",
    osi.aliquota_inss   AS "aliquotaInss",
    osi.aliquota_irrf   AS "aliquotaIrrf",
    osi.aliquota_iss    AS "aliquotaIss",
    osi.aliquota_pis    AS "aliquotaPis"

FROM ordens_servico_omie os
LEFT JOIN empresas e
       ON os.empresa_codigo = e.codigo
LEFT JOIN cliente_fornecedor c
       ON os.cliente_codigo = c.codigo
      AND os.cliente_empresa_codigo = c.empresa_codigo
LEFT JOIN ordens_servico_itens_omie osi
       ON os.id = osi.ordem_servico_id
      AND os.empresa_codigo = osi.ordem_servico_empresa_codigo
LEFT JOIN servicos_cadastrados_omie sc
       ON (osi.codigo_servico::text) = sc.codigo
      AND os.empresa_codigo = sc.empresa_codigo
LEFT JOIN cnae_omie cnae
       ON osi.codigo_cnae = cnae.codigo
LEFT JOIN contratos_servico_omie ctr
       ON os.numero_contrato = ctr.numero_contrato
      AND os.empresa_codigo = ctr.empresa_codigo
LEFT JOIN tipos_faturamento_contrato tfc
       ON ctr.tipo_faturamento = tfc.codigo
LEFT JOIN projetos p
       ON os.projeto_codigo = p.codigo
      AND os.projeto_empresa_codigo = p.empresa_codigo
LEFT JOIN categorias cat
       ON os.categoria_codigo = cat.codigo
      AND os.categoria_empresa_codigo = cat.empresa_codigo
LEFT JOIN contas_correntes cc
       ON os.conta_corrente_codigo = cc.codigo
      AND os.conta_corrente_empresa_codigo = cc.empresa_codigo
LEFT JOIN vendedores v
       ON (os.vendedor_codigo::text) = v.codigo
      AND os.vendedor_empresa_codigo = v.empresa_codigo;

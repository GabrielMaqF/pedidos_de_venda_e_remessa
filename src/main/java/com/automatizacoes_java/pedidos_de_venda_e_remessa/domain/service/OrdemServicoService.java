package com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.CategoriaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ClienteFornecedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ContaCorrenteEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.DepartamentoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.OrdemServicoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.ProjetoEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.VendedorEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.EntidadeCompostaId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.id.OrdemServicoId;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.OrdemServicoRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OrdemServicoDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service.OmieApiClientService;

import jakarta.transaction.Transactional;

@Service
public class OrdemServicoService extends BaseService<OrdemServicoEntity, OrdemServicoId, Long> {

	/**
	 * O Spring injetará o ClienteRepository aqui automaticamente. Nós então o
	 * passamos para o construtor da classe mãe (BaseService) para que os métodos
	 * genéricos (findAll, findById) possam usá-lo.
	 *
	 * @param clienteRepository O repositório específico para ClienteEntity.
	 */
	@Autowired
	OrdemServicoRepository repository;

	@Autowired
	OmieApiClientService omieApiClientService;

	@Autowired
	ClienteFornecedorService cfService;

	@Autowired
	CategoriaService cgService;

	@Autowired
	ContaCorrenteService ccService;

	@Autowired
	ProjetoService pjService;

	@Autowired
	VendedorService vdService;

	@Autowired
	DepartamentoService dpService;

	@Transactional
	public OrdemServicoEntity criarOuAtualizarPorOmie(OrdemServicoDTO dto, EmpresaEntity empresa) {

		ClienteFornecedorEntity cliente = cfService.findById(
				new EntidadeCompostaId(String.valueOf(dto.getCabecalho().getCodigoCliente()), empresa.getCodigo()))
				.orElse(null);
		CategoriaEntity categoria = cgService
				.findById(new EntidadeCompostaId(String.valueOf(dto.getInformacoesAdicionais().getCodigoCategoria()),
						empresa.getCodigo()))
				.orElse(null);
		ContaCorrenteEntity contaCorrente = ccService
				.findById(new EntidadeCompostaId(
						String.valueOf(dto.getInformacoesAdicionais().getCodigoContaCorrente()), empresa.getCodigo()))
				.orElse(null);
		ProjetoEntity projeto = pjService
				.findById(new EntidadeCompostaId(String.valueOf(dto.getInformacoesAdicionais().getCodigoProjeto()),
						empresa.getCodigo()))
				.orElse(null);
		VendedorEntity vendedor = vdService.findById(
				new EntidadeCompostaId(String.valueOf(dto.getCabecalho().getCodigoVendedor()), empresa.getCodigo()))
				.orElse(null);
		List<DepartamentoEntity> departamentos = dpService.findAll();

		OrdemServicoId id = new OrdemServicoId(dto.getCabecalho().getCodigoOs(), empresa.getCodigo());

		// Procura o cliente, se não existir, cria um novo
		OrdemServicoEntity entidade = repository.findById(id).orElse(new OrdemServicoEntity(dto, empresa));

		// Atualiza os dados com as informações do DTO
		entidade.atualizarDados(dto, cliente, categoria, contaCorrente, projeto, departamentos, vendedor);

		// Salva e retorna a entidade gerenciada
		return repository.save(entidade);
	}

	@Transactional
	public CompletableFuture<ResponseEntity<?>> getAllOmieUpdateBDA(List<EmpresaEntity> empresas) {

		if (empresas.isEmpty())
			return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Nenhuma Empresa Encontrada"));

		int totalRegistros = 0;
		for (EmpresaEntity e : empresas) {
			int paginaAtual = 1, totalPaginas = 0;

			try {
				Thread.sleep(1000L);
				do {
					OmieListarOsResponse res = omieApiClientService.listarOsPorPagina(e, paginaAtual).get();

					if (res == null || res.getOrdensDeServico().isEmpty())
						break;

					totalPaginas = res.getTotalDePaginas();

					for (OrdemServicoDTO dto : res.getOrdensDeServico()) {
						criarOuAtualizarPorOmie(dto, e); // este método: findById -> atualizarDados -> save
						totalRegistros++;
					}

//					System.out.printf("Empresa:\t%s\t|\tPagina:\t%d\t|\tTotalPagina:\t%d\t|\tTotalRegistros:\t%d%n",e.getNomeFantasia(), paginaAtual, totalPaginas, totalRegistros);

					paginaAtual++;
				} while (paginaAtual <= totalPaginas);
			} catch (InterruptedException | ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Erro no fluxo!"));
			}
		}

		return CompletableFuture
				.completedFuture(ResponseEntity.ok("Total de " + totalRegistros + " registros persistidos"));

	}
}

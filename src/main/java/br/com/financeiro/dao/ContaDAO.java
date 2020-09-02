package br.com.financeiro.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.financeiro.dto.ContaDTO;
import br.com.financeiro.model.Conta;

@Stateless
//EJB para controlar a transação automaticamente
//@Stateless para não salvar o objeto após a chamada, é o normalmente utilizado
//@Stateful para manter o objeto na sessão, utilizado para carrinho de compras por exemplo
//@Startup @Singleton para executar somente uma vez na inicialização do sistema
//https://www.devmedia.com.br/ejb-introducao-ao-novo-enterprise-javabeans-3-2/30807
public class ContaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	EntityManager em;

	private DAO<Conta> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Conta>(this.em, Conta.class);
	}
	
	public void salvar(ContaDTO contaDTO) throws ContaDAOException {
		
		validarConta(contaDTO);
		
		Conta conta = tradutorDTOParaEntidade(contaDTO);
		
		if(conta.getId() == null) {
			dao.adiciona(conta);
		}else {
			dao.atualiza(conta);
		}
	}
	
	private void validarConta(ContaDTO contaDTO) throws ContaDAOException {
		if(contaDTO.getValor().compareTo(BigDecimal.ZERO) < 0) {
			throw new ContaDAOException("Não é possível cadastrar um valor negativo");
		}
	}
	
	private Conta tradutorDTOParaEntidade(ContaDTO contaDTO) {
		Conta conta = new Conta();
		conta.setId(contaDTO.getId());
		conta.setNumero(contaDTO.getNumero());
		conta.setValor(contaDTO.getValor());
		
		return conta;
	}
	
	public ContaDTO tradutorEntidadeParaDTO(Conta conta) {
		ContaDTO contaDTO = new ContaDTO();
		contaDTO.setId(conta.getId());
		contaDTO.setNumero(conta.getNumero());
		contaDTO.setValor(conta.getValor());
		contaDTO.setCriacao(conta.getCriacao());
		contaDTO.setAlteracao(conta.getAlteracao());
		
		return contaDTO;
	}

	public void adiciona(Conta t) {
		dao.adiciona(t);
	}

	public void remove(Conta t) {
		dao.remove(t);
	}

	public void atualiza(Conta t) {
		dao.atualiza(t);
	}

	public List<ContaDTO> listaTodos() {
		List<Conta> listaConta = dao.listaTodos();
		List<ContaDTO> listaContaDTO = new ArrayList<ContaDTO>();
		for (Conta conta : listaConta) {
			listaContaDTO.add(tradutorEntidadeParaDTO(conta));
		}
		
		return listaContaDTO;
	}

	public Conta buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
}

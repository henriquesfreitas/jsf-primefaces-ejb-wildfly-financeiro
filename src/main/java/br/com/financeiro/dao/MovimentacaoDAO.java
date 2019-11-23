package br.com.financeiro.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.codenotfound.model.Endereco;
import com.codenotfound.model.Order;
import com.codenotfound.viacep.BuscaCEPServices;

import br.com.financeiro.dto.MovimentacaoDTO;
import br.com.financeiro.model.Conta;
import br.com.financeiro.model.Movimentacao;
import br.com.financeiro.utils.JMSUtils;

@Stateless
public class MovimentacaoDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1644838887229280540L;
	
	@PersistenceContext
	EntityManager em;

	private DAO<Movimentacao> dao;
	
	@Inject
	private JMSUtils jmsUtils;

	@PostConstruct
	void init() {
		this.dao = new DAO<Movimentacao>(this.em, Movimentacao.class);
	}
	
	public void enviarParaFilaESalvar(MovimentacaoDTO movimentacaoDTO) throws MovimentacaoDAOException {
		validarMovimentacao(movimentacaoDTO);
		jmsUtils.sendJMS(movimentacaoDTO);
	}
	
	public void salvar(MovimentacaoDTO movimentacaoDTO) {
		Movimentacao movimentacao = tradutorDTOParaEntidade(movimentacaoDTO);
		if(movimentacao.getId() == null) {
			dao.adiciona(movimentacao);
		}else {
			dao.atualiza(movimentacao);
		}
	}

	private void validarMovimentacao(MovimentacaoDTO MovimentacaoDTO) throws MovimentacaoDAOException {
		if(MovimentacaoDTO.getValor().compareTo(BigDecimal.ZERO) < 0) {
			throw new MovimentacaoDAOException("Não é possível cadastrar um valor negativo");
		}
	}
	
	private Movimentacao tradutorDTOParaEntidade(MovimentacaoDTO movimencacaoDTO) {
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setId(movimencacaoDTO.getId());
		movimentacao.setTipo(movimencacaoDTO.getTipo());
		movimentacao.setValor(movimencacaoDTO.getValor());
		movimentacao.setConta(buscaMovimentacaoPorId(movimencacaoDTO.getContaDTO().getId()));
		
		return movimentacao;
	}
	
	private MovimentacaoDTO tradutorEntidadeParaDTO(Movimentacao movimentacao) {
		MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO();
		movimentacaoDTO.setId(movimentacao.getId());
		movimentacaoDTO.setTipo(movimentacao.getTipo());
		movimentacaoDTO.setValor(movimentacao.getValor());
		movimentacaoDTO.setContaDTO(new ContaDAO().tradutorEntidadeParaDTO(movimentacao.getConta()));
		movimentacaoDTO.setCriacao(movimentacao.getCriacao());
		movimentacaoDTO.setAlteracao(movimentacao.getAlteracao());
		
		return movimentacaoDTO;
	}

	public void adiciona(Movimentacao t) {
		dao.adiciona(t);
	}

	public void remove(Movimentacao t) {
		dao.remove(t);
	}

	public void atualiza(Movimentacao t) {
		dao.atualiza(t);
	}

	public List<MovimentacaoDTO> listaTodos() {
		List<Movimentacao> listaMovimentacao = dao.listaTodos();
		List<MovimentacaoDTO> listaMovimentacaoDTO = new ArrayList<MovimentacaoDTO>();
		for (Movimentacao Movimentacao : listaMovimentacao) {
			listaMovimentacaoDTO.add(tradutorEntidadeParaDTO(Movimentacao));
		}
		
		return listaMovimentacaoDTO;
	}

	public Movimentacao buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	public Conta buscaMovimentacaoPorId(Integer id) {
		return em.find(Conta.class, id);
	}

}

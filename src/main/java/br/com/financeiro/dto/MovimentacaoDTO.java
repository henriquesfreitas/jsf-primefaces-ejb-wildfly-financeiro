package br.com.financeiro.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimentacaoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4285001473755726351L;
	private Integer id;
	private String tipo;
	private BigDecimal valor;
	private ContaDTO contaDTO;
	private LocalDateTime criacao;
	private LocalDateTime alteracao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public ContaDTO getContaDTO() {
		return contaDTO;
	}
	public void setContaDTO(ContaDTO contaDTO) {
		this.contaDTO = contaDTO;
	}
	public LocalDateTime getCriacao() {
		return criacao;
	}
	public String getCriacaoString() {
		return (criacao != null) ? criacao.toString() : null;
	}
	public void setCriacao(LocalDateTime criacao) {
		this.criacao = criacao;
	}
	public LocalDateTime getAlteracao() {
		return alteracao;
	}
	public String getAlteracaoString() {
		return (alteracao != null) ? alteracao.toString() : null;
	}
	public void setAlteracao(LocalDateTime alteracao) {
		this.alteracao = alteracao;
	}
	
}

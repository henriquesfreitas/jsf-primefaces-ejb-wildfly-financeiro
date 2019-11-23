package br.com.financeiro.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6123229138736529103L;
	private Integer id;
	private Integer numero;
	private BigDecimal valor;
	private LocalDateTime criacao;
	private LocalDateTime alteracao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public LocalDateTime getCriacao() {
		return criacao;
	}
	public void setCriacao(LocalDateTime criacao) {
		this.criacao = criacao;
	}
	public LocalDateTime getAlteracao() {
		return alteracao;
	}
	public void setAlteracao(LocalDateTime alteracao) {
		this.alteracao = alteracao;
	}
}

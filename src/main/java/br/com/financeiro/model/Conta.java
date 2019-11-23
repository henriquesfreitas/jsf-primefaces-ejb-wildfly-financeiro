package br.com.financeiro.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Conta extends Entidade{
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private Integer numero;
	private BigDecimal valor;
	
	//o lado fraco, que não possui o FK na tabela, é utilizado o mappedBy
	@OneToMany(mappedBy = "conta")
	private List<Movimentacao> movimentacao;
	
	public Conta() {
	}
	
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
	
}

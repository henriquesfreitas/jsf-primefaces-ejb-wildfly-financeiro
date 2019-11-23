package br.com.financeiro.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
//classe abstrata cria um objeto pai que não pode ser instanciado diretamente
//apenas pela a classe filha
public abstract class Entidade {

	//	@Column(name = "startTime", columnDefinition="DATETIME")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime criacao;

//	@Temporal(TemporalType.TIMESTAMP) temporal it's only used with Date
	private LocalDateTime alteracao;

	@PrePersist
	protected void onCreate() {
		criacao = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		alteracao = LocalDateTime.now();
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

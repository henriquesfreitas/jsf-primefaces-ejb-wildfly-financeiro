package br.com.financeiro.model;

import java.util.ArrayList;
import java.util.List;

public enum MovimentacaoTipoEnum {
	
	SAQUE("Saque"),
	DEPOSITO("Dep�sito");
	
	
	public String tipoValor;
	
	MovimentacaoTipoEnum(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	
	public String getValor(){
        return tipoValor;
    }
	
}

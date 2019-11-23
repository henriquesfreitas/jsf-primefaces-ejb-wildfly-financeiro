package br.com.financeiro.dao;

//como extende Exception, irá exigir um tratamento quando for invocado
//como throws na assinatura do método ou então o try catch
public class MovimentacaoDAOException extends Exception {
	
	public MovimentacaoDAOException(String message) {
		super(message);
	}

}

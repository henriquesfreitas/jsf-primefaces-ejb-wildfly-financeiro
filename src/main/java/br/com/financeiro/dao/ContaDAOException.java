package br.com.financeiro.dao;

//como extende Exception, irá exigir um tratamento quando for invocado
//como throws na assinatura do método ou então o try catch
public class ContaDAOException extends Exception{

	public ContaDAOException(String message) {
		super(message);
	}
	
}

package br.com.financeiro.dao;

//como extende Exception, ir� exigir um tratamento quando for invocado
//como throws na assinatura do m�todo ou ent�o o try catch
public class ContaDAOException extends Exception{

	public ContaDAOException(String message) {
		super(message);
	}
	
}

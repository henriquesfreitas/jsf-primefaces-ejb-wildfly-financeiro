package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.financeiro.dao.ContaDAO;
import br.com.financeiro.dao.ContaDAOException;
import br.com.financeiro.dto.ContaDTO;

//http://blog.triadworks.com.br/nao-misture-anotacoes-do-jsf-com-anotacoes-do-cdi
//Percebemos aqui que temos um problema, pois o escopo de Request � curto demais para manter o bean na quantidade de tempo que desejamos
//e o escopo Session � longo demais e acaba mantendo o bean al�m do que desejamos. O ViewScoped veio para tentar resolver esse problema, 
//criando um meio-termo entre o Request e o Session.
//O ViewScoped mant�m o estado do bean enquanto houver requisi��es da mesma view/p�gina, e quando ele muda de p�gina o estado do bean � descartado.
//https://antoniogoncalves.org/2011/09/25/injection-with-cdi-part-iii/
@Named
@ViewScoped
public class ContaBean implements Serializable {

	private static final long serialVersionUID = 5887595765114164464L;
	
	private ContaDTO contaDTO;
	private List<ContaDTO> listaContaDTO;
	
	//https://stackoverflow.com/questions/8138232/should-i-use-ejb-or-inject
	//you could use @EJB instead of @Inject, it's makes sense since 
	//ContaDAO use the EJB @Stateless, but with @Inject you can have more options and it's recommend to Java EE 6 or up
	//"@Inject can inject any bean, while @EJB can only inject EJBs. You can use either to inject EJBs, but I'd prefer @Inject everywhere."
	@Inject
	private ContaDAO contaDAO;
	
	@PostConstruct
	public void init() {
		novaConta();
		listarTodos();
	}
	
	private void novaConta() {
		contaDTO = new ContaDTO();
	}
	
	private void listarTodos() {
		listaContaDTO = contaDAO.listaTodos();
	}
	
//	anota��o para come�ar uma transa��o com CDI, por�m como ser� controlado pelo EJB na classe DAO
//	n�o necessidade de colocar essa anota��o, a menos que queira que esse m�todo inteiro fique
//	dentro de uma transa��o
//	@Transactional 
	public void salvar() {
		System.out.println("salvar "+ contaDTO.getNumero());
		try {
			contaDAO.salvar(contaDTO);
			listarTodos();
			novaConta();
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta salva com sucesso!", ""));
		} catch (ContaDAOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("messages", new FacesMessage(e.getLocalizedMessage()));
		}
	}
	
	public ContaDTO getContaDTO() {
		return contaDTO;
	}

	public void setContaDTO(ContaDTO contaDTO) {
		this.contaDTO = contaDTO;
	}

	public List<ContaDTO> getListaContaDTO() {
		return listaContaDTO;
	}

	public void setListaContaDTO(List<ContaDTO> listaContaDTO) {
		this.listaContaDTO = listaContaDTO;
	}

	public ContaDAO getContaDAO() {
		return contaDAO;
	}

	public void setContaDAO(ContaDAO contaDAO) {
		this.contaDAO = contaDAO;
	}
	
}

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

@Named
@ViewScoped
public class ContaBean implements Serializable {

	private static final long serialVersionUID = 5887595765114164464L;
	
	private ContaDTO contaDTO;
	private List<ContaDTO> listaContaDTO;
	
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
	
//	anotação para começar uma transação com CDI, porém como será controlado pelo EJB na classe DAO
//	não necessidade de colocar essa anotação, a menos que queira que esse método inteiro fique
//	dentro de uma transação
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

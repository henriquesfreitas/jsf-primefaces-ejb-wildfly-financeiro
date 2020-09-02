package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.financeiro.dao.ContaDAO;
import br.com.financeiro.dao.MovimentacaoDAO;
import br.com.financeiro.dao.MovimentacaoDAOException;
import br.com.financeiro.dto.ContaDTO;
import br.com.financeiro.dto.MovimentacaoDTO;
import br.com.financeiro.model.MovimentacaoTipoEnum;

@Named
@ViewScoped
public class MovimentacaoBean implements Serializable{

	private static final long serialVersionUID = 7187093799219358485L;
	
	private MovimentacaoDTO movimentacaoDTO;
	private List<MovimentacaoDTO> listaMovimentacaoDTO;
	private List<ContaDTO> listaContas;
	
	//https://stackoverflow.com/questions/8138232/should-i-use-ejb-or-inject
	//you could use @EJB instead of @Inject, it's makes sense since 
	//ContaDAO use the EJB @Stateless, but with @Inject you can have more options and it's recommend to Java EE 6 or up
	//"@Inject can inject any bean, while @EJB can only inject EJBs. You can use either to inject EJBs, but I'd prefer @Inject everywhere."
	@Inject
	private MovimentacaoDAO movimentacaoDAO;
	
	@Inject
	private ContaDAO contaDAO;
	
	@PostConstruct
	public void init() {
		novaMovimentacao();
		listarTodasAsContas();
		listarTodos();
	}
	
	private void novaMovimentacao() {
		movimentacaoDTO = new MovimentacaoDTO();
		movimentacaoDTO.setContaDTO(new ContaDTO());
	}
	

	public List<MovimentacaoTipoEnum> getListaMovimentacaoTipoEnum(){
		
		List<MovimentacaoTipoEnum> movimentacaoTipos = new ArrayList<MovimentacaoTipoEnum>();
		for (MovimentacaoTipoEnum movimentacaoTipo : MovimentacaoTipoEnum.values()) {
			movimentacaoTipos.add(movimentacaoTipo);
		}
		
		return movimentacaoTipos;
	}
	
	private void listarTodos() {
		listaMovimentacaoDTO = movimentacaoDAO.listaTodos();
	}
	
//	anotação para começar uma transação com CDI, porém como será controlado pelo EJB na classe DAO
//	não necessidade de colocar essa anotação, a menos que queira que esse método inteiro fique
//	dentro de uma transação
//	@Transactional 
	public void salvar() {
		System.out.println("salvar "+ movimentacaoDTO.getTipo());
		try {
			movimentacaoDAO.enviarParaFilaESalvar(movimentacaoDTO);
			listarTodos();
			novaMovimentacao();
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimentação salva com sucesso!", ""));
		} catch (MovimentacaoDAOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("messages", new FacesMessage(e.getLocalizedMessage()));
		}
	}
	
	private void listarTodasAsContas() {
		listaContas = contaDAO.listaTodos();
	}
	
	public MovimentacaoDTO getMovimentacaoDTO() {
		return movimentacaoDTO;
	}

	public void setMovimentacaoDTO(MovimentacaoDTO movimentacaoDTO) {
		this.movimentacaoDTO = movimentacaoDTO;
	}

	public List<MovimentacaoDTO> getListaMovimentacaoDTO() {
		return listaMovimentacaoDTO;
	}

	public void setListaMovimentacaoDTO(List<MovimentacaoDTO> listaMovimentacaoDTO) {
		this.listaMovimentacaoDTO = listaMovimentacaoDTO;
	}

	public MovimentacaoDAO getMovimentacaoDAO() {
		return movimentacaoDAO;
	}

	public void setMovimentacaoDAO(MovimentacaoDAO movimentacaoDAO) {
		this.movimentacaoDAO = movimentacaoDAO;
	}

	public List<ContaDTO> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<ContaDTO> listaContas) {
		this.listaContas = listaContas;
	}

}

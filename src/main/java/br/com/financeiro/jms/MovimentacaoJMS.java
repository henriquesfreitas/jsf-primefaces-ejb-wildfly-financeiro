package br.com.financeiro.jms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.com.financeiro.dao.MovimentacaoDAO;
import br.com.financeiro.dto.MovimentacaoDTO;


/*
 * https://stackoverflow.com/questions/16671422/how-to-acknowledge-messages-in-message-driven-beans
 * não é possível alterar o Auto-acknowledge no messageDriven, para testar de outra forma, ver o link acima,
 * ou criar um outro bean em que um botão chama ele
 *
 */
@MessageDriven(
        name = "JMSProcessorService",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/MovimentacaoQueue")
        })
public class MovimentacaoJMS implements MessageListener{
	
//	setado para dar rollback e fazer a mensagem voltar para a fila
//  o melhor seria usar o message.acknowledge(), porém no EJB não suporta
//	@Resource
//    private MessageDrivenContext messageDrivenContext;
	
	@Inject
	private MovimentacaoDAO movimentacaoDAO;
	
	@Override
    public void onMessage(Message message) {

        final String now = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now());
        System.out.println("iniciando consumo fila");

        if (message instanceof ObjectMessage) {

            final ObjectMessage objectMessage = (ObjectMessage) message;

            try {
                final MovimentacaoDTO movimentacaoDTO = (MovimentacaoDTO) objectMessage.getObject();
                movimentacaoDAO.salvar(movimentacaoDTO);
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
        
//    	setado para dar rollback e fazer a mensagem voltar para a fila
//      messageDrivenContext.setRollbackOnly();
        
        System.out.println("concluido consumo");
    }
}

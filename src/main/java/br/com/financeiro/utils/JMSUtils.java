package br.com.financeiro.utils;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

@Stateless
public class JMSUtils {
	
	@Resource(lookup = "java:/jms/queue/MovimentacaoQueue")
    private Queue queue;
	
    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory cf;
 
    private Connection connection;

	public void sendJMS(Object message) {

		try {         
			connection = cf.createConnection();
			//CLIENT_ACKNOWLEDGE espera que o consumidor avise que a mensagem foi lida e pode ser descartada
			//invocando o message.acknowledge()
			//AUTO_ACKNOWLEDGE ele descarta a mensagem automaticamente quando alguem a recebe
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			MessageProducer publisher = null;

			publisher = session.createProducer(queue);

			connection.start();

			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject((Serializable) message);
			publisher.send(objectMessage);
		}catch (Exception exc) {
			exc.printStackTrace();
		}finally {         
			if (connection != null)   {
				try {
					connection.close();
				} catch (JMSException e) {                    
					e.printStackTrace();
				}
			}
		}
	}
}

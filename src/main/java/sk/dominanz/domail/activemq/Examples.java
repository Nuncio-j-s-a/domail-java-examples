package sk.dominanz.domail.activemq;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

import sk.dominanz.domail.Configuration;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import javax.jms.Message;
import javax.jms.TextMessage;

public class Examples {
	private static final String CLIENTID = "ExamplesActvieMQ";
	private static long TIMEOUT = 15000;
	
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	//private MessageProducer producer;
	private MessageConsumer consumer;
	
    private String connectionUrlPath = "tcp://" + Configuration.URL + ":61616";
    private String connectionQueueName = "COM_NOTIF";
    

    public Examples() {
    	//connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
    		
    }
    
    
	public void example10_ConsumeNotifications() throws JMSException {
		System.out.println("example10_ConsumeNotifications");

		this.connectionFactory = new ActiveMQConnectionFactory(connectionUrlPath);

		// Create a Connection
		this.connection = connectionFactory.createConnection();
		this.connection.setClientID(CLIENTID);
		this.connection.start();

		// Create a Session
		this.session = connection.createSession(false, AUTO_ACKNOWLEDGE);

		// Get the destination (Topic or Queue)
		this.destination = this.session.createQueue(this.connectionQueueName);

		// Create a MessageProducer from the Session to the Topic or Queue
		this.consumer = this.session.createConsumer(destination);
		// this.consumer.Listener += Consumer_Listener;

		System.out.println("Waiting to receive message from consumer, will timeout after " + TIMEOUT/1000 + "s");

		// receive the message from the queue.
		Message message = consumer.receive(TIMEOUT);
		
		// Since We are using TestMessage in our example. MessageProducer sent us a TextMessage
		// So we need cast to it to get access to its getText() method which will give
		// us the text of the message
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			System.out.printf("Received message '%s' from the queue '%s' running on local JMS Server.\n", textMessage.getText(), connectionQueueName);
		}
		else{
            System.out.println("Queue Empty"); 
        }

		
		//Close a Connection
		this.connection.stop();
		this.connection.close();
	}
}

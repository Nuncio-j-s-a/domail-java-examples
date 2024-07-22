/**
 * 
 */
package sk.dominanz.domail;

import java.io.IOException;

import javax.jms.JMSException;

import sk.dominanz.domail.activemq.Examples;

/**
 * @author vanek
 *
 */
public class Program {

	/**
	 * @param args
	 * @throws IOException, JMSException 
	 */
	public static void main(String[] args) throws IOException,JMSException {
		
		//OpenApi
		sk.dominanz.domail.openapi.Examples examples_openApi = new sk.dominanz.domail.openapi.Examples();
		examples_openApi.example1_EmailSendSimple1();
		//examples_openApi.example2_EmailSendSimple2();
		//examples_openApi.example3_EmailSendAdvanced1();
		//examples_openApi.example4_EmailSendAdvanced2();
		//examples_openApi.example5_CommunicationGet();
		//examples_openApi.example6_communicationGetState();
		//examples_openApi.example7_EmailSendWithTemplate();
		//examples_openApi.example8_EmailSendWithTemplateAdvanced_JSON();
		//examples_openApi.example9_EmailSendWithTemplateAdvanced_CSV();
		

		
		//SOAP	
		sk.dominanz.domail.soap.Examples example_soap = new sk.dominanz.domail.soap.Examples();
		//example_soap.example1_EmailSendSimple1();
		//example_soap.example2_EmailSendSimple2();
		//example_soap.example3_EmailSendAdvanced1();
		//example_soap.example4_EmailSendAdvanced2();
		//example_soap.example5_CommunicationGet();
		//example_soap.example6_communicationGetState();
		//example_soap.example7_EmailSendWithTemplate();
		//example_soap.example8_EmailSendWithTemplateAdvanced_JSON();
		//example_soap.example9_EmailSendWithTemplateAdvanced_CSV();
		
		//SMTP
		sk.dominanz.domail.smtp.Examples examples_smtp = new sk.dominanz.domail.smtp.Examples();
        //examples_smtp.exampleSmtp_EmailSend1();
		//examples_smtp.exampleSmtp_EmailSend2();
		//examples_smtp.exampleSmtp_EmailSend3();
		
		//ActiveMQ
		sk.dominanz.domail.activemq.Examples examples_activemq = new sk.dominanz.domail.activemq.Examples();
		//examples_activemq.example10_ConsumeNotifications();
	}

}

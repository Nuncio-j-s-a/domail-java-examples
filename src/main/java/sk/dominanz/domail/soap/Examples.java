package sk.dominanz.domail.soap;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import sk.dominanz.domail.Configuration;
import sk.dominanz.domail.DomailClientSoap;


public class Examples {
	private String url = "/dws/domail-input-ws/";
	private DomailClientSoap client;
		
	public Examples() throws MalformedURLException {
		this.client = new DomailClientSoap(Configuration.DOMAIL_URL + url);
	}


	/**
	 * Send simple email to 1 recipient
	 */
	public Long example1_EmailSendSimple1() {
		System.out.println("example1_EmailSendSimple1");

		EmailSendRequestType esr = new EmailSendRequestType();
		esr.setScenario("Default_REST");

		//set 1 recipients
		EmailAddressType eat = new EmailAddressType();
		eat.setName("NameOfEmail");
		eat.setEmailAddress(Configuration.MailTo);
		
		esr.getTo().add(eat);
		
		CommunicationResponseType result = client.sendEmail(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id=" + result.getId());
		return result.getId();
	}
	
	/**
	 * Send simple email for 2 recipients with 1 file in attachment
	 * @throws UnsupportedEncodingException
	 */
	public void example2_EmailSendSimple2() throws UnsupportedEncodingException {
		System.out.println("example2_EmailSendSimple2");

		EmailSendRequestType esr = new EmailSendRequestType();
		esr.setScenario("Default_REST");
		
		List<EmailAddressType> contacts = new ArrayList<EmailAddressType>();
		
		//set 2 recipients
		EmailAddressType eat1 = new EmailAddressType();
		eat1.setName("NameOfEmail");
		eat1.setEmailAddress(Configuration.MailTo);
		esr.getTo().add(eat1);
		
		EmailAddressType eat2 = new EmailAddressType();
		eat2.setName("NameOfEmail");
		eat2.setEmailAddress(Configuration.MailTo2);
		contacts.add(eat2);
		esr.getTo().add(eat2);
		
		//set 1 attachment
		FileHandlerType fileHandlerType = new FileHandlerType();
		fileHandlerType.setFilename("testFile1");
		String utf8String = "VG90byBqZSB0ZXN0b3ZhY2lhIHByaWxvaGE";
		fileHandlerType.setDataHandler(utf8String.getBytes("UTF-8"));
		fileHandlerType.setContentID("54321");
		fileHandlerType.setMimeType("application/text");
		fileHandlerType.setEncoding("UTF-8");
		esr.getAttachment().add(fileHandlerType);
		
		//set statistics field
		StatisticsType statisticsType = new StatisticsType();
		statisticsType.setGroup("JavaExample");
		statisticsType.setOperation("Example2");
		statisticsType.setCategory("Examples");
		statisticsType.getTag().add("tag1");
		statisticsType.getTag().add("TEST");	
		esr.setStatistics(statisticsType);
		 
		esr.setSubject(Configuration.MailSubject);
		esr.setHtmlBody("<b>Html body</b>");
		esr.setTestMode(false);
	
		CommunicationResponseType result = client.sendEmail(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id="  + result.getId());
	}
	
	
	/**
	 * Send email for 1 recipient with advanced informations
	 */
	public void example3_EmailSendAdvanced1() {
		System.out.println("example3_EmailSendAdvanced1");
		
		
		EmailSendAdvRequestType esr = new EmailSendAdvRequestType();
		esr.setExtId(System.currentTimeMillis()+"");
		esr.setSysId("200");
		
		EmailSendRequestType esrt = new EmailSendRequestType();
		esrt.setScenario("Default_REST");

		// set 1 recipients
		EmailAddressType eat = new EmailAddressType();
		eat.setName("NameOfEmail");
		eat.setEmailAddress(Configuration.MailTo);
		esrt.getTo().add(eat);

		esr.setEmailSendRequest(esrt);
			
		CommunicationResponseType result = client.sendEmailAdvanced(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id=" + result.getId());
	}
	

	/**
	 * Send email for 2 recipients with advanced informations
	 * @throws UnsupportedEncodingException 
	 */
	public void example4_EmailSendAdvanced2() throws UnsupportedEncodingException {
		System.out.println("example4_EmailSendAdvanced2");

		EmailSendAdvRequestType esr = new EmailSendAdvRequestType();
		esr.setExtId(System.currentTimeMillis()+"");
		esr.setSysId("200");
		
		EmailSendRequestType esrt = new EmailSendRequestType();
		esrt.setScenario("Default_REST");
		
		esrt.setSubject(Configuration.MailSubject);
		esrt.setHtmlBody("<b>Html body</b>");
		esrt.setTestMode(false);
		
		esr.setEmailSendRequest(esrt);
			
		//set 2 recipients
		EmailAddressType eat1 = new EmailAddressType();
		eat1.setName("NameOfEmail");
		eat1.setEmailAddress(Configuration.MailTo);
		esrt.getTo().add(eat1);
		
		EmailAddressType eat2 = new EmailAddressType();
		eat2.setName("NameOfEmail");
		eat2.setEmailAddress(Configuration.MailTo2);
		esrt.getTo().add(eat2);
			
		FileHandlerType fileHandlerType = new FileHandlerType();
		fileHandlerType.setFilename("testFile1");
		String utf8String = "VG90byBqZSB0ZXN0b3ZhY2lhIHByaWxvaGE";
		
		fileHandlerType.setDataHandler(utf8String.getBytes("UTF-8"));
		fileHandlerType.setContentID("54321");
		fileHandlerType.setMimeType("application/text");
		fileHandlerType.setEncoding("UTF-8");
		esrt.getAttachment().add(fileHandlerType);
		
		StatisticsType statisticsType = new StatisticsType();
		statisticsType.setGroup("JavaExample");
		statisticsType.setOperation("Example4");
		statisticsType.setCategory("Examples");
		statisticsType.getTag().add("tag1");
		statisticsType.getTag().add("TEST");	
		esrt.setStatistics(statisticsType);

		CommunicationResponseType result = client.sendEmailAdvanced(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id=" + result.getId());
	}
	
	/**
	 * Example for get info about communication
	 */
	public void example5_CommunicationGet() {
		long id = example1_EmailSendSimple1();
		System.out.println("example5_CommunicationGet");

		CommunicationGetRequestType req = new CommunicationGetRequestType();
		req.setId(id);
		
		CommunicationGetResponseType result = client.getCommunicationInfo(req);
		if (result.getDetail() != null) {
			
			for(CommunicationDetailType detail : result.getDetail())
			{
				if (detail.getId() > 0)	
				{
					System.out.println("call GET OK - detail.Id=" + detail.getId());
					System.out.println(detail);
				}
			}
		}
	}
	
	/**
	 * Example for get info about state of communication
	 */
	public void example6_communicationGetState() {
		long id = example1_EmailSendSimple1();
		System.out.println("example6_communicationGetState");

		
		CommunicationGetStateRequestType req = new CommunicationGetStateRequestType();
		req.setId(id);
		
		CommunicationGetStateResponseType result = client.getCommunicationState(req);
		
		if (result.getState() != null) {
			for(CommunicationStateType state : result.getState())
			{
				if (state.getId() > 0)	
				{
					System.out.println("call GET OK - state.Id=" + state.getId());
					System.out.println(state);
				}
			}
		}
	}
	

	/**
	 * Send email with created template
	 */
	public void example7_EmailSendWithTemplate() {
		System.out.println("example7_EmailSendWithTemplate");
		
		EmailSendWithTemplateRequestType eswtr = new EmailSendWithTemplateRequestType();
		eswtr.setScenario("Default_TEMPLATE");
	
		//set parameters
		eswtr.getFillParams().add(new ParametersTemplateType() {{
			setKey("email");
			setValue(Configuration.MailTo);
		}});
		
		eswtr.getFillParams().add(new ParametersTemplateType() {{
			setKey("subject");
			setValue(Configuration.MailSubject);
		}});
		
		eswtr.getFillParams().add(new ParametersTemplateType() {{
			setKey("firstname");
			setValue("John");
		}});
		
		eswtr.getFillParams().add(new ParametersTemplateType() {{
			setKey("surname");
			setValue("Doe");
		}});
		
		
		EmailSendWithTemplateResponseType result = client.sendEmailTemplate(eswtr);
		if (result.getMetaCommunication() > 0)
			System.out.println("call POST OK - result.MetaCommunication=" + result.getMetaCommunication());
	}
	

	/**
	 * Send advanced emails with template with JSON input data 
	 * @throws IOException
	 */
	public void example8_EmailSendWithTemplateAdvanced_JSON() throws IOException {
		System.out.println("example8_EmailSendWithTemplateAdvanced_JSON");
	
		EmailSendWithTemplateAdvRequestType e = new EmailSendWithTemplateAdvRequestType();
		e.setScenario("Default_TEMPLATE_JSON");
		e.setExtId(System.currentTimeMillis()+"");
		e.setSysId(System.currentTimeMillis()+"");

		e.setStatistics(new StatisticsType() {{
			setGroup("JavaExample");
			setOperation("Example8");
			setCategory("Examples");
			getTag().add("tag1");
			getTag().add("TEST");	
		}});

		e.setAdvReqData(new AdvReqType() {{
			setPriority(true);
			setDuplicity(false);
		}});
		
		String jsonData = "[{ \"email\" : \""+Configuration.MailTo+"\"," +
                "\"subject\" : \"Subject of the first email in JSON\"," +
                "\"firstname\" : \"John\"," +
                "\"lastname\" : \"Doe\"" +
               "}," +
               "{" +
               "\"email\" : \""+Configuration.MailTo2+"\"," +
               "\"subject\" :\"Subject of the second email in JSON\"," +
               "\"firstname\" : \"John\"," +
               "\"lastname\" : \"Doe II.\"" +
               "}" +
            "]";

		TemplateDataJSONType json = new TemplateDataJSONType();
		json.setJSONData(jsonData.getBytes(StandardCharsets.UTF_8));
		e.setTemplateDataJSON(json);
		
		EmailSendWithTemplateResponseType result = client.sendEmailTemplateAdvanced(e);
		if (result.metaCommunication > 0)
			System.out.println("call POST OK - result.metaCommunication=" + result.metaCommunication);	
	}
	
	

	/**
	 * Send advanced emails with template with CSV input data 
	 * @throws IOException
	 */
	public void example9_EmailSendWithTemplateAdvanced_CSV() throws IOException {
		System.out.println("example9_EmailSendWithTemplateAdvanced_CSV");
	
		EmailSendWithTemplateAdvRequestType e = new EmailSendWithTemplateAdvRequestType();
		e.setScenario("Default_TEMPLATE_CSV");
		e.setExtId(System.currentTimeMillis()+"");
		e.setSysId(System.currentTimeMillis()+"");

		e.setStatistics(new StatisticsType() {{
			setGroup("JavaExample");
			setOperation("Example9");
			setCategory("Examples");
			getTag().add("tag1");
			getTag().add("TEST");	
		}});
		
		e.setAdvReqData(new AdvReqType() {{
			setPriority(true);
			setDuplicity(false);
		}});
	
		FileHandlerType file1 = new FileHandlerType();
		file1.setFilename("test1.csv");
		file1.setContentID("1");
		file1.setMimeType("application/csv");
		file1.setEncoding("utf -8");
		
		String csvData = "name,surname,email,document ID\r\n"
				+ "John,Doe,"+Configuration.MailTo+",123\r\n"
				+ "John,Doe II.,"+Configuration.MailTo2+",456";
		
		file1.setDataHandler(csvData.getBytes(StandardCharsets.UTF_8));
		
		
		FileHandlerType file2 = new FileHandlerType();
		file2.setFilename("fakturky.csv");
		file2.setContentID("2");
		file2.setMimeType("application/csv");
		file2.setEncoding("utf -8");
		
		String csvData2 = "document ID,name,amount\r\n"
				+ "123,Monthly payment - 1,235\r\n"
				+ "123,Monthly payment - 2,100\r\n"
				+ "123,Monthly payment - 3,200\r\n"
				+ "123,Monthly payment - 4,400\r\n"
				+ "123,Monthly payment - 5,500\r\n"
				+ "123,Monthly payment - 6,700\r\n"
				+ "456,Monthly payment - 1,111\r\n"
				+ "456,Monthly payment - 2,150\r\n"
				+ "456,Monthly payment - 3,160";
		
		file2.setDataHandler(csvData2.getBytes(StandardCharsets.UTF_8));
			
	
		TemplateDataCSVType csv = new TemplateDataCSVType();
		csv.getCSVFiles().add(file1);
		csv.getCSVFiles().add(file2);
		e.setTemplateDataCSV(csv);
		
		
		EmailSendWithTemplateResponseType result = client.sendEmailTemplateAdvanced(e);
		if (result.metaCommunication > 0)
			System.out.println("call POST OK - result.metaCommunication=" + result.metaCommunication);	
	}
}

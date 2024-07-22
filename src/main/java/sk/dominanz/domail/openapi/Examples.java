package sk.dominanz.domail.openapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import sk.dominanz.domail.input.rest.model.EmailSendRequest;
import sk.dominanz.domail.input.rest.model.EmailSendWithTemplateAdvancedRequest;
import sk.dominanz.domail.input.rest.model.EmailSendWithTemplateRequest;
import sk.dominanz.domail.input.rest.model.FileHandlerType;
import sk.dominanz.domail.input.rest.model.ParametersTemplate;
import sk.dominanz.domail.Configuration;
import sk.dominanz.domail.DomailClientRest;
import sk.dominanz.domail.input.rest.model.AdvancedRequestType;
import sk.dominanz.domail.input.rest.model.CommunicationDetail;
import sk.dominanz.domail.input.rest.model.CommunicationId;
import sk.dominanz.domail.input.rest.model.CommunicationStateType;
import sk.dominanz.domail.input.rest.model.CommunicationTemplateId;
import sk.dominanz.domail.input.rest.model.EmailAddressType;
import sk.dominanz.domail.input.rest.model.EmailSendAdvancedRequest;
import sk.dominanz.domail.input.rest.model.StatisticsType;

public class Examples {
	String url = "/dir/domail-input-rest";    

	private DomailClientRest client;
	public Examples() {
		this.client = new DomailClientRest(Configuration.DOMAIL_URL +  url,Configuration.Username,Configuration.Password);
	}
	
	/**
	 * Send simple email to 1 recipient
	 */
	public Long example1_EmailSendSimple1() {
		System.out.println("example1_EmailSendSimple1");
		
		EmailSendRequest esr = new EmailSendRequest();
		esr.setScenario("Default_REST");
		
		List<EmailAddressType> contacts = new ArrayList<EmailAddressType>();
		
		//set 1 recipients
		EmailAddressType eat = new EmailAddressType();
		eat.setAddressName("NameOfEmail");
		eat.setEmailAddress(Configuration.MailTo);
		contacts.add(eat);
		
		esr.setTo(contacts);
		CommunicationId result = client.sendEmail(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id="  + result.getId());
		return result.getId();
	}
	
	
	
	/**
	 * Send simple email for 2 recipients with 1 file in attachment
	 * @throws UnsupportedEncodingException
	 */
	public void example2_EmailSendSimple2() throws UnsupportedEncodingException {
		System.out.println("example2_EmailSendSimple2");

		EmailSendRequest esr = new EmailSendRequest();
		esr.setScenario("Default_REST");
		
		List<EmailAddressType> contacts = new ArrayList<EmailAddressType>();
		
		//set 2 recipients
		EmailAddressType eat1 = new EmailAddressType();
		eat1.setAddressName("NameOfEmail");
		eat1.setEmailAddress(Configuration.MailTo);
		contacts.add(eat1);
		
		EmailAddressType eat2 = new EmailAddressType();
		eat2.setAddressName("NameOfEmail");
		eat2.setEmailAddress(Configuration.MailTo2);
		contacts.add(eat2);
		
		esr.setTo(contacts);
		
		FileHandlerType fileHandlerType = new FileHandlerType();
		fileHandlerType.setFilename("testFile1");
		String utf8String = "VG90byBqZSB0ZXN0b3ZhY2lhIHByaWxvaGE";
		
		fileHandlerType.setDataHandler(utf8String.getBytes("UTF-8"));
		fileHandlerType.setContentID("54321");
		fileHandlerType.setMimeType("application/text");
		fileHandlerType.setEncoding("UTF-8");
		esr.addAttachmentsItem(fileHandlerType);		
		
		StatisticsType statisticsType = new StatisticsType();
		statisticsType.setGroup("JavaExample");
		statisticsType.setOperation("Example2");
		statisticsType.setCategory("Examples");
		statisticsType.addTagsItem("tag1");
		statisticsType.addTagsItem("TEST");	
		esr.setStatistics(statisticsType);
		 
		esr.setSubject(Configuration.MailSubject);
		esr.setHtmlBody("<b>Html body</b>");
		esr.setTestmode(false);
		
		CommunicationId result = client.sendEmail(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id="  + result.getId());
	}

	/**
	 * Send email for 1 recipient with advanced informations
	 */
	public void example3_EmailSendAdvanced1() {
		System.out.println("example3_EmailSendAdvanced1");

		EmailSendAdvancedRequest esr = new EmailSendAdvancedRequest();
		esr.setScenario("Default_REST");
		esr.setExtId(System.currentTimeMillis()+"");
		esr.setSysId("200");
		
		// set 1 recipients
		List<EmailAddressType> contacts = new ArrayList<EmailAddressType>();
		EmailAddressType eat = new EmailAddressType();
		eat.setAddressName("NameOfEmail");
		eat.setEmailAddress(Configuration.MailTo);
		contacts.add(eat);

		esr.setTo(contacts);

		CommunicationId result = client.sendEmailAdvanced(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id=" + result.getId());
		
	}

	

	/**
	 * Send email for 2 recipients with advanced informations
	 * @throws UnsupportedEncodingException 
	 */
	public void example4_EmailSendAdvanced2() throws UnsupportedEncodingException {
		System.out.println("example4_EmailSendAdvanced2");

		EmailSendAdvancedRequest esr = new EmailSendAdvancedRequest();
		esr.setScenario("Default_REST");
		esr.setExtId(System.currentTimeMillis()+"");
		esr.setSysId("200");
		
		List<EmailAddressType> contacts = new ArrayList<EmailAddressType>();
		//set 2 recipients
		EmailAddressType eat1 = new EmailAddressType();
		eat1.setAddressName("NameOfEmail");
		eat1.setEmailAddress(Configuration.MailTo);
		contacts.add(eat1);
		
		EmailAddressType eat2 = new EmailAddressType();
		eat2.setAddressName("NameOfEmail");
		eat2.setEmailAddress(Configuration.MailTo2);
		contacts.add(eat2);
		
		esr.setTo(contacts);

		
		FileHandlerType fileHandlerType = new FileHandlerType();
		fileHandlerType.setFilename("testFile1");
		String utf8String = "VG90byBqZSB0ZXN0b3ZhY2lhIHByaWxvaGE";
		
		fileHandlerType.setDataHandler(utf8String.getBytes("UTF-8"));
		fileHandlerType.setContentID("54321");
		fileHandlerType.setMimeType("application/text");
		fileHandlerType.setEncoding("UTF-8");
		esr.addAttachmentsItem(fileHandlerType);		
		
		StatisticsType statisticsType = new StatisticsType();
		statisticsType.setGroup("JavaExample");
		statisticsType.setOperation("Example4");
		statisticsType.setCategory("Examples");
		statisticsType.addTagsItem("tag1");
		statisticsType.addTagsItem("TEST");	
		esr.setStatistics(statisticsType);
		 
		esr.setSubject(Configuration.MailSubject);
		esr.setHtmlBody("<b>Html body</b>");
		esr.setTestmode(false);
		
		
		CommunicationId result = client.sendEmailAdvanced(esr);
		if (result.getId() > 0)
			System.out.println("call POST OK - result.Id=" + result.getId());
	}
	
	/**
	 * Example for get info about communication
	 */
	public void example5_CommunicationGet() {
		long id = example1_EmailSendSimple1();
		System.out.println("example5_CommunicationGet");
		
		CommunicationDetail result = client.getCommunicationInfo(id);
		if (result.getId() > 0) {
			System.out.println("call GET OK - result.Id=" + result.getId());
			System.out.println(result);
		}
	}
		
	/**
	 * Example for get info about state of communication
	 */
	public void example6_communicationGetState() {
		long id = example1_EmailSendSimple1();
		System.out.println("example6_communicationGetState");

		CommunicationStateType result = client.getCommunicationState(id);
		if (result.getId() > 0) {
			System.out.println("call GET OK - result.Id=" + result.getId());
			System.out.println(result);
		}
	}
	
	/**
	 * Send email with created template
	 */
	public void example7_EmailSendWithTemplate() {
		System.out.println("example7_EmailSendWithTemplate");
		
		EmailSendWithTemplateRequest eswtr = new EmailSendWithTemplateRequest();
		eswtr.setScenario("Default_TEMPLATE");
	
		//set parameters
		List<ParametersTemplate> params = new ArrayList<ParametersTemplate>();
		
		params.add(new ParametersTemplate() {{
			setKey("email");
			setValue(Configuration.MailTo);
		}});
		
		params.add(new ParametersTemplate() {{
			setKey("subject");
			setValue("Subject of email");
		}});
		
		params.add(new ParametersTemplate() {{
			setKey("firstname");
			setValue("John");
		}});
		
		params.add(new ParametersTemplate() {{
			setKey("surname");
			setValue("Doe");
		}});
		
		eswtr.setParams(params);

		
		CommunicationTemplateId result = client.sendEmailTemplate(eswtr);
		if (result.getMainCommunicationId() > 0)
			System.out.println("call POST OK - result.MainCommunicationId=" + result.getMainCommunicationId());
	}
	
	/**
	 * Send advanced emails with template with JSON input data 
	 * @throws IOException
	 */
	public void example8_EmailSendWithTemplateAdvanced_JSON() throws IOException {
		System.out.println("example8_EmailSendWithTemplateAdvanced_JSON");
	
		EmailSendWithTemplateAdvancedRequest e = new EmailSendWithTemplateAdvancedRequest();
		e.setScenario("Default_TEMPLATE_JSON");
		e.setExtId(System.currentTimeMillis()+"");
		e.setSysId(System.currentTimeMillis()+"");

		e.setStatistics(new StatisticsType() {{
			setGroup("JavaExample");
			setOperation("Example8");
			setCategory("Examples");
			addTagsItem("tag1");
			addTagsItem("TEST");	
		}});
		
		e.setAdvancedRequestData(new AdvancedRequestType() {{
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
		
		
		ObjectMapper mapper = new ObjectMapper();
		//JsonNode node = mapper.readTree(jsonData);
		Object map = mapper.readValue(jsonData, Object.class);
		e.setTemplateDataJSON((List<Object>)map);
		
		CommunicationTemplateId result = client.sendEmailTemplateAdvanced(e);
		if (result.getMainCommunicationId() > 0)
			System.out.println("call POST OK - result.MainCommunicationId=" + result.getMainCommunicationId());	
	}
	
	
	/**
	 * Send advanced emails with template with CSV input data 
	 * @throws IOException
	 */
	public void example9_EmailSendWithTemplateAdvanced_CSV() throws IOException {
		System.out.println("example9_EmailSendWithTemplateAdvanced_CSV");
	
		EmailSendWithTemplateAdvancedRequest e = new EmailSendWithTemplateAdvancedRequest();
		e.setScenario("Default_TEMPLATE_CSV");
		e.setExtId(System.currentTimeMillis()+"");
		e.setSysId(System.currentTimeMillis()+"");

		e.setStatistics(new StatisticsType() {{
			setGroup("JavaExample");
			setOperation("Example9");
			setCategory("Examples");
			addTagsItem("tag1");
			addTagsItem("TEST");	
		}});
		
		e.setAdvancedRequestData(new AdvancedRequestType() {{
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
				+ "John,Doe II.,"+Configuration.MailTo+",456";
		
		file1.setDataHandler(csvData.getBytes(StandardCharsets.UTF_8));
		e.addTemplateDataCSVItem(file1);
		
		
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
		e.addTemplateDataCSVItem(file2);
		
		
		
		CommunicationTemplateId result = client.sendEmailTemplateAdvanced(e);
		if (result.getMainCommunicationId() > 0)
			System.out.println("call POST OK - result.MainCommunicationId=" + result.getMainCommunicationId());	
	}
	
	
}

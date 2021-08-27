//,temp,sample_280.java,2,13,temp,sample_7630.java,2,13
//,2
public class xxx {
private void verifyTheRecivedEmail(String expectString) throws Exception {
MockEndpoint mock = getMockEndpoint("mock:result");
mock.assertIsSatisfied();
Exchange out = mock.assertExchangeReceived(0);
ByteArrayOutputStream baos = new ByteArrayOutputStream(((MailMessage)out.getIn()).getMessage().getSize());
((MailMessage)out.getIn()).getMessage().writeTo(baos);
String dumpedMessage = baos.toString();
assertTrue("There should have the " + expectString, dumpedMessage.indexOf(expectString) > 0);


log.info("multipart alternative");
}

};
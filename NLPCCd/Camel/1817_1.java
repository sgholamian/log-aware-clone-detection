//,temp,sample_694.java,2,9,temp,sample_693.java,2,9
//,2
public class xxx {
public void processRequest(RequestEvent requestReceivedEvent) {
Request request = requestReceivedEvent.getRequest();
ServerTransaction serverTransactionId = requestReceivedEvent .getServerTransaction();
String viaBranch = ((ViaHeader)(request.getHeaders(ViaHeader.NAME).next())).getParameter("branch");


log.info("received from branch");
}

};
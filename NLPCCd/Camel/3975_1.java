//,temp,sample_2241.java,2,18,temp,sample_2242.java,2,20
//,3
public class xxx {
private void prepareMessageSenders(SpringWebserviceConfiguration configuration) {
if (!(configuration.getTimeout() > -1) && configuration.getSslContextParameters() == null) {
return;
}
WebServiceTemplate webServiceTemplate = configuration.getWebServiceTemplate();
WebServiceMessageSender[] messageSenders = webServiceTemplate.getMessageSenders();
for (int i = 0; i < messageSenders.length; i++) {
WebServiceMessageSender messageSender = messageSenders[i];
if (messageSender instanceof HttpComponentsMessageSender) {
if (configuration.getSslContextParameters() != null) {


log.info("not applying sslcontextparameters based configuration to httpcomponentsmessagesender if you are using this messagesender which you are not by default you will need to configure ssl using the commons http x protocol registry");
}
}
}
}

};
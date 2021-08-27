//,temp,sample_2241.java,2,18,temp,sample_2242.java,2,20
//,3
public class xxx {
public void dummy_method(){
WebServiceMessageSender[] messageSenders = webServiceTemplate.getMessageSenders();
for (int i = 0; i < messageSenders.length; i++) {
WebServiceMessageSender messageSender = messageSenders[i];
if (messageSender instanceof HttpComponentsMessageSender) {
if (configuration.getSslContextParameters() != null) {
}
if (configuration.getTimeout() > -1) {
if (messageSender.getClass().equals(HttpComponentsMessageSender.class)) {
((HttpComponentsMessageSender) messageSender).setReadTimeout(configuration.getTimeout());
} else {


log.info("not applying timeout configuration to httpcomponentsmessagesender based implementation you are using what appears to be a custom messagesender which you are not doing by default you will need configure timeout on your own");
}
}
}
}
}

};
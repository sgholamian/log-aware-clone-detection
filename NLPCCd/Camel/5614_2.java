//,temp,sample_4336.java,2,10,temp,sample_2321.java,2,13
//,3
public class xxx {
public String route(Exchange exchange) {
Message message = exchange.getIn().getBody(Message.class);
if (message != null) {
SessionID destinationSession = getDestinationSessionID(message);
if (destinationSession != null) {
String destinationUri = String.format("%s?sessionID=%s", engineUri, destinationSession);


log.info("routing destination");
}
}
}

};
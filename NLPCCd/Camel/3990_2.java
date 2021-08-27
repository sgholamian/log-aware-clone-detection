//,temp,sample_1220.java,2,18,temp,sample_5058.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (failed && !getEndpoint().getConfiguration().isTransferExchange()) {
if (exchange.getException() != null) {
response = exchange.getException();
} else {
response = exchange.getOut().getBody();
}
}
if (response != null) {
Mina2Helper.writeBody(session, response, exchange, configuration.getWriteTimeout());
} else {


log.info("writing no response");
}
}

};
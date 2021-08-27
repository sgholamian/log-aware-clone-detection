//,temp,sample_4205.java,2,11,temp,sample_3129.java,2,15
//,3
public class xxx {
private void writeToStream(OutputStream outputStream, Exchange exchange) throws IOException, CamelExchangeException {
Object body = exchange.getIn().getBody();
if (body == null) {
return;
}
if (!(body instanceof String)) {
byte[] bytes = exchange.getIn().getBody(byte[].class);
if (bytes != null) {


log.info("writing as byte to");
}
}
}

};
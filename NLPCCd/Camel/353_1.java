//,temp,sample_3128.java,2,9,temp,sample_1710.java,2,8
//,3
public class xxx {
private void writeToStream(OutputStream outputStream, Exchange exchange) throws IOException, CamelExchangeException {
Object body = exchange.getIn().getBody();
if (body == null) {


log.info("body is null cannot write it to the stream");
}
}

};
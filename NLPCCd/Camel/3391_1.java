//,temp,sample_377.java,2,10,temp,sample_4901.java,2,17
//,3
public class xxx {
private Object readWithAdapter(JsonPath path, Exchange exchange) {
Object json = headerName != null ? exchange.getIn().getHeader(headerName) : exchange.getIn().getBody();
doInitAdapter(exchange);
if (adapter != null) {


log.info("attempting to use jacksonjsonadapter");
}
}

};
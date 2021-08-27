//,temp,sample_376.java,2,7,temp,sample_375.java,2,7
//,3
public class xxx {
private Object readWithAdapter(JsonPath path, Exchange exchange) {
Object json = headerName != null ? exchange.getIn().getHeader(headerName) : exchange.getIn().getBody();


log.info("jsonpath is read with adapter");
}

};
//,temp,sample_376.java,2,7,temp,sample_375.java,2,7
//,3
public class xxx {
private Object readWithInputStream(JsonPath path, Exchange exchange) throws IOException {
Object json = headerName != null ? exchange.getIn().getHeader(headerName) : exchange.getIn().getBody();


log.info("jsonpath is read as inputstream");
}

};
//,temp,sample_1920.java,2,10,temp,sample_1584.java,2,11
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
MongoDbOperation operation = endpoint.getOperation();
Object header = exchange.getIn().getHeader(OPERATION_HEADER);
if (header != null) {


log.info("overriding default operation with operation specified on header");
}
}

};
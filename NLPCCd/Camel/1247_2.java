//,temp,sample_1976.java,2,11,temp,sample_4244.java,2,10
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
MongoDbOperation operation = endpoint.getOperation();
Object header = exchange.getIn().getHeader(MongoDbConstants.OPERATION_HEADER);
if (header != null) {


log.info("overriding default operation with operation specified on header");
}
}

};
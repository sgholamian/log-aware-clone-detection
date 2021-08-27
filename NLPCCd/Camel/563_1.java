//,temp,sample_7314.java,2,9,temp,sample_1856.java,2,9
//,3
public class xxx {
private void tryConfigureBulkRequests() throws JSchException {
Integer bulkRequests = endpoint.getConfiguration().getBulkRequests();
if (bulkRequests != null) {


log.info("configuring channel to use up to bulk request s");
}
}

};
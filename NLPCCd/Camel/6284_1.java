//,temp,sample_1014.java,2,11,temp,sample_8508.java,2,12
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String remotePath = DropboxHelper.getRemotePath(configuration, exchange);
DropboxConfigurationValidator.validateDelOp(remotePath);
DropboxDelResult result = new DropboxAPIFacade(configuration.getClient(), exchange) .del(remotePath);
exchange.getIn().setHeader(DropboxResultHeader.DELETED_PATH.name(), result.getEntry());
exchange.getIn().setBody(result.getEntry());


log.info("deleted");
}

};
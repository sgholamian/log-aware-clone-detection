//,temp,sample_1014.java,2,11,temp,sample_8508.java,2,12
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String remotePath = DropboxHelper.getRemotePath(configuration, exchange);
String newRemotePath = DropboxHelper.getNewRemotePath(configuration, exchange);
DropboxConfigurationValidator.validateMoveOp(remotePath, newRemotePath);
DropboxMoveResult result = new DropboxAPIFacade(configuration.getClient(), exchange) .move(remotePath, newRemotePath);
exchange.getIn().setHeader(DropboxResultHeader.MOVED_PATH.name(), result.getOldPath());
exchange.getIn().setBody(result.getNewPath());


log.info("moved from to");
}

};
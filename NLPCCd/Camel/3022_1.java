//,temp,sample_519.java,2,12,temp,sample_4287.java,2,9
//,3
public class xxx {
protected boolean doConnect(RemoteFileConfiguration configuration) throws GenericFileOperationFailedException {
String host = configuration.getHost();
int port = configuration.getPort();
String username = configuration.getUsername();
String account = ((FtpConfiguration) configuration).getAccount();
if (clientConfig != null) {


log.info("configuring ftpclient with config");
}
}

};
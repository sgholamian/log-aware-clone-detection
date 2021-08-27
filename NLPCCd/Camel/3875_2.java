//,temp,sample_5042.java,2,16,temp,sample_5041.java,2,13
//,3
public class xxx {
protected void doStart() throws Exception {
super.doStart();
if (configuration.isClientMode() && configuration.getProtocol().equals("tcp")) {
connector.setHandler(new ReceiveHandler());
ConnectFuture future = connector.connect(address);
future.awaitUninterruptibly();
session = future.getSession();


log.info("connected to server address using connector timeout millis");
}
}

};
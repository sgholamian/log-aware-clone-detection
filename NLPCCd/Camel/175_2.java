//,temp,sample_1633.java,2,9,temp,sample_1632.java,2,7
//,3
public class xxx {
public void messageReceived(ChannelHandlerContext ctx, MessageEvent messageEvent) throws Exception {
HttpRequest request = (HttpRequest) messageEvent.getMessage();


log.info("message received");
}

};
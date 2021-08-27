//,temp,sample_4251.java,2,6,temp,sample_218.java,2,8
//,3
public class xxx {
private void handleError(ChannelHandlerContext ctx, Object msg, OutstandingRpc rpc) throws Exception {
if (msg instanceof String) {


log.info("received error message");
}
}

};
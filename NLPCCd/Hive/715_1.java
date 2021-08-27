//,temp,sample_221.java,2,8,temp,sample_3948.java,2,10
//,3
public class xxx {
public final void channelInactive(ChannelHandlerContext ctx) throws Exception {
if (rpcs.size() > 0) {


log.info("closing rpc channel with outstanding rpcs");
}
}

};
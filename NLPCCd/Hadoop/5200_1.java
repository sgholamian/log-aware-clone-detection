//,temp,sample_5175.java,2,17,temp,sample_5174.java,2,13
//,3
public class xxx {
public void dummy_method(){
RpcCall call = (RpcCall) info.header();
SocketAddress remoteAddress = info.remoteAddress();
if (LOG.isTraceEnabled()) {
}
if (this.progNumber != call.getProgram()) {
sendAcceptedReply(call, remoteAddress, AcceptState.PROG_UNAVAIL, ctx);
return;
}
int ver = call.getVersion();
if (ver < lowProgVersion || ver > highProgVersion) {


log.info("invalid rpc call version");
}
}

};
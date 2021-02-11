//,temp,sample_1061.java,2,15,temp,sample_1062.java,2,17
//,3
public class xxx {
public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
if (evt instanceof IdleStateEvent) {
IdleStateEvent idleEvt = (IdleStateEvent) evt;
switch (idleEvt.state()) {
case WRITER_IDLE: if (id2Call.isEmpty()) {
if (LOG.isTraceEnabled()) {


log.info("shutdown connection to because idle for a long time");
}
}
}
}
}

};
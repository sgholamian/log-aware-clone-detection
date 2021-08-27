//,temp,sample_7091.java,2,8,temp,sample_675.java,2,10
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
if (cause instanceof IOException) {


log.info("io exception client connection closed");
}
}

};
//,temp,sample_1253.java,2,9,temp,sample_7092.java,2,9
//,3
public class xxx {
public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
if (cause instanceof IOException) {
} else {


log.info("caught an exception while reading closing channel");
}
}

};
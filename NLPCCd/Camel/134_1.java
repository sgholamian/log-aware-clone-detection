//,temp,sample_5054.java,2,8,temp,sample_3389.java,2,10
//,3
public class xxx {
public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
if (cause instanceof IOException) {


log.info("ioexceptions are automatically handled by mina");
}
}

};
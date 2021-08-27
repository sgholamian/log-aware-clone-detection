//,temp,sample_8495.java,2,9,temp,sample_5055.java,2,11
//,3
public class xxx {
public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
if (cause instanceof IOException) {
return;
}
if (session != null) {


log.info("closing session as an exception was thrown from mina");
}
}

};
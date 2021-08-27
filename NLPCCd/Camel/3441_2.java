//,temp,sample_3534.java,2,7,temp,sample_6130.java,2,7
//,3
public class xxx {
public static void writeBody(IoSession session, Object body, Exchange exchange) throws CamelExchangeException {
WriteFuture future = session.write(body);


log.info("waiting for write to complete for body using session");
}

};
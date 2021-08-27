//,temp,sample_4313.java,2,11,temp,sample_4316.java,2,11
//,2
public class xxx {
public void failed(Throwable throwable) {
try {
saveCookies(exchange, client, cxfRsEndpoint.getCookieHandler());
fail(throwable);
} catch (Exception error) {


log.info("error while processing failed request");
}
}

};
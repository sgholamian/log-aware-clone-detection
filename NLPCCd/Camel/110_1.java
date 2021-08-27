//,temp,sample_3132.java,2,12,temp,sample_631.java,2,17
//,3
public class xxx {
private void openStream(final Exchange exchange) throws Exception {
if (outputStream != null) {
return;
}
if ("header".equals(uri)) {
outputStream = resolveStreamFromHeader(exchange.getIn().getHeader("stream"), exchange);


log.info("opened stream");
}
}

};
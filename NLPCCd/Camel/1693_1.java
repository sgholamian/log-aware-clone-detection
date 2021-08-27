//,temp,sample_7068.java,2,13,temp,sample_646.java,2,10
//,3
public class xxx {
public HttpResponse toNettyResponse(Message message, NettyHttpConfiguration configuration) throws Exception {
if (message.getBody() instanceof HttpResponse) {
return (HttpResponse) message.getBody();
}
boolean failed = message.getExchange().isFailed();
int defaultCode = failed ? 500 : 200;
int code = message.getHeader(Exchange.HTTP_RESPONSE_CODE, defaultCode, int.class);
HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(code));


log.info("http status code");
}

};
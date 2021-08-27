//,temp,sample_7068.java,2,13,temp,sample_646.java,2,10
//,3
public class xxx {
public void toSparkResponse(Message message, Response response, SparkConfiguration configuration) throws Exception {
boolean failed = message.getExchange().isFailed();
int defaultCode = failed ? 500 : 200;
int code = message.getHeader(Exchange.HTTP_RESPONSE_CODE, defaultCode, int.class);
response.status(code);


log.info("http status code");
}

};
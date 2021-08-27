//,temp,sample_4595.java,2,8,temp,sample_4596.java,2,8
//,2
public class xxx {
public void testFutureEchoException() throws Exception {
Echo service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Echo.class);
Future<String> future = service.asText(4);


log.info("got future");
}

};
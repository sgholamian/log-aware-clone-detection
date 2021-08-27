//,temp,sample_6353.java,2,8,temp,sample_6354.java,2,8
//,2
public class xxx {
public void testFutureEchoCallTwoTimes() throws Exception {
Echo service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Echo.class);
Future<String> future = service.asText(4);


log.info("got future");
}

};
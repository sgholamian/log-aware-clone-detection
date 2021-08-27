//,temp,sample_6356.java,2,10,temp,sample_6355.java,2,10
//,2
public class xxx {
public void testFutureEchoCallTwoTimes() throws Exception {
Echo service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Echo.class);
Future<String> future = service.asText(4);
assertEquals("Four", future.get(5, TimeUnit.SECONDS));
future = service.asText(5);


log.info("waiting for future to be done");
}

};
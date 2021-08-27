//,temp,sample_6352.java,2,8,temp,sample_6351.java,2,8
//,2
public class xxx {
public void testFutureEcho() throws Exception {
Echo service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Echo.class);
Future<String> future = service.asText(4);


log.info("waiting for future to be done");
}

};
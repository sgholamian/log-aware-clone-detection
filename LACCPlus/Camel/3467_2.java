//,temp,ProxyReturnFutureTest.java,44,59,temp,ProxyReturnFutureTest.java,31,41
//,3
public class xxx {
    @Test
    public void testFutureEcho() throws Exception {
        Echo service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Echo.class);

        Future<String> future = service.asText(4);
        log.info("Got future");

        log.info("Waiting for future to be done ...");
        String reply = future.get(5, TimeUnit.SECONDS);
        assertEquals("Four", reply);
    }

};
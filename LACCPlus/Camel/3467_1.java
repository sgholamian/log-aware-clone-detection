//,temp,ProxyReturnFutureTest.java,44,59,temp,ProxyReturnFutureTest.java,31,41
//,3
public class xxx {
    @Test
    public void testFutureEchoCallTwoTimes() throws Exception {
        Echo service = ProxyHelper.createProxy(context.getEndpoint("direct:echo"), Echo.class);

        Future<String> future = service.asText(4);
        log.info("Got future");

        log.info("Waiting for future to be done ...");
        assertEquals("Four", future.get(5, TimeUnit.SECONDS));

        future = service.asText(5);
        log.info("Got future");

        log.info("Waiting for future to be done ...");
        assertEquals("Four", future.get(5, TimeUnit.SECONDS));
    }

};
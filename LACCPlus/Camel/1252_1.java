//,temp,AsyncJmsInOutTest.java,51,67,temp,SyncJmsInOutIT.java,35,51
//,3
public class xxx {
    @Test
    public void testAsyncJmsInOut() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(100);
        mock.expectsNoDuplicates(body());

        StopWatch watch = new StopWatch();

        for (int i = 0; i < 100; i++) {
            template.sendBody("seda:start", "" + i);
        }

        // just in case we run on slow boxes
        assertMockEndpointsSatisfied(20, TimeUnit.SECONDS);

        LOG.info("Took " + watch.taken() + " ms. to process 100 messages request/reply over JMS");
    }

};
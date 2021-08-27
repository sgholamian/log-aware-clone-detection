//,temp,JettyAsyncDefaultContinuationTimeoutTest.java,41,62,temp,JettyAsyncContinuationTimeoutTest.java,37,58
//,3
public class xxx {
    @Test
    public void testJettyAsyncTimeout() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived("Bye World");

        StopWatch watch = new StopWatch();
        try {
            template.requestBody("http://localhost:{{port}}/myservice", null, String.class);
            fail("Should have thrown an exception");
        } catch (CamelExecutionException e) {
            LOG.info("Timeout hit and client got reply with failure status code");

            long taken = watch.taken();

            HttpOperationFailedException cause = assertIsInstanceOf(HttpOperationFailedException.class, e.getCause());
            assertEquals(504, cause.getStatusCode());

            // should be approx 3-4 sec.
            assertTrue(taken < 4500, "Timeout should occur faster than " + taken);
        }

        assertMockEndpointsSatisfied();
    }

};
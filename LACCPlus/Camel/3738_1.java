//,temp,JmsDefaultTaskExecutorTypeTest.java,75,88,temp,JmsDefaultTaskExecutorTypeTest.java,60,73
//,3
public class xxx {
    @Test
    public void testDefaultTaskExecutor() throws Exception {
        context.getRouteController().startRoute("default");
        Long beforeThreadCount = currentThreadCount();
        getMockEndpoint("mock:result.default").expectedMessageCount(1000);
        doSendMessages("foo.default", 500, 5, null);
        Thread.sleep(100);
        doSendMessages("foo.default", 500, 5, null);
        assertMockEndpointsSatisfied();
        Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;
        LOG.info("Number of threads created, testDefaultTaskExecutor: " + numberThreadsCreated);
        assertTrue(numberThreadsCreated >= 800, "Number of threads created should be equal or higher than "
                                                + "800 with default behaviour");
    }

};
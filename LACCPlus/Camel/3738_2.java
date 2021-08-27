//,temp,JmsDefaultTaskExecutorTypeTest.java,75,88,temp,JmsDefaultTaskExecutorTypeTest.java,60,73
//,3
public class xxx {
    @Test
    public void testSimpleAsyncTaskExecutor() throws Exception {
        context.getRouteController().startRoute("simpleAsync");
        Long beforeThreadCount = currentThreadCount();
        getMockEndpoint("mock:result.simpleAsync").expectedMessageCount(1000);
        doSendMessages("foo.simpleAsync", 500, 5, DefaultTaskExecutorType.SimpleAsync);
        Thread.sleep(100);
        doSendMessages("foo.simpleAsync", 500, 5, DefaultTaskExecutorType.SimpleAsync);
        assertMockEndpointsSatisfied();
        Long numberThreadsCreated = currentThreadCount() - beforeThreadCount;
        LOG.info("Number of threads created, testSimpleAsyncTaskExecutor: " + numberThreadsCreated);
        assertTrue(numberThreadsCreated >= 800, "Number of threads created should be equal or higher than "
                                                + "800 with SimpleAsyncTaskExecutor");
    }

};